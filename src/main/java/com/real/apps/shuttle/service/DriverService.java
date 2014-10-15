package com.real.apps.shuttle.service;

import com.real.apps.shuttle.model.Driver;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Created by zorodzayi on 14/10/10.
 */
public interface DriverService {
    List<Driver> list(int skip, int limit);

    Driver insert(Driver driver);

    Driver delete(Driver driver);

    Driver update(Driver driver);

    Driver findOne(ObjectId id);
}
