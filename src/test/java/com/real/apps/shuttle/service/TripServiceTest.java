package com.real.apps.shuttle.service;

import com.real.apps.shuttle.model.Trip;
import com.real.apps.shuttle.respository.RepositoryConfig;
import com.real.apps.shuttle.respository.TripRepository;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
}
