package com.real.apps.shuttle.domain.model.service;

import com.real.apps.shuttle.domain.model.BookedRange;
import com.real.apps.shuttle.domain.model.Vehicle;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;

import java.util.Set;

/**
 * Created by zorodzayi on 15/04/26.
 */
public interface VehicleDomainService {
    Set<Vehicle> bookable(ObjectId companyId, Pageable pageable, BookedRange bookedRange);

    boolean book(Vehicle vehicle, BookedRange bookedRange);
}
