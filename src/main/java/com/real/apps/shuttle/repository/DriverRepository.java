package com.real.apps.shuttle.repository;

import com.real.apps.shuttle.model.Driver;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by zorodzayi on 14/11/01.
 */
public interface DriverRepository extends PagingAndSortingRepository<Driver,ObjectId> {
    Page<Driver> findByCompanyId(ObjectId id,Pageable pageable);
}
