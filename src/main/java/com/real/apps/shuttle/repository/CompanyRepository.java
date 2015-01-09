package com.real.apps.shuttle.repository;

import com.real.apps.shuttle.model.Company;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by zorodzayi on 14/10/31.
 */
public interface CompanyRepository extends PagingAndSortingRepository<Company,ObjectId> {
    Page<Company> findByAgentId(ObjectId agentId,Pageable pageable);
}
