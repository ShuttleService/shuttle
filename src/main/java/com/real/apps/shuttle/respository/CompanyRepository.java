package com.real.apps.shuttle.respository;

import com.real.apps.shuttle.model.Company;
import org.bson.types.ObjectId;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by zorodzayi on 14/10/31.
 */
public interface CompanyRepository extends PagingAndSortingRepository<Company,ObjectId> {
}
