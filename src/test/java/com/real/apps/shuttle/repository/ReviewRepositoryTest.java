package com.real.apps.shuttle.repository;

import com.real.apps.shuttle.domain.model.Review;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by zorodzayi on 14/11/01.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RepositoryConfig.class)
public class ReviewRepositoryTest {
    @Autowired
    private ReviewRepository repository;
    @Autowired
    private MongoOperations operations;

    @Test
    public void shouldInsertAReviewInTheDatabase() {
        String[] reviews = new String[]{"Test Review Text To Be Inserted Into The Database"};
        Review review = new Review();
        review.setReviews(reviews);
        Review saved = repository.save(review);
        assertThat(saved.getId(), notNullValue());
        Query query = new Query((Criteria.where("reviews").is(reviews)));
        Review found = operations.findOne(query, Review.class);
        assertThat(found, notNullValue());
        operations.remove(query, Review.class);
    }

    @Test
    public void shouldFindNNumberOfReviews() {
        int skip = 0;
        int limit = 2;

        Review review = new Review();
        operations.save(review);
        operations.save(review);
        Page<Review> page = repository.findAll(new PageRequest(skip, limit));
        assertThat(page.getSize(), is(limit));
        operations.dropCollection("review");
    }
}
