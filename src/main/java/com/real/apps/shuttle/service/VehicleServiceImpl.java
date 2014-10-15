package com.real.apps.shuttle.service;

import com.real.apps.shuttle.model.Vehicle;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zorodzayi on 14/10/15.
 */
@Service
public class VehicleServiceImpl implements VehicleService
{
    @Override
    public List<Vehicle> list(int skip, int limit) {
        return null;
    }

    @Override
    public Vehicle insert(Vehicle vehicle) {
        return null;
    }

    @Override
    public Vehicle update(Vehicle vehicle) {
        return null;
    }

    @Override
    public Vehicle findOne(ObjectId id) {
        return null;
    }


}
