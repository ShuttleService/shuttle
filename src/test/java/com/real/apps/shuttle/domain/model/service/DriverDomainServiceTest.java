package com.real.apps.shuttle.domain.model.service;

import com.real.apps.shuttle.domain.model.BookedRange;
import com.real.apps.shuttle.domain.model.Driver;
import com.real.apps.shuttle.repository.DriverRepository;
import com.real.apps.shuttle.repository.RepositoryConfig;
import com.real.apps.shuttle.service.ServiceConfig;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by zorodzayi on 15/04/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceConfig.class, RepositoryConfig.class})
public class DriverDomainServiceTest {
    @Autowired
    private DriverDomainServiceImpl service;
    @Autowired
    private DriverRepository repository;
    @Autowired
    private MongoOperations operations;
    private Date from = new Date();
    private Date to = DateUtils.addMinutes(from, 4);
    private Date later = DateUtils.addMinutes(to, 4);
    private BookedRange bookedRange = new BookedRange(from, to);

    @After
    public void cleanUp() {
        operations.dropCollection("driver");
    }

    @Test
    public void shouldInjectDependencies() {
        assertNotNull(service);
        assertNotNull(service.repository);
        assertNotNull(service.bookedRangeService);
    }

    @Test
    public void bookingADriverWhoIsNotBookedShouldAddABookedRangeToHim() {
        Driver driver = new Driver();
        driver = repository.save(driver);
        assertThat(driver.getBookedRanges().size(), is(0));

        boolean booked = service.book(driver, bookedRange);

        assertThat(booked, is(true));
        driver = repository.findOne(driver.getId());
        assertThat(driver.getBookedRanges().size(), is(1));
        assertThat(driver.getBookedRanges().iterator().next(), is(bookedRange));

    }

    @Test
    public void bookingADriverWhoHasADifferentBookedRangeShouldAddAnotherBookedRange() {
        Driver driver = new Driver();
        driver = repository.save(driver);
        service.book(driver, bookedRange);
        driver = repository.findOne(driver.getId());
        assertThat(driver.getBookedRanges().size(), is(1));

        boolean booked = service.book(driver, new BookedRange(to,later));
        assertThat(booked, is(true));
        assertThat(driver.getBookedRanges().size(), is(2));

    }
}
