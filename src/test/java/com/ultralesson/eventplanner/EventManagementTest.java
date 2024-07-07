package com.ultralesson.eventplanner;

import com.ultralesson.eventplanner.model.Event;
import com.ultralesson.eventplanner.model.Venue;
import com.ultralesson.eventplanner.service.EventPlanner;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class EventManagementTest {

    private final EventPlanner eventPlanner;
    private final Event event;
    private final Venue venue;

    // Constructor for dependency injection
    public EventManagementTest(EventPlanner eventPlanner, Event event, Venue venue) {
        this.eventPlanner = eventPlanner;
        this.event = event;
        this.venue = venue;
    }

    @BeforeMethod
    public void setUp() {
        // Any additional setup if needed
    }

    @Test
    public void testEventCreation() {
        eventPlanner.addEvent(event);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(eventPlanner.getEvents().contains(event), "Event should be in the list after creation");
        softAssert.assertEquals(event.getName(), "Tech Conference", "Event name should match the given name");
        softAssert.assertEquals(event.getVenue(), venue, "Event venue should match the given venue");
        softAssert.assertAll();
    }

    @Test(dependsOnMethods = "testEventCreation")
    public void testUpdateEvent() {
        eventPlanner.addEvent(event);
        event.setName("Tech Conference Updated");
        event.setDescription("A conference about technology - Updated");
        eventPlanner.updateEvent(event);

        Event updatedEvent = eventPlanner.getEvents().get(event.getId());
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(updatedEvent, "Updated event should not be null");
        softAssert.assertEquals(updatedEvent.getName(), "Tech Conference Updated", "Event name should be updated");
        softAssert.assertEquals(updatedEvent.getDescription(), "A conference about technology - Updated", "Event description should be updated");
        softAssert.assertEquals(updatedEvent.getVenue(), venue, "Event venue should remain unchanged");
        softAssert.assertAll();
    }

    @Test(dependsOnMethods = "testEventCreation")
    public void testEventDetailsIntegrity() {
        eventPlanner.addEvent(event);

        Event retrievedEvent = eventPlanner.getEvents().stream()
                .filter(e -> e.getId() == event.getId())
                .findFirst()
                .orElse(null);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(retrievedEvent, "Event should exist after addition.");
        boolean eventDataIntact = retrievedEvent.getName().equals("Tech Conference") &&
                retrievedEvent.getDescription().equals("A conference about technology") &&
                retrievedEvent.getVenue().equals(venue);
        softAssert.assertTrue(eventDataIntact, "Event details should remain intact post addition: name, description, and venue.");
        softAssert.assertAll();
    }
}
