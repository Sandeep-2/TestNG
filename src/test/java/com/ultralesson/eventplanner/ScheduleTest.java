package com.ultralesson.eventplanner;

import com.ultralesson.eventplanner.model.Event;
import com.ultralesson.eventplanner.model.Schedule;
import com.ultralesson.eventplanner.model.Venue;
import com.ultralesson.eventplanner.service.EventPlanner;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;

public class ScheduleTest {

    private EventPlanner eventPlanner;

    @BeforeMethod
    public void setUp() {
        eventPlanner = new EventPlanner();
    }

    @Test
    public void testCreateScheduleWithValidDetails() {
        Venue venue = new Venue(1, "Conference Center", "New York Central", 500);
        Event event = new Event(1, "Tech Conference", "A conference about technology", venue);
        eventPlanner.addEvent(event);
        LocalDateTime startTime = LocalDateTime.now().plusDays(1);
        LocalDateTime endTime = startTime.plusHours(2);

        eventPlanner.scheduleEvent(event, venue, startTime, endTime);
        Schedule createdSchedule = eventPlanner.getSchedules().get(0);

        Assert.assertNotNull(createdSchedule, "Schedule should not be null");
        Assert.assertEquals(createdSchedule.getEvent(), event, "Scheduled event should match the input event");
        Assert.assertEquals(createdSchedule.getVenue(), venue, "Scheduled venue should match the input venue");
        Assert.assertEquals(createdSchedule.getStartTime(), startTime, "Start time should match the input start time");
        Assert.assertEquals(createdSchedule.getEndTime(), endTime, "End time should match the input end time");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateScheduleWithPastDates() {
        Venue venue = new Venue(2, "Hotel Ballroom", "Washington DC", 200);
        Event event = new Event(2, "Wedding Reception", "A lovely wedding reception", venue);
        LocalDateTime startTime = LocalDateTime.now().minusDays(1); // Past date
        LocalDateTime endTime = startTime.plusHours(2);

        eventPlanner.scheduleEvent(event, venue, startTime, endTime);
    }


}
