package com.real.apps.shuttle.repository;

import com.real.apps.shuttle.domain.model.Review;
import org.bson.types.ObjectId;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by zorodzayi on 14/11/01.
 */
public interface ReviewRepository extends PagingAndSortingRepository<Review,ObjectId> {
}
