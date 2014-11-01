package com.real.apps.shuttle.service;

import com.real.apps.shuttle.model.Driver;
import com.real.apps.shuttle.respository.DriverRepository;
import com.real.apps.shuttle.respository.RepositoryConfig;
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
public class DriverServiceTest {
    @Autowired
    private DriverServiceImpl impl;
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();
    @Mock
    private DriverRepository repository;

    @Test
    public void shouldCallSaveOnTheRepo(){
        final Driver driver = new Driver();

        context.checking(new Expectations(){{
            oneOf(repository).save(driver);
            will(returnValue(driver));
        }});
        impl.setRepository(repository);
        Driver inserted = impl.insert(driver);
        assertThat(inserted,is(driver));
    }
}
