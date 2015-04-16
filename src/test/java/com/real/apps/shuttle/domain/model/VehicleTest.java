package com.real.apps.shuttle.domain.model;

import org.bson.types.ObjectId;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by zorodzayi on 14/11/01.
 */
public class VehicleTest {
    @Test
    public void shouldBeEqualToItself(){
        Vehicle vehicle = new Vehicle();
        assertTrue(vehicle.equals(vehicle));
    }
    @Test
    public void shouldEqualAVehicleWithTheSameId(){
        ObjectId id = ObjectId.get();
        Vehicle vehicle = new Vehicle();
        Vehicle vehicle1 = new Vehicle();
        vehicle.setId(id);
        vehicle1.setId(id);
        assertTrue(vehicle.equals(vehicle1));
        assertTrue(vehicle1.equals(vehicle));
    }
    @Test
    public void shouldBeFalseWhenBothHaveNullIds(){
        assertFalse(new Vehicle().equals(new Vehicle()));
    }
    @Test
    public void shouldBeFalseForAnObjectOfADifferentType(){
        ObjectId id = ObjectId.get();
        Vehicle vehicle = new Vehicle();
        Company company = new Company();
        vehicle.setId(id);
        company.setId(id);
        assertFalse(vehicle.equals(company));
    }
    @Test
    public void shouldBeFalseForANull(){
        assertFalse(new Vehicle().equals(null));
    }
}
