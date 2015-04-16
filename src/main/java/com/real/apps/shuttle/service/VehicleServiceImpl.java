package com.real.apps.shuttle.service;

import com.real.apps.shuttle.domain.model.Vehicle;
import com.real.apps.shuttle.repository.VehicleRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Created by zorodzayi on 14/10/15.
 */
@Service
public class VehicleServiceImpl implements VehicleService
{
    @Autowired
    private VehicleRepository repository;
    @Override
    public Page<Vehicle> page(int skip, int limit) {
        return repository.findAll(new PageRequest(skip,limit));
    }

    @Override
    public Page<Vehicle> pageByCompanyId(ObjectId companyId, int skip, int limit) {
        return repository.findByCompanyId(companyId,new PageRequest(skip,limit));
    }

    @Override
    public Vehicle insert(Vehicle vehicle) {
        return repository.save(vehicle);
    }

    @Override
    public Vehicle update(Vehicle vehicle) {
        return null;
    }

    @Override
    public Vehicle findOne(ObjectId id) {
        return null;
    }

    public void setRepository(VehicleRepository repository) {
        this.repository = repository;
    }
}
