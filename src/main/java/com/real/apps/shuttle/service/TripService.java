package com.real.apps.shuttle.service;

import com.real.apps.shuttle.model.Trip;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by zorodzayi on 14/10/11.
 */
public interface TripService {
    Page<Trip> list(int skip, int limit);

    Trip insert(Trip trip);

    Trip delete(Trip trip);

    Trip update(Trip trip);

    Trip findOne(ObjectId id);
}
