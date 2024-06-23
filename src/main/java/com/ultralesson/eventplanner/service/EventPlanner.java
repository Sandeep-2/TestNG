package com.ultralesson.eventplanner.service;

import com.ultralesson.eventplanner.model.Attendee;
import com.ultralesson.eventplanner.model.Event;
import com.ultralesson.eventplanner.model.Schedule;
import com.ultralesson.eventplanner.model.Venue;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EventPlanner {
    private List<Event> events;
    private List<Venue> venues;
    private List<Attendee> attendees;
    private List<Schedule> schedules;

    public EventPlanner() {
        this.events = new ArrayList<>();
        this.venues = new ArrayList<>();
        this.attendees = new ArrayList<>();
        this.schedules = new ArrayList<>();
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public void addVenue(Venue venue) {
        venues.add(venue);
    }

    public void addAttendee(Attendee attendee) {
        attendees.add(attendee);
    }

    public void scheduleEvent(Event event, Venue venue, LocalDateTime startTime, LocalDateTime endTime) {
        if (checkDateIsPast(startTime))
            throw new IllegalArgumentException("Start date cannot be past dated");
        if (checkDateIsPast(endTime))
            throw new IllegalArgumentException("End date cannot be past dated");
        if(checkIsOverlapping(startTime,endTime))
            throw new IllegalArgumentException("Schedule time cannot be overlapped");

        int scheduleId = schedules.size() + 1;
        Schedule schedule = new Schedule(scheduleId, event, venue, startTime, endTime);
        schedules.add(schedule);
    }

    public void updateEvent(Event updatedEvent) {
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getId() == updatedEvent.getId()) {
                events.set(i, updatedEvent);
                break;
            }
        }
    }

    public void cancelEvent(int eventId) {
        events.removeIf(event -> event.getId() == eventId);
        schedules.removeIf(schedule -> schedule.getEvent().getId() == eventId);
    }

    public void deleteEvent(int eventId) {
        cancelEvent(eventId);
    }

    public List<Event> getEvents() {
        return events;
    }

    public List<Venue> getVenues() {
        return venues;
    }

    public List<Attendee> getAttendees() {
        return attendees;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public boolean checkDateIsPast(LocalDateTime date) {
       return LocalDateTime.now().toLocalDate().isAfter(date.toLocalDate());
    }

    public boolean checkIsOverlapping(LocalDateTime startTime,LocalDateTime endTime){
        boolean isOverlapping = false;
        for (Schedule schedule : schedules) {
            if (schedule.getEndTime().isAfter(startTime)||endTime.isAfter(schedule.getStartTime()))
                isOverlapping = true;
        }
        return isOverlapping;
    }

    public void updateVenue(Venue updatedVenue,Venue venue) {
        venues = venues.stream()
                .map(ven -> ven.getId() == venue.getId() ? updatedVenue : venue)
                .collect(Collectors.toList());
    }

    public void removeVenue(Venue updatedVenue) {
        venues = venues.stream()
                .filter(venue -> venue != updatedVenue)
                .collect(Collectors.toList());
    }

    public void assignVenueToEvent(Venue nonExistentVenue, Event event) {
        event.setVenue(nonExistentVenue);
    }
    public boolean createEvent(Event event) {
        if (event.getName() == null || event.getName().isEmpty()) {
            throw new IllegalArgumentException("Event name cannot be null or empty");
        }
        if (event.getDescription() == null || event.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Event description cannot be null or empty");
        }
        // Add other validation checks if necessary
        addEvent(event);
        return true;
    }
}

