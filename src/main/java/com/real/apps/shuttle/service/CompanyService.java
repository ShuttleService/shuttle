package com.real.apps.shuttle.service;

import com.real.apps.shuttle.model.Company;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Created by zorodzayi on 14/10/16.
 */
public interface CompanyService {
    Company insert(Company company);

    List<Company> list(int skip, int limit);

    Company update(Company company);

    Company findOne(ObjectId id);

    Company delete(Company company);
}
