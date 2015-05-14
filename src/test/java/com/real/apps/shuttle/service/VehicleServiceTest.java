package com.real.apps.shuttle.service;

import com.real.apps.shuttle.domain.model.BookedRange;
import com.real.apps.shuttle.domain.model.Vehicle;
import com.real.apps.shuttle.domain.model.service.VehicleDomainService;
import com.real.apps.shuttle.repository.RepositoryConfig;
import com.real.apps.shuttle.repository.VehicleRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.bson.types.ObjectId;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    private final int skip = 0;
    private final int limit = 10;

    @Test
    public void shouldCallSaveOnRepository() {
        final Vehicle vehicle = new Vehicle();

        context.checking(new Expectations() {{
            oneOf(repository).save(vehicle);
            will(returnValue(vehicle));
        }});
        impl.setRepository(repository);
        Vehicle inserted = impl.insert(vehicle);
        assertThat(inserted, is(vehicle));
    }

    @Test
    public void shouldCallRepositoryListWithTheGivenSkipAndLimit() {
        final Page<Vehicle> page = new PageImpl<Vehicle>(Arrays.asList(new Vehicle()));
        context.checking(new Expectations() {{
            oneOf(repository).findAll(new PageRequest(skip, limit));
            will(returnValue(page));
        }});

        impl.setRepository(repository);
        Page<Vehicle> actual = impl.page(skip, limit);
        assertThat(page.getContent().get(0), is(actual.getContent().get(0)));
    }

    @Test
    public void shouldCallRepositoryFindByCompanyId() {
        final Page<Vehicle> page = new PageImpl<>(Arrays.asList(new Vehicle()));
        final ObjectId id = ObjectId.get();
        context.checking(new Expectations() {{
            oneOf(repository).findByCompanyId(id, new PageRequest(skip, limit));
            will(returnValue(page));
        }});

        impl.setRepository(repository);
        Page<Vehicle> actual = impl.pageByCompanyId(id, skip, limit);
        assertThat(actual, is(page));
    }

    @Test
    public void shouldCallDomainServiceBookable() {
        VehicleDomainService domainService = mock(VehicleDomainService.class);
        Date from = new Date();
        Date to = DateUtils.addMinutes(from, 2);

        BookedRange bookedRange = new BookedRange(from,to);

        ObjectId companyId = ObjectId.get();
        Pageable pageable = new PageRequest(skip, limit);

        Set<Vehicle> vehicles = new HashSet<>(Arrays.asList(new Vehicle()));
        when(domainService.bookable(companyId, pageable, bookedRange)).thenReturn(vehicles);
        impl.domainService = domainService;
        Set<Vehicle> actual = impl.bookable(companyId, pageable, bookedRange);

        assertThat(actual, is(vehicles));
        assertThat(actual.iterator().next(), is(vehicles.iterator().next()));
        verify(domainService).bookable(companyId, pageable, bookedRange);
    }
}