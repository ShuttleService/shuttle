package com.real.apps.shuttle.repository;

import com.real.apps.shuttle.domain.model.BookingRange;
import com.real.apps.shuttle.domain.model.Driver;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;

import static org.springframework.data.mongodb.core.query.Criteria.*;
import static org.springframework.data.mongodb.core.query.CriteriaDefinition.*;

import org.springframework.data.mongodb.core.query.Query;

import static org.springframework.data.mongodb.core.query.Query.*;

/**
 * Created by zorodzayi on 15/04/17.
 */
public class DriverRepositoryCustomImpl implements DriverRepositoryCustom {
    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public Page<Driver> findAvailableWithinBookingRangeByCompanyId(BookingRange bookingRange, ObjectId id, Pageable pageable) {
        Query query = query(where("bookingRange.from").gt(bookingRange.getTo()));

        return null;
    }
}
