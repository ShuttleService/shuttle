package com.real.apps.shuttle.domain.model.service;

import com.real.apps.shuttle.domain.model.BookedRange;
import com.real.apps.shuttle.domain.model.Driver;
import com.real.apps.shuttle.repository.DriverRepository;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by zorodzayi on 15/04/18.
 */
@Service
public class DriverDomainServiceImpl implements DriverDomainService {
    @Autowired
    BookedRangeService bookedRangeService;
    @Autowired
    DriverRepository repository;
    Logger logger = Logger.getLogger(DriverDomainService.class);

    @Override
    public boolean book(Driver driver, BookedRange bookedRange) {

        return true;
    }

    @Override
    public Set<Driver> bookableDrivers(ObjectId companyId, Pageable pageable, BookedRange bookedRange) {
        logger.debug(String.format("Finding The BookableDrivers {companyId:%s, Pageable:%s, BookedRange:%s}", companyId, pageable, bookedRange));
        Set<Driver> bookable = new HashSet<>();
        Page<Driver> page = repository.findByCompanyId(companyId, pageable);

        logger.debug(String.format("{Page Returned From Repository %s, containing %d drivers, Content Size:%d }", page, page.getTotalElements(), page.getContent().size()));

        for (Driver driver : page.getContent()) {

            if (bookedRangeService.availableForBooking(driver.getBookedRanges(), bookedRange)) {
                logger.debug(String.format("Adding driver %s to the bookable list ", driver.getFirstName()));
                bookable.add(driver);
            }
        }

        return bookable;
    }


    public void bookedRangeService(BookedRangeService bookedRangeService) {
        this.bookedRangeService = bookedRangeService;
    }

    public void repository(DriverRepository repository) {
        this.repository = repository;
    }
}
