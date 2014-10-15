package com.real.apps.shuttle.service;

import com.real.apps.shuttle.model.Driver;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zorodzayi on 14/10/10.
 */
@Service
public class DriverServiceImpl implements DriverService {
    @Override
    public List<Driver> list(int skip, int limit) {
        return null;
    }

    @Override
    public Driver insert(Driver driver) {
        return null;
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


}
