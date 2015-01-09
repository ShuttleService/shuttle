package com.real.apps.shuttle.service;

import com.real.apps.shuttle.model.Vehicle;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by zorodzayi on 14/10/15.
 */
public interface VehicleService {
    Page<Vehicle> page(int skip, int limit);

    Page<Vehicle> pageByCompanyId(ObjectId companyId,int skip, int limit);

    Vehicle insert(Vehicle vehicle);

    Vehicle update(Vehicle vehicle);

    Vehicle findOne(ObjectId id);
}
