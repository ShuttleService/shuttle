package com.real.apps.shuttle.domain.model.service;

import com.real.apps.shuttle.domain.model.BookedRange;
import com.real.apps.shuttle.domain.model.Driver;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;

import java.util.Set;

/**
 * Created by zorodzayi on 15/04/18.
 */
public interface DriverDomainService {
    boolean book(Driver driver, BookedRange bookedRange);

    Set<Driver> bookableDrivers(ObjectId companyId, Pageable pageable, BookedRange bookedRange);
}
