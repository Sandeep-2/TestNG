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

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAssignVenueToEvent() {
        // Arrange
        EventPlanner eventPlanner = new EventPlanner();
        Venue venue = new Venue(1, "Test Venue", "Test Address", 100);
        Event event = new Event(1, "Test Event", "Event Description", null); // Initially, no venue assigned

        // Act
        event.setVenue(venue);
        eventPlanner.addEvent(event);

        // Assert
        Event retrievedEvent = eventPlanner.getEvents().get(0);
        Assert.assertEquals(retrievedEvent.getVenue(), venue, "Assigned venue should match the event’s venue");
    }

    @Test
    public void testAddUpdateRemoveVenue() {
        // Arrange
        EventPlanner eventPlanner = new EventPlanner();
        Venue venue = new Venue(2, "Another Venue", "Another Address", 200);

        // Act: Add the venue
        eventPlanner.addVenue(venue);

        // Assert: Check addition
        Assert.assertTrue(eventPlanner.getVenues().contains(venue), "Venue should be added to the list");

        // Act: Update the venue
        Venue updatedVenue = new Venue(2, "Updated Venue", "Updated Address", 250);
        eventPlanner.updateVenue(updatedVenue,venue);

        // Assert: Check update
        Venue retrievedVenue = eventPlanner.getVenues().stream()
                .filter(v -> v.getId() == updatedVenue.getId())
                .findFirst()
                .orElse(null);
        Assert.assertEquals(retrievedVenue, updatedVenue, "Venue should be updated");

        // Act: Remove the venue
        eventPlanner.removeVenue(updatedVenue);

        // Assert: Check removal
        Assert.assertFalse(eventPlanner.getVenues().contains(updatedVenue), "Venue should be removed from the list");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAssignNonExistentVenueToEvent() {
        EventPlanner eventPlanner = new EventPlanner();
        Venue nonExistentVenue = new Venue(3, "Phantom Venue", "Nowhere", 0);
        Event event = new Event(1, "Test Event", "Event Description", null);

        // This should throw an exception since the venue does not exist in the event planner's venue list
        eventPlanner.assignVenueToEvent(nonExistentVenue, event);
    }
}
