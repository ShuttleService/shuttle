package com.real.apps.shuttle.model;

import com.real.apps.shuttle.model.Company;
import com.real.apps.shuttle.model.Driver;
import org.bson.types.ObjectId;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by zorodzayi on 14/11/01.
 */
public class DriverTest {

    @Test
    public void shouldBeEqualToItself(){
        Driver driver = new Driver();
        assertTrue(driver.equals(driver));
    }
    @Test
    public void shouldBeEqualIfTheyHaveTheSameId(){
        ObjectId id = ObjectId.get();
        Driver driver = new Driver();
        Driver driver1 = new Driver();
        driver.setId(id);
        driver1.setId(id);
        assertTrue(driver.equals(driver1));
        assertTrue(driver1.equals(driver));
        assertTrue(driver1.equals(driver1));
    }

    @Test
    public void shouldBeFalseForNull(){
        assertFalse(new Driver().equals(null));
    }

    @Test
    public void shouldBeFalseForNullId(){
        assertFalse(new Driver().equals(new Driver()));
    }
    @Test
    public void shouldBeFalseWithObjectOfADifferentClass(){
        ObjectId id = ObjectId.get();
        Company company = new Company();
        company.setId(id);
        Driver driver = new Driver();
        driver.setId(id);
        assertFalse(driver.equals(company));
    }
}
