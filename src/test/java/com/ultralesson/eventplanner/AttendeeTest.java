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
}
