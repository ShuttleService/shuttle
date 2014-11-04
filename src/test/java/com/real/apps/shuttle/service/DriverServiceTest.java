package com.real.apps.shuttle.service;

import com.real.apps.shuttle.model.Driver;
import com.real.apps.shuttle.respository.DriverRepository;
import com.real.apps.shuttle.respository.RepositoryConfig;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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
    public void shouldCallSaveOnTheRepo() {
        final Driver driver = new Driver();

        context.checking(new Expectations() {{
            oneOf(repository).save(driver);
            will(returnValue(driver));
        }});
        impl.setRepository(repository);
        Driver inserted = impl.insert(driver);
        assertThat(inserted, is(driver));
    }

    @Test
    public void shouldCallRepositoryListWithTheGivenSkipAndLimit() {
        final int skip = 0;
        final int limit = 10;
        final Page<Driver> page = new PageImpl<Driver>(Arrays.asList(new Driver()));

        context.checking(new Expectations() {{
            oneOf(repository).findAll(new PageRequest(skip, limit));
            will(returnValue(page));
        }});

        impl.setRepository(repository);

        Page<Driver> actual = impl.list(skip, limit);
        assertThat(actual.getContent().get(0), is(page.getContent().get(0)));
    }
}
