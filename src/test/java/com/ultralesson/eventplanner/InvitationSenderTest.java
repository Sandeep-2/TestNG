package com.ultralesson.eventplanner;

import com.ultralesson.eventplanner.model.Attendee;
import com.ultralesson.eventplanner.model.Event;
import com.ultralesson.eventplanner.model.Schedule;
import com.ultralesson.eventplanner.model.Venue;
import com.ultralesson.eventplanner.service.InvitationSender;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InvitationSenderTest {

    private InvitationSender invitationSender;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeMethod
    public void setUp() {
        invitationSender = new InvitationSender();
        System.setOut(new PrintStream(outContent));
    }

    @AfterMethod
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    public void testSendInvitationsSuccessfully() {
        Event event = createTestEvent();
        invitationSender.sendInvitations(event);

        String consoleOutput = outContent.toString();
        Assert.assertTrue(consoleOutput.contains("Sending invitation to john.doe@example.com"), "Console output should contain invitation to John Doe");
        Assert.assertTrue(consoleOutput.contains("Sending invitation to jane.smith@example.com"), "Console output should contain invitation to Jane Smith");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testSendInvitationsNoAttendees() {
        Event event = createTestEventWithoutAttendees();
        invitationSender.sendInvitations(event);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testSendInvitationsNonExistentEvent() {
        invitationSender.sendInvitations(null);
    }

    private Event createTestEvent() {
        Venue venue = new Venue(1, "Conference Center", "New York Central", 500);
        List<Attendee> attendees = new ArrayList<>();
        attendees.add(new Attendee(1, "John Doe", "john.doe@example.com"));
        attendees.add(new Attendee(2, "Jane Smith", "jane.smith@example.com"));
        Event event = new Event(1, "Tech Conference", "A conference about technology", venue);
        Schedule schedule = new Schedule(1, event, venue, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1).plusHours(2));
        event.addAttendees(attendees);
        return event;
    }

    private Event createTestEventWithoutAttendees() {
        Venue venue = new Venue(1, "Conference Center", "New York Central", 500);
        List<Attendee> attendees = new ArrayList<>();
        Event event = new Event(1, "Tech Conference", "A conference about technology", venue);
        event.addAttendees(attendees);
        Schedule schedule = new Schedule(1, event, venue, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1).plusHours(2));
        return event;
    }
}
