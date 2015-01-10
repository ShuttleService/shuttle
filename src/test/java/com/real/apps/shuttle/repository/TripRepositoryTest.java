package com.real.apps.shuttle.repository;

import com.real.apps.shuttle.model.Trip;
import org.bson.types.ObjectId;
import org.junit.After;
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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by zorodzayi on 14/11/01.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RepositoryConfig.class)
public class TripRepositoryTest {
    @Autowired
    private TripRepository repository;
    @Autowired
    private MongoOperations template;
    @After
    public void cleanUp(){
        template.dropCollection("trip");
    }

    @Test
    public void shouldInsertATripIntoMongo() {
        String source = "Test Trip Source To Be Inserted";
        Trip trip = new Trip();
        trip.setSource(source);
        Trip saved = repository.save(trip);
        assertThat(saved.getId(), notNullValue());
        Query query = new Query(Criteria.where("source").is(source));
        Trip found = template.findOne(query, Trip.class);
        assertThat(found.getSource(), is(source));
        template.remove(query, Trip.class);
    }

    @Test
    public void shouldFindNNumberOfTrips() {
        int skip = 0;
        int limit = 2;
        Trip trip = new Trip();
        template.save(trip);
        template.save(trip);
        Page<Trip> page = repository.findAll(new PageRequest(skip,limit));
        assertThat(page.getSize(),is(limit));
        template.dropCollection("trip");
    }

    @Test
    public void shouldFindOnlyTripsWithTheGivenCompanyId(){
        ObjectId id = ObjectId.get();
        ObjectId control = ObjectId.get();
        Trip tripToBeFound = new Trip();
        Trip tripToBeFound2 = new Trip();
        Trip tripNotToBeFound = new Trip();

        tripToBeFound.setCompanyId(id);
        tripToBeFound2.setCompanyId(id);
        tripNotToBeFound.setCompanyId(control);

        assertNotNull(repository.save(tripToBeFound).getId());
        assertNotNull(repository.save(tripToBeFound2).getId());
        assertNotNull(repository.save(tripNotToBeFound).getId());

        Page<Trip> actual = repository.findByCompanyId(id,new PageRequest(0,10));
        assertThat(actual.getTotalElements(),is(2l));
        assertThat(actual.getContent().get(0).getCompanyId(),is(id));
        assertThat(actual.getContent().get(1).getCompanyId(),is(id));
    }

    @Test
    public void shouldOnlyFindTripsWithTheGivenUserId(){
        ObjectId id = ObjectId.get();
        ObjectId control = ObjectId.get();

        Trip tripToBeFound = new Trip();
        tripToBeFound.setClientId(id);
        Trip tripToBeFound2 = new Trip();
        tripToBeFound2.setClientId(id);

        Trip tripNotToBeFound = new Trip();
        tripNotToBeFound.setClientId(control);

        assertNotNull(repository.save(tripNotToBeFound).getId());
        assertNotNull(repository.save(tripToBeFound).getId());
        assertNotNull(repository.save(tripToBeFound2).getId());

        Page<Trip> actual = repository.findByClientId(id,new PageRequest(0,10));
        assertThat(actual.getTotalElements(),is(2l));
        assertThat(actual.getContent().get(0).getClientId(),is(id));
        assertThat(actual.getContent().get(1).getClientId(),is(id));
     }
}
