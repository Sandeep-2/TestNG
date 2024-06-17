package com.ultralesson.eventplanner;

import com.ultralesson.eventplanner.model.Event;
import com.ultralesson.eventplanner.model.Venue;
import com.ultralesson.eventplanner.service.EventPlanner;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class EventPlannerTest {

    private EventPlanner eventPlanner;

    @BeforeMethod
    public void setUp() {
        eventPlanner = new EventPlanner();
    }

    @Test
    public void testCreateEventWithValidDetails() {
        Venue venue = new Venue(1, "Conference Center", "New York Central", 500);
        Event event = new Event(1, "Tech Conference", "A conference about technology", venue);

        eventPlanner.addEvent(event);

        Event retrievedEvent = eventPlanner.getEvents().get(0);
        Assert.assertEquals(retrievedEvent.getName(), "Tech Conference", "Event name should match");
        Assert.assertEquals(retrievedEvent.getDescription(), "A conference about technology", "Event description should match");
        Assert.assertEquals(retrievedEvent.getVenue(), venue, "Event venue should match");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateEventWithoutDescription() {
        Venue venue = new Venue(1, "Conference Center", "New York Central", 500);
        Event event = new Event(2, "Tech Conference", null, venue);

        eventPlanner.addEvent(event);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateEventWithoutVenue() {
        Event event = new Event(3, "Tech Conference", "A conference about technology", null);

        eventPlanner.addEvent(event);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateEventWithoutName() {
        Venue venue = new Venue(1, "Conference Center", "New York Central", 500);
        Event event = new Event(4, "", "A conference about technology", venue);

        eventPlanner.addEvent(event);

        Assert.assertFalse(eventPlanner.getEvents().contains(event), "Event should not be created without a name");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateVenueWithoutAddress() {
        Venue venue = new Venue(1, "Conference Center", null, 500);
        Event event = new Event(2, "Tech Conference", "A conference about technology", venue);

        eventPlanner.addEvent(event);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateVenueWithoutName() {
        Venue venue = new Venue(1, null, "New York Central", 500);
        Event event = new Event(2, "Tech Conference", "A conference about technology", venue);

        eventPlanner.addEvent(event);
    }
}
