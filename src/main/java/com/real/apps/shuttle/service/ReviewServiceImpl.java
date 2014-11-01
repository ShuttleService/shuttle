package com.real.apps.shuttle.service;

import com.real.apps.shuttle.model.Review;
import com.real.apps.shuttle.respository.ReviewRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zorodzayi on 14/10/11.
 */
@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository repository;

    @Override
    public List<Review> list(int skip, int limit) {
        return null;
    }

    @Override
    public Review insert(Review review) {
        return repository.save(review);
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

    public void setRepository(ReviewRepository repository) {
        this.repository = repository;
    }
}