package com.real.apps.shuttle.respository;

import com.real.apps.shuttle.model.Review;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
        assertThat(saved.getId(),notNullValue());
        Query query = new Query(Criteria.where("id").is(saved.getId()));
        Review found = operations.findOne(query,Review.class);
        assertThat(found,notNullValue());
        operations.remove(query,Review.class);
    }
}
