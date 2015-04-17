package com.real.apps.shuttle.repository;

import com.real.apps.shuttle.domain.model.Driver;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;

/**
 * Created by zorodzayi on 14/11/01.
 */
public interface DriverRepository extends PagingAndSortingRepository<Driver, ObjectId>, DriverRepositoryCustom {
    Page<Driver> findByCompanyId(ObjectId id, Pageable pageable);

    @Query("{  companyId:?0,$or:[{ 'bookingRange.from':{$lte:?1} },{   'bookingRange.to':{$gte:?2}  }]  }")
    Page<Driver> findAvailableForCompanyId(ObjectId id, Date from, Date to, Pageable pageable);
}