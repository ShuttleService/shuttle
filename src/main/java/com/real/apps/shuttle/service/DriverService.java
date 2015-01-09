package com.real.apps.shuttle.service;

import com.real.apps.shuttle.model.Driver;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by zorodzayi on 14/10/10.
 */
public interface DriverService {
    Page<Driver> page(int skip, int limit);

    Page<Driver> pageByCompanyId(ObjectId companyId,int skip, int limit);

    Driver insert(Driver driver);

    Driver delete(Driver driver);

    Driver update(Driver driver);

    Driver findOne(ObjectId id);
}
