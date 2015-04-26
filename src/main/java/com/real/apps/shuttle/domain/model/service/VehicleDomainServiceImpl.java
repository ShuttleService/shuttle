package com.real.apps.shuttle.domain.model.service;

import com.real.apps.shuttle.domain.model.BookedRange;
import com.real.apps.shuttle.domain.model.Vehicle;
import com.real.apps.shuttle.repository.VehicleRepository;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by zorodzayi on 15/04/26.
 */
@Service
public class VehicleDomainServiceImpl implements VehicleDomainService {
    private Logger logger = Logger.getLogger(VehicleDomainServiceImpl.class);
    @Autowired
    VehicleRepository repository;
    @Autowired
    BookedRangeService bookedRangeService;


    @Override
    public Set<Vehicle> bookable(ObjectId companyId, Pageable pageable, BookedRange bookedRange) {
        logger.debug(String.format("Finding Bookable Vehicles {companyId:%s,pageable:%s,bookedRange:%s}", companyId, pageable, bookedRange));
        Page<Vehicle> page = repository.findByCompanyId(companyId, pageable);
        logger.debug(String.format("{Page:%s}", page));
        Set<Vehicle> bookable = new HashSet<>();

        for (Vehicle vehicle : page.getContent()) {
            if (bookedRangeService.availableForBooking(vehicle.getBookedRanges(), bookedRange)) {
                bookable.add(vehicle);
            }
        }

        return bookable;
    }
}
