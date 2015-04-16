package com.real.apps.shuttle.service;

import com.real.apps.shuttle.domain.model.Trip;
import com.real.apps.shuttle.repository.RepositoryConfig;
import com.real.apps.shuttle.repository.TripRepository;
import org.bson.types.ObjectId;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

/**
 * Created by zorodzayi on 14/11/01.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceConfig.class, RepositoryConfig.class})
public class TripServiceTest {
    @Autowired
    private TripServiceImpl impl;
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();
    @Mock
    private TripRepository repository;
    @Autowired
    private TripRepository tripRepository;

    private final int skip = 0;
    private final int limit = 100;

    @After
    public void cleanUp(){
        impl.setRepository(tripRepository);
    }
    @Test
    public void shouldCallSaveOnRepository(){
        final Trip trip = new Trip();

        context.checking(new Expectations(){{
            oneOf(repository).save(trip);
            will(returnValue(trip));
        }});
        impl.setRepository(repository);
        Trip inserted = impl.insert(trip);
        assertThat(inserted,is(trip));
    }

    @Test
    public void shouldCallRepositoryListWithTheGivenSkipAndList(){
        final Page<Trip> page = new PageImpl<Trip>(Arrays.asList(new Trip()));

        context.checking(new Expectations(){{
            oneOf(repository).findAll(new PageRequest(skip,limit));
            will(returnValue(page));
        }});

        impl.setRepository(repository);
        Page<Trip> actual = impl.page(skip,limit);
        assertThat(actual.getContent().get(0),is(page.getContent().get(0)));
    }

    @Test
    public void shouldCallRepositoryFindByUserId(){
        final Page<Trip> page = new PageImpl<>(Arrays.asList(new Trip()));
        final ObjectId id = ObjectId.get();

        context.checking(new Expectations(){{
            oneOf(repository).findByClientId(id, new PageRequest(skip,limit));
            will(returnValue(page));
        }});

        impl.setRepository(repository);
        Page<Trip> actual = impl.pageByUserId(id,skip,limit);
        assertThat(actual,is(page));
    }

    @Test
    public void shouldCallRepositoryFindByCompanyId(){
        final Page<Trip> page = new PageImpl<>(Arrays.asList(new Trip()));
        final ObjectId id = ObjectId.get();

        context.checking(new Expectations(){{
            oneOf(repository).findByCompanyId(id, new PageRequest(skip,limit));
            will(returnValue(page));
        }});

        impl.setRepository(repository);

        Page<Trip> actual = impl.pageByCompanyId(id,skip,limit);
        assertThat(actual,is(page));
    }

    @Test
    public void shouldCallRepositorySave(){
        final Trip trip = new Trip();

        context.checking(new Expectations(){{
            oneOf(repository).save(trip);
            will(returnValue(trip));
        }});

        impl.setRepository(repository);
        impl.update(trip);
        context.assertIsSatisfied();
    }
}
