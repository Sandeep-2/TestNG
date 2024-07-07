package com.ultralesson.eventplanner;

import com.ultralesson.eventplanner.model.Event;
import com.ultralesson.eventplanner.model.Venue;
import com.ultralesson.eventplanner.service.EventPlanner;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

public class EventCreationTest {

    private EventPlanner eventPlanner;

    @BeforeMethod
    public void setUp() {
        eventPlanner = new EventPlanner();
    }

    @DataProvider(name = "eventDataProvider")
    public Object[][] eventDataProvider() {
        return new Object[][]{
                {01,"Event 1", "Description 1",new Venue(3, "Phantom Venue", "Nowhere", 0), true},
                {02,"Event 2", "",new Venue(3, "Phantom Venue", "Nowhere", 0), false},  // Invalid case: empty description
                {03,"", "Description 3",new Venue(3, "Phantom Venue", "Nowhere", 0), false},  // Invalid case: empty name
                {04,"Event 4","Description 4", null, false},  // Invalid case: null description
                {05,null, "Description 5",null, false},  // Invalid case: null name
                {06,"Event 6", "Description 6",new Venue(3, "Phantom Venue", "Nowhere", 0), true}
        };
    }

    @Test(dataProvider = "eventDataProvider")
    public void testCreateEvent(int id, String name, String description, Venue venue, boolean expectedSuccess) {
        try {
            Event event = new Event(id,name, description,venue);
            boolean success = eventPlanner.createEvent(event);
            Assert.assertEquals(success, expectedSuccess, "Event creation success status did not match expected value.");
        } catch (IllegalArgumentException e) {
            Assert.assertFalse(expectedSuccess, "Expected failure but event creation succeeded.");
        }
    }

    @Factory
    public Object[] createInstances() {
        return new Object[]{
                new Event(01,"Event 1", "Description 1",new Venue(3, "Phantom Venue1", "Nowhere", 0)),
                new Event(02,"Event 2", "Description 2",new Venue(1, "Phantom Venue2", "Nowhere", 0)),
                new Event(03,"Event 3", "Description 3",new Venue(2, "Phantom Venue3", "Nowhere", 0)),
                new Event(04,"Event 4", "Description 4",new Venue(60, "Phantom Venue4", "Nowhere", 0)),
                new Event(05,"Event 5", "Description 5",new Venue(8, "Phantom Venue5", "Nowhere", 0)),
        };
    }
}
