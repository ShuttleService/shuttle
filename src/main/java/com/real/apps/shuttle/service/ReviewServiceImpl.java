package com.real.apps.shuttle.service;

import com.real.apps.shuttle.model.Review;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zorodzayi on 14/10/11.
 */
@Service
public class ReviewServiceImpl implements ReviewService {
    @Override
    public List<Review> list(int skip, int limit) {
        return null;
    }

    @Override
    public Review insert(Review review) {
        return null;
    }

    @Override
    public Review delete(Review review) {
        return null;
    }

    @Override
    public Review update(Review review) {
        return null;
    }

    @Override
    public Review findOne(ObjectId id) {
        return null;
    }
}