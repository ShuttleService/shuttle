package com.real.apps.shuttle.service;

import com.real.apps.shuttle.model.Vehicle;
import com.real.apps.shuttle.respository.RepositoryConfig;
import com.real.apps.shuttle.respository.VehicleRepository;
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
public class VehicleServiceTest {
    @Autowired
    private VehicleServiceImpl impl;
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();
    @Mock
    private VehicleRepository repository;

    @Test
    public void shouldCallSaveOnRepository(){
        final Vehicle vehicle = new Vehicle();

        context.checking(new Expectations(){{
            oneOf(repository).save(vehicle);
            will(returnValue(vehicle));
        }});
        impl.setRepository(repository);
        Vehicle inserted = impl.insert(vehicle);
        assertThat(inserted,is(vehicle));
    }
}
