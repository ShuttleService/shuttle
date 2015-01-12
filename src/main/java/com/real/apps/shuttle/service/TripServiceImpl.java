package com.real.apps.shuttle.service;

import com.real.apps.shuttle.model.Trip;
import com.real.apps.shuttle.repository.TripRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Created by zorodzayi on 14/10/11.
 */
@Service
public class TripServiceImpl implements TripService {
    @Autowired
    private TripRepository repository;

    @Override
    public Page<Trip> page(int skip, int limit) {
        return repository.findAll(new PageRequest(skip,limit));
    }

    @Override
    public Page<Trip> pageByCompanyId(ObjectId companyId,int skip,int limit) {
        return repository.findByCompanyId(companyId,new PageRequest(skip,limit));
    }

    @Override
    public Page<Trip> pageByUserId(ObjectId userId, int skip, int limit) {
        return repository.findByClientId(userId,new PageRequest(skip,limit));
    }

    @Override
    public Trip insert(Trip trip) {
        return repository.save(trip);
    }

    @Override
    public Trip delete(Trip trip) {
        return null;
    }

    @Override
    public Trip update(Trip trip) {
        return repository.save(trip);
    }

    @Override
    public Trip findOne(ObjectId id) {
        return null;
    }

    public void setRepository(TripRepository repository) {
        this.repository = repository;
    }
}
