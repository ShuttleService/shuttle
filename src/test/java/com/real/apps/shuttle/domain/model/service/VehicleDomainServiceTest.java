package com.real.apps.shuttle.domain.model.service;

import com.real.apps.shuttle.domain.model.BookedRange;
import com.real.apps.shuttle.domain.model.Vehicle;
import com.real.apps.shuttle.repository.RepositoryConfig;
import com.real.apps.shuttle.repository.VehicleRepository;
import com.real.apps.shuttle.service.ServiceConfig;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.awt.print.Book;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by zorodzayi on 15/04/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceConfig.class, RepositoryConfig.class})
public class VehicleDomainServiceTest {
    @Autowired
    private VehicleDomainServiceImpl service;
    @Autowired
    private VehicleRepository repository;
    @Autowired
    private MongoOperations operations;

    private Date from = new Date();
    private Date to = DateUtils.addMinutes(from, 1);
    private Date later = DateUtils.addMinutes(to, 1);

    private BookedRange bookedRange = new BookedRange(from, to);

    @After
    public void cleanUp() {
        operations.dropCollection("vehicle");
    }

    @Test
    public void shouldInjectDependencies() {
        assertNotNull(service);
        assertNotNull(service.repository);
        assertNotNull(service.bookedRangeService);
    }

    @Test
    public void aVehicleThatIsNotBookedShouldHaveABookedRangeAfterABooking() {
        Vehicle vehicle = repository.save(new Vehicle());
        assertThat(vehicle.getBookedRanges().size(), is(0));
        boolean booked = service.book(vehicle, bookedRange);

        assertThat(booked, is(true));
        vehicle = repository.findOne(vehicle.getId());
        assertThat(vehicle.getBookedRanges().size(), is(1));
        assertThat(vehicle.getBookedRanges().iterator().next(), is(bookedRange));
    }

    @Test
    public void aVehicleThatIsAlreadyBookedShouldAddAnotherBookingAfterASuccessfulBooking() {
        Vehicle vehicle = repository.save(new Vehicle());

        boolean booked = service.book(vehicle, bookedRange);

        assertThat(booked, is(true));
        vehicle = repository.findOne(vehicle.getId());
        assertThat(vehicle.getBookedRanges().size(), is(1));

        BookedRange bookedRange = new BookedRange(to, later);
        booked = service.book(vehicle, bookedRange);
        assertThat(booked, is(true));
        vehicle = repository.findOne(vehicle.getId());
        assertThat(vehicle.getBookedRanges().size(), is(2));
    }

}
