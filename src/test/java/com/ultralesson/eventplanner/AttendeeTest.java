package com.ultralesson.eventplanner;

import com.ultralesson.eventplanner.model.Attendee;
import com.ultralesson.eventplanner.model.Event;
import com.ultralesson.eventplanner.model.Venue;
import com.ultralesson.eventplanner.service.EventPlanner;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class AttendeeTest {

    Attendee attendee;

    @BeforeMethod
    public void setUp() {
        attendee = new Attendee(1, "John Doe", "john.doe@example.com");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowExceptionForInvalidEmail(){
        Attendee invalidAttendee = new Attendee(5, "Ishan Kumar", "example.com");
    }

    @Test
    public void testAttendeeProperties() {
        assertEquals(attendee.getId(), 1, "Attendee ID does not match");
        assertEquals(attendee.getName(), "John Doe", "Attendee name does not match");
        assertEquals(attendee.getEmail(), "john.doe@example.com", "Attendee email does not match");
    }

    @Test
    public void testAddAttendeeToEvent() {
        EventPlanner eventPlanner = new EventPlanner();
        Venue venue = new Venue(1, "Conference Center", "New York Central", 500);
        Event event = new Event(1, "Tech Conference", "A conference about technology", venue);
        eventPlanner.addEvent(event);

        Attendee attendee = new Attendee(3, "Alice Brown", "alice.brown@example.com");
        event.addAttendee(attendee);

        Assert.assertTrue(event.getAttendees().contains(attendee), "Attendee should have been added to the event");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddInvalidAttendeeToEvent() {
        Attendee invalidAttendee = new Attendee(4, "", "");
    }

    @Test(expectedExceptions = Exception.class)
    public void testAddAttendeeToNonExistentEvent() {
        EventPlanner eventPlanner = new EventPlanner();
        Venue venue = new Venue(1, "Conference Center", null, 500);
        Event fakeEvent = new Event(99, "Non-Existent Event", "This event does not exist", venue);

        Attendee attendee = new Attendee(5, "Charlie Green", "charlie.green@example.com");
        fakeEvent.addAttendee(attendee);
    }

    @Test
    public void testRemoveAttendeeFromEvent() {
        EventPlanner eventPlanner = new EventPlanner();
        Venue venue = new Venue(1, "Conference Center", "New York Central", 500);
        Event event = new Event(1, "Tech Conference", "A conference about technology", venue);
        eventPlanner.addEvent(event);

        Attendee attendee = new Attendee(3, "Alice Brown", "alice.brown@example.com");
        event.addAttendee(attendee);

        event.removeAttendee(attendee);
        Assert.assertFalse(event.getAttendees().contains(attendee), "Attendee should have been removed from the event");
    }
}
