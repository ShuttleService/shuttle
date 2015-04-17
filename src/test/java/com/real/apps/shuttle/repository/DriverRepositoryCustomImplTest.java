package com.real.apps.shuttle.repository;

import com.real.apps.shuttle.domain.model.BookingRange;
import com.real.apps.shuttle.domain.model.Company;
import com.real.apps.shuttle.domain.model.Driver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by zorodzayi on 15/04/17.
 */
@ContextConfiguration(classes = {RepositoryConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class DriverRepositoryCustomImplTest {

    @Autowired
    private MongoOperations operations;
    @Autowired
    private DriverRepository repository;
    @Autowired
    private CompanyRepository companyRepository;

    @Before
    public void init() {
        assertThat(operations, is(notNullValue()));
        assertThat(repository, is(notNullValue()));
    }

    @After
    public void cleanUp() {
        operations.dropCollection("driver");
        operations.dropCollection("company");
    }

    @Test
    public void shouldFindAvailableDrivesWithinBookingRangeForAGivenCompanyId() {
        Company company = new Company();
        assertThat(company.getId(), is(nullValue()));
        companyRepository.save(company);
        assertThat(company.getId(), is(notNullValue()));

        Driver driver = new Driver();
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date tomorrow = calendar.getTime();

        BookingRange bookingRange = new BookingRange(now, tomorrow);
        driver.setBookingRange(bookingRange);

        assertThat(driver.getId(), is(nullValue()));
        repository.save(driver);
        assertThat(driver.getId(), is(notNullValue()));

        BookingRange range = new BookingRange(tomorrow, null);
        // Driver foundDriver = repository.findAvailableWithinBookingRangeByCompanyId(range, company.getId(), new PageRequest(0, 1)).getContent().get(0);
        Page<Driver> drivers = repository.findAvailableForCompanyId(company.getId(), bookingRange.getFrom(),
                bookingRange.getTo(), new PageRequest(0, 1));
        assertThat(drivers, is(notNullValue()));
        // assertThat(foundDriver.getId(), is(driver.getId()));
    }
}
