package com.real.apps.shuttle.domain.model.service;

import com.real.apps.shuttle.repository.RepositoryConfig;
import com.real.apps.shuttle.service.ServiceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;
/**
 * Created by zorodzayi on 15/04/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceConfig.class, RepositoryConfig.class})
public class VehicleDomainServiceTest {
    @Autowired
    private VehicleDomainServiceImpl service;

    @Test
    public void shouldInjectDependencies(){
        assertNotNull(service);
        assertNotNull(service.repository);
        assertNotNull(service.bookedRangeService);
    }

}
