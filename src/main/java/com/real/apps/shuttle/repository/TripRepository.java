package com.real.apps.shuttle.repository;

import com.real.apps.shuttle.model.Trip;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by zorodzayi on 14/11/01.
 */
public interface TripRepository extends PagingAndSortingRepository<Trip,ObjectId> {
    Page<Trip> findByCompanyId(ObjectId companyId,Pageable pageable);
    Page<Trip> findByClientId(ObjectId clientId,Pageable pageable);
}
