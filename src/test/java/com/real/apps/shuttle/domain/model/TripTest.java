package com.real.apps.shuttle.domain.model;

import org.bson.types.ObjectId;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by zorodzayi on 14/11/01.
 */
public class TripTest {
    @Test
    public void shouldBeEqualToItself(){
        Trip trip = new Trip();
        assertTrue(trip.equals(trip));
    }
    @Test
    public void shouldBeEqualIfTheHaveTheSameId(){
        ObjectId id = ObjectId.get();
        Trip trip = new Trip();
        Trip trip1 = new Trip();
        trip.setId(id);
        trip1.setId(id);
        assertTrue(trip.equals(trip1));
        assertTrue(trip1.equals(trip));
    }
    @Test
    public void shouldNotEqualNull(){
        assertFalse(new Trip().equals(null));
    }
    @Test
    public void shouldBeFalseForNullIds(){
        assertFalse(new Trip().equals(new Trip()));
    }
    @Test
    public void shouldNotEqualAnObjectOfADifferentType(){
        ObjectId id = ObjectId.get();
        Trip trip = new Trip();
        Review review = new Review();
        trip.setId(id);
        review.setId(id);
        assertFalse(trip.equals(review));
    }
}
