package com.real.apps.shuttle.service;

import com.real.apps.shuttle.domain.model.Trip;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;

/**
 * Created by zorodzayi on 14/10/11.
 */
public interface TripService {
    Page<Trip> page(int skip, int limit);

    Page<Trip> pageByCompanyId(ObjectId companyId, int skip, int limit);

    Page<Trip> pageByUserId(ObjectId userId, int skip, int limit);

    Trip insert(Trip trip);

    Trip delete(Trip trip);

    Trip update(Trip trip);

    Trip findOne(ObjectId id);

}
