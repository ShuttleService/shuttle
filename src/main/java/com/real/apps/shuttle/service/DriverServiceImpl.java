package com.real.apps.shuttle.service;

import com.real.apps.shuttle.model.Driver;
import com.real.apps.shuttle.respository.DriverRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zorodzayi on 14/10/10.
 */
@Service
public class DriverServiceImpl implements DriverService {
    @Autowired
    private DriverRepository repository;
    @Override
    public List<Driver> list(int skip, int limit) {
        return null;
    }

    @Override
    public Driver insert(Driver driver) {
        return repository.save(driver);
    }

    @Override
    public Driver delete(Driver driver) {
        return null;
    }

    @Override
    public Driver update(Driver driver) {
        return null;
    }

    @Override
    public Driver findOne(ObjectId id) {
        return null;
    }

     public void setRepository(DriverRepository repository) {
        this.repository = repository;
    }
}
