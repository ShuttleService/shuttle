package com.real.apps.shuttle.service;

import com.real.apps.shuttle.model.Review;
import com.real.apps.shuttle.repository.RepositoryConfig;
import com.real.apps.shuttle.repository.ReviewRepository;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
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
public class ReviewServiceTest {
    @Autowired
    private ReviewServiceImpl impl;
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();
    @Mock
    private ReviewRepository repository;

    @Test
    public void shouldCallSaveOnRepository() {
        final Review review = new Review();
        context.checking(new Expectations() {{
            oneOf(repository).save(review);
            will(returnValue(review));
        }});
        impl.setRepository(repository);
        Review inserted = impl.insert(review);
        assertThat(inserted,is(review));
    }

    @Test
    public void shouldCallRepositoryFindAllWithTheGivenSkipAndLimit(){
        final int skip = 0;
        final int limit = 10;
        final Page<Review> page = new PageImpl<Review>(Arrays.asList(new Review()));
        context.checking(new Expectations(){{
            oneOf(repository).findAll(new PageRequest(skip,limit));
            will(returnValue(page));
        }});

        impl.setRepository(repository);
        Page<Review> actual  = impl.list(skip,limit);
        assertThat(actual.getContent().get(0),is(page.getContent().get(0)));
    }
}
