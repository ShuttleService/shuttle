package com.real.apps.shuttle.service;

import com.real.apps.shuttle.model.Driver;
import com.real.apps.shuttle.repository.DriverRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Created by zorodzayi on 14/10/10.
 */
@Service
public class DriverServiceImpl implements DriverService {
    @Autowired
    private DriverRepository repository;
    @Override
    public Page<Driver> list(int skip, int limit) {
        return repository.findAll( new PageRequest(skip,limit));
    }

    @Override
    public Page<Driver> pageByCompanyId(ObjectId companyId,int skip,int limit) {
        return repository.findByCompanyId(companyId,new PageRequest(skip,limit));
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
