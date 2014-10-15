package com.real.apps.shuttle.service;

import com.real.apps.shuttle.model.Review;
import com.real.apps.shuttle.model.Trip;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zorodzayi on 14/10/11.
 */
@Service
public class TripServiceImpl implements TripService {
    @Override
    public List<Trip> list(int skip, int limit) {
        return null;
    }

    @Override
    public Trip insert(Trip trip) {
        return null;
    }

    @Override
    public Trip delete(Trip trip) {
        return null;
    }

    @Override
    public Trip update(Trip trip) {
        return null;
    }

    @Override
    public Trip findOne(ObjectId id) {
        return null;
    }


}
