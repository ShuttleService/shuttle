package com.real.apps.shuttle.model;

import static junit.framework.Assert.*;

import org.bson.types.ObjectId;
import org.junit.Test;

/**
 * Created by zorodzayi on 14/11/01.
 */
public class ReviewTest {
    @Test
    public void shouldBeEqualToItself(){
        Review review = new Review();
        assertTrue(review.equals(review));
    }
    @Test
    public void shouldBeEqualIfTheyHaveTheSameId(){
        ObjectId id = ObjectId.get();
        Review review = new Review();
        Review review1 = new Review();
        review.setId(id);
        review1.setId(id);
        assertTrue(review.equals(review1));
        assertTrue(review1.equals(review));
    }
    @Test
    public void shouldNotEqualNull(){
        assertFalse(new Review().equals(null));
    }
    @Test
    public void shouldNotEqualAnObjectOfADifferentType(){
        ObjectId id = ObjectId.get();
        Review review = new Review();
        review.setId(id);
        Vehicle vehicle = new Vehicle();
        vehicle.setId(id);
        assertFalse(review.equals(vehicle));
    }
}
