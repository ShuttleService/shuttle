package com.real.apps.shuttle.domain.model;

import org.bson.types.ObjectId;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by zorodzayi on 14/11/01.
 */
public class CompanyTest {
    @Test
    public void shouldBeEqualIfTheyHaveTheSameId() {
        ObjectId id = new ObjectId();
        Company company = new Company();
        company.setId(id);
        Company company1 = new Company();
        company1.setId(id);

        assertTrue(company.equals(company1));
        assertTrue(company1.equals(company));
        assertTrue(company.equals(company));
    }

    @Test
    public void shouldBeFalseForNullId() {
        Company company = new Company();
        ObjectId id = ObjectId.get();
        Company company1 = new Company();
        company1.setId(id);

        assertFalse(company.equals(company1));
        assertFalse(company1.equals(company));
    }

    @Test
    public void shouldBeFalseWhenComparedWithNull() {
        assertFalse(new Company().equals(null));
    }

    @Test
    public void shouldBeFalseWhenComparedWithAnObjectFromADifferentClass() {
        Company company = new Company();
        ObjectId id = ObjectId.get();
        Trip trip = new Trip();
        trip.setId(id);
        company.setId(id);
        assertFalse(company.equals(trip));
    }

    @Test
    public void shouldBeEqualToItself() {
        Company company = new Company();
        assertTrue(company.equals(company));
    }

}
