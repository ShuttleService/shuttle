package com.real.apps.shuttle.domain.model;

import org.bson.types.ObjectId;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by zorodzayi on 14/11/01.
 */
public class UserTest {
    @Test
    public void shouldBeEqualToItself(){
        User user = new User();
        assertTrue(user.equals(user));
    }
    @Test
    public void shouldBeEqualForTheSameId(){
        ObjectId id = ObjectId.get();
        User user = new User();
        User user1 = new User();
        user.setId(id);
        user1.setId(id);
        assertTrue(user.equals(user1));
        assertTrue(user1.equals(user));
    }
    @Test
    public void shouldNotEqualNull(){
        assertFalse(new User().equals(null));
    }
    @Test
    public void shouldBeFalseForNullId(){
        assertFalse(new User().equals(new User()));
    }
    @Test
    public void shouldNotEqualAnObjectOfADifferentType(){
        ObjectId id = ObjectId.get();
        User user = new User();
        Vehicle vehicle = new Vehicle();
        user.setId(id);
        vehicle.setId(id);
        assertFalse(user.equals(vehicle));
    }
}
