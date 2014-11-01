package com.real.apps.shuttle.respository;

import com.real.apps.shuttle.model.Trip;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by zorodzayi on 14/11/01.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RepositoryConfig.class)
public class TripRepositoryTest {
    @Autowired
    private TripRepository repository;
    @Autowired
    private MongoTemplate template;

    @Test
    public void shouldInsertATripIntoMongo() {
        String source = "Test Trip Source To Be Inserted";
        Trip trip = new Trip();
        trip.setSource(source);
        Trip saved = repository.save(trip);
        assertThat(saved.getId(), notNullValue());
        Query query = new Query(Criteria.where("source").is(source));
        Trip found = template.findOne(query,Trip.class);
        assertThat(found.getSource(),is(source));
        template.remove(query,Trip.class);
    }
}
