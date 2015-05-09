package com.real.apps.shuttle.service;

import com.real.apps.shuttle.domain.model.Driver;
import com.real.apps.shuttle.domain.model.Trip;
import com.real.apps.shuttle.domain.model.Vehicle;
import com.real.apps.shuttle.domain.model.service.DriverDomainService;
import com.real.apps.shuttle.domain.model.service.VehicleDomainService;
import com.real.apps.shuttle.repository.DriverRepository;
import com.real.apps.shuttle.repository.TripRepository;
import com.real.apps.shuttle.repository.VehicleRepository;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


/**
 * Created by zorodzayi on 14/10/11.
 */
@Service
public class TripServiceImpl implements TripService {
    @Autowired
    private TripRepository repository;
    @Autowired
    VehicleDomainService vehicleDomainService;
    @Autowired
    DriverDomainService driverDomainService;
    @Autowired
    VehicleRepository vehicleRepository;
    @Autowired
    DriverRepository driverRepository;
    private static final Logger LOGGER = Logger.getLogger(TripServiceImpl.class);
    @Override
    public Page<Trip> page(int skip, int limit) {
        return repository.findAll(new PageRequest(skip, limit));
    }

    @Override
    public Page<Trip> pageByCompanyId(ObjectId companyId, int skip, int limit) {
        return repository.findByCompanyId(companyId, new PageRequest(skip, limit));
    }

    @Override
    public Page<Trip> pageByUserId(ObjectId userId, int skip, int limit) {
        return repository.findByClientId(userId, new PageRequest(skip, limit));
    }

    @Override
    public Trip insert(Trip trip) {
        LOGGER.debug(String.format("{DriverRepository:%s,VehicleRepository:%s}",driverRepository,vehicleRepository));
        Driver driver = driverRepository.findOne(trip.getDriverId());
        Vehicle vehicle = vehicleRepository.findOne(trip.getVehicleId());

        driverDomainService.book(driver,trip.getBookedRange());
        vehicleDomainService.book(vehicle,trip.getBookedRange());

        return repository.save(trip);
    }

    @Override
    public Trip delete(Trip trip) {
        return null;
    }

    @Override
    public Trip update(Trip trip) {
        return repository.save(trip);
    }

    @Override
    public Trip findOne(ObjectId id) {
        return null;
    }

    public void setRepository(TripRepository repository) {
        this.repository = repository;
    }
}
