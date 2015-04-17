package com.real.apps.shuttle.repository;

import com.real.apps.shuttle.domain.model.BookingRange;
import com.real.apps.shuttle.domain.model.Driver;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by zorodzayi on 15/04/17.
 */
public interface DriverRepositoryCustom {
    Page<Driver> findAvailableWithinBookingRangeByCompanyId(BookingRange bookingRange, ObjectId id, Pageable pageable);
}