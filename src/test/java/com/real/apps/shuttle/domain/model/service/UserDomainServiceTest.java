package com.real.apps.shuttle.domain.model.service;

import com.real.apps.shuttle.repository.RepositoryConfig;
import com.real.apps.shuttle.service.ServiceConfig;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by zorodzayi on 15/06/09.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceConfig.class, RepositoryConfig.class})
public class UserDomainServiceTest {

    @Autowired
    private UserDomainServiceImpl service;

    @Test
    public void shouldInjectDependencies() {
        assertThat(service.passwordEncoder, notNullValue());
        assertThat(service.repository, notNullValue());
    }

}
