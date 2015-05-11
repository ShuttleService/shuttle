package com.real.apps.shuttle.repository;

import com.real.apps.shuttle.domain.model.Driver;
import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by zorodzayi on 14/11/01.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RepositoryConfig.class)
public class DriverRepositoryTest {

    @Autowired
    private DriverRepository repository;
    @Autowired
    private MongoOperations operations;

    @After
    @Before
    public void cleanUp() {
        operations.dropCollection("driver");
    }

    @Test
    public void shouldInsertADriverInTheDatabase() {
        String name = "Test Driver Name To Be Inserted Into The Database";
        Driver driver = new Driver();
        driver.setFirstName(name);
        Driver saved = repository.save(driver);
        assertThat(saved.getId(), notNullValue());
        Query query = new Query(Criteria.where("firstName").is(name));
        Driver found = operations.findOne(query, Driver.class);
        assertThat(found, notNullValue());
        assertThat(found.getFirstName(), is(name));
        operations.remove(query, Driver.class);
    }

    @Test
    public void shouldFindNNumberOfDrivers() {
        int skip = 0;
        int limit = 2;
        Driver driver = new Driver();
        operations.save(driver);
        operations.save(driver);
        Page<Driver> page = repository.findAll(new PageRequest(skip, limit));
        assertThat(page.getSize(), is(limit));
        operations.dropCollection("driver");
    }

    @Test
    public void shouldAddAReferenceToTheDriverAfterSaving() {
        Driver driver = new Driver();
        repository.save(driver);
        ObjectId id = driver.getId();
        assertThat(id, is(notNullValue()));
        assertThat(driver.getReference(), is(id.toString()));
    }

    @Test
    public void shouldFindOnlyDriversWithAGivenCompanyId() {
        ObjectId id = ObjectId.get();
        ObjectId control = ObjectId.get();

        Driver driverToBeFound = new Driver();
        driverToBeFound.setCompanyId(id);
        driverToBeFound.setEmail("Test Email For Driver To Be Found");
        Driver driverToBeFound2 = new Driver();
        driverToBeFound2.setCompanyId(id);
        driverToBeFound2.setEmail("Test Email For Driver 2 Not To Be Found");

        Driver driverNotToBeFound = new Driver();
        driverNotToBeFound.setCompanyId(control);
        driverNotToBeFound.setEmail("Test Email For Driver Not To Be Found");

        assertNotNull(repository.save(driverNotToBeFound).getId());
        assertNotNull(repository.save(driverToBeFound).getId());
        assertNotNull(repository.save(driverToBeFound2).getId());

        Page<Driver> page = repository.findByCompanyId(id, new PageRequest(0, 100));
        assertThat(page.getTotalElements(), is(2l));
        assertThat(page.getContent().get(0).getCompanyId(), is(id));
        assertThat(page.getContent().get(1).getCompanyId(), is(id));
    }

}
