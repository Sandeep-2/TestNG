package com.ultralesson.eventplanner.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Event {
    private int id;
    private String name;
    private String description;
    private List<Attendee> attendees;

    private Venue venue;

    public Event(int id, String name, String description, Venue venue) {
        if (name == null || name == "")
            throw new IllegalArgumentException("Name is should not be null");
        else if (description == null)
            throw new IllegalArgumentException("Description is should not be null");
        else if (venue == null)
            throw new IllegalArgumentException("Venue is should not be null");
        this.id = id;
        this.name = name;
        this.description = description;
        this.venue = venue;
        this.attendees = new ArrayList<>();
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Attendee> getAttendees() {
        return attendees;
    }

    public void addAttendee(Attendee attendee) {
        attendees.add(attendee);
    }

    public void removeAttendee(Attendee attendee) {
        attendees.remove(attendee);
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public void addAttendees(List<Attendee> newAttendees) {
        attendees.addAll(newAttendees);
    }
}


