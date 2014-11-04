package com.real.apps.shuttle.service;

import com.real.apps.shuttle.model.Review;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;

/**
 * Created by zorodzayi on 14/10/11.
 */
public interface ReviewService {
    Page<Review> list(int skip, int limit);

    Review insert(Review review);

    Review delete(Review review);

    Review update(Review review);

    Review findOne(ObjectId id);
}
