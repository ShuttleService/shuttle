package com.real.apps.shuttle.repository;

import com.real.apps.shuttle.domain.model.Vehicle;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by zorodzayi on 14/11/01.
 */
public interface VehicleRepository extends PagingAndSortingRepository<Vehicle,ObjectId>{
    Page<Vehicle> findByCompanyId(ObjectId companyId,Pageable pageable);
}
