package com.real.apps.shuttle.respository;

import com.real.apps.shuttle.model.Vehicle;
import org.bson.types.ObjectId;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by zorodzayi on 14/11/01.
 */
public interface VehicleRepository extends PagingAndSortingRepository<Vehicle,ObjectId>{
}
