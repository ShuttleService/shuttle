package com.real.apps.shuttle.respository;

import com.real.apps.shuttle.model.Trip;
import org.bson.types.ObjectId;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by zorodzayi on 14/11/01.
 */
public interface TripRepository extends PagingAndSortingRepository<Trip,ObjectId> {
}
