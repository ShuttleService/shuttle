package com.real.apps.shuttle.service;

import com.real.apps.shuttle.model.Company;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zorodzayi on 14/10/16.
 */
@Service
public class CompanyServiceImpl implements CompanyService {
    @Override
    public Company insert(Company company) {
        return null;
    }

    @Override
    public List<Company> list(int skip, int limit) {
        return null;
    }

    @Override
    public Company update(Company company) {
        return null;
    }

    @Override
    public Company findOne(ObjectId id) {
        return null;
    }

    @Override
    public Company delete(Company company) {
        return null;
    }
}
