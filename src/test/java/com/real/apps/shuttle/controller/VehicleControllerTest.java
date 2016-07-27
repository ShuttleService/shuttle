package com.real.apps.shuttle.controller;

import com.google.gson.Gson;
import com.real.apps.shuttle.config.MvcConfiguration;
import com.real.apps.shuttle.domain.model.BookedRange;
import com.real.apps.shuttle.domain.model.Vehicle;
import com.real.apps.shuttle.domain.model.service.VehicleDomainService;
import com.real.apps.shuttle.service.VehicleService;
import org.apache.commons.lang3.time.DateUtils;
import org.bson.types.ObjectId;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;
import java.util.*;

import static com.real.apps.shuttle.controller.UserDetailsUtils.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by zorodzayi on 14/10/09.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MvcConfiguration.class})
@WebAppConfiguration
public class VehicleControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private VehicleController controller;
    private MockMvc mockMvc,mockMvcNonSecured;
    private static final String VIEW_PAGE = "vehicle";
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();
    @Mock
    private VehicleService service;
    @Mock
    private VehicleDomainService domainService;
    @Autowired
    private VehicleService vehicleService;
    private final int skip = 2, limit = 3;
    private final Date from = new Date();
    private final Date to = DateUtils.addMinutes(from, 3);
    final BookedRange bookedRange = new BookedRange(from,to);
    final Pageable pageable = new PageRequest(skip, limit);
    private final ObjectId companyId = ObjectId.get();
    private final ObjectId id = ObjectId.get();
    private ArgumentCaptor<BookedRange> bookedRangeArgumentCaptor = ArgumentCaptor.forClass(BookedRange.class);
    private ArgumentCaptor<Pageable> pageableArgumentCaptor = ArgumentCaptor.forClass(Pageable.class);
    private ArgumentCaptor<ObjectId> objectIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
    private String fromString = new DateTime(this.from).toDateTimeISO().toString();
    private String toString = new DateTime(this.to).toDateTimeISO().toString();
    private ObjectId vehicleCompanyId = ObjectId.get();
    @Autowired
    private Filter springSecurityFilterChain;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilters(springSecurityFilterChain).
                alwaysDo(print()).addFilters().build();
        mockMvcNonSecured = MockMvcBuilders.webAppContextSetup(webApplicationContext).alwaysDo(print()).build();
    }

    @Test
    public void shouldInjectDependencies() {
        assertThat(controller.domainService, is(notNullValue()));
    }

    @After
    public void cleanUp() {
        controller.setService(vehicleService);
    }

    @Test
    public void shouldRenderVehiclePage() throws Exception {
        mockMvcNonSecured.perform(get("/" + VIEW_PAGE)).
                andExpect(view().name(VIEW_PAGE)).andExpect(status().isOk());
    }

    @Test
    public void shouldReturnThePageOfAllVehiclesWhenAdminIsLoggedIn() throws Exception {
        String licenseNumber = "Test License Number To List";
        final Vehicle vehicle = new Vehicle();
        vehicle.setLicenseNumber(licenseNumber);
        List<Vehicle> vehicles = Arrays.asList(vehicle);
        final Page<Vehicle> page = new PageImpl<>(vehicles);
        controller.setService(service);

        context.checking(new Expectations() {{
            oneOf(service).page(skip, limit);
            will(returnValue(page));
        }});

        mockMvc.perform(get("/" + VIEW_PAGE + "/" + skip + "/" + limit).with(user(admin(ObjectId.get())))).
                andExpect(status().isOk()).
                andExpect(jsonPath("$['content'][0]['licenseNumber']").value(licenseNumber));
        context.assertIsSatisfied();
    }

    @Test
    public void shouldReturnThePageOfAllVehiclesWhenWorldIsLoggedIn() throws Exception {
        String licenseNumber = "Test License Number To List";
        final Vehicle vehicle = new Vehicle();
        vehicle.setLicenseNumber(licenseNumber);
        List<Vehicle> vehicles = Arrays.asList(vehicle);
        final Page<Vehicle> page = new PageImpl<>(vehicles);
        controller.setService(service);

        context.checking(new Expectations() {{
            oneOf(service).page(skip, limit);
            will(returnValue(page));
        }});

        mockMvc.perform(get("/" + VIEW_PAGE + "/" + skip + "/" + limit).with(user(world(ObjectId.get())))).
                andExpect(status().isOk()).
                andExpect(jsonPath("$['content'][0]['licenseNumber']").value(licenseNumber));
        context.assertIsSatisfied();
    }

    @Test
    public void shouldFindVehiclesForTheCompanyWhenACompanyUserIsLoggedIn() throws Exception {
        final ObjectId id = ObjectId.get();
        Vehicle vehicle = new Vehicle();
        vehicle.setCompanyId(id);
        final Page<Vehicle> page = new PageImpl<>(Arrays.asList(vehicle));

        context.checking(new Expectations() {{
            oneOf(service).pageByCompanyId(id, skip, limit);
            will(returnValue(page));
        }});
        controller.setService(service);
        mockMvc.perform(get(String.format("/%s/%d/%d", VIEW_PAGE, skip, limit)).with(user(companyUser(id)))).
                andExpect(status().isOk()).
                andExpect(jsonPath("$['content'][0]['companyId']['timestamp']").value(id.getTimestamp()));

    }

    @Test
    public void shouldCallInsertOnServiceAndReturnTheInsertedVehicle() throws Exception {
        String licenseNumber = "Test License Number To Insert";
        final Vehicle vehicle = new Vehicle();
        vehicle.setLicenseNumber(licenseNumber);

        controller.setService(service);

        context.checking(new Expectations() {
            {
                oneOf(service).insert(with(any(Vehicle.class)));
                will(returnValue(vehicle));
            }
        });

        String vehicleString = new Gson().toJson(vehicle);
        mockMvcNonSecured.perform(post("/" + VIEW_PAGE).contentType(MediaType.APPLICATION_JSON).content(vehicleString)).andDo(print()).
                andExpect(status().isOk()).
                andExpect(jsonPath("$['licenseNumber']").value(licenseNumber));
        context.assertIsSatisfied();
    }

    @Test
    public void shouldCallUpdateOnServiceAndReturnTheUpdatedVehicle() throws Exception {
        String licenseNumber = "Test License Number To Update";
        final Vehicle vehicle = new Vehicle();
        vehicle.setLicenseNumber(licenseNumber);

        controller.setService(service);

        context.checking(new Expectations() {{
            oneOf(service).update(with(any(Vehicle.class)));
            will(returnValue(vehicle));
        }});

        String vehicleString = new Gson().toJson(vehicle);

        mockMvcNonSecured.perform(put("/" + VIEW_PAGE).contentType(MediaType.APPLICATION_JSON).content(vehicleString)).andDo(print()).
                andExpect(status().isOk()).
                andExpect(jsonPath("$['licenseNumber']").value(licenseNumber));

        context.assertIsSatisfied();
    }

    @Test
    public void shouldCallFindOneAndReturnTheFoundVehicle() throws Exception {
        final ObjectId id = ObjectId.get();
        final Vehicle vehicle = new Vehicle();

        vehicle.setId(id);

        controller.setService(service);

        context.checking(new Expectations() {{
            oneOf(service).findOne(id);
            will(returnValue(vehicle));
        }});

        mockMvc.perform(get("/" + VIEW_PAGE + "/one/" + id)).
                andExpect(status().isOk()).
                andExpect(jsonPath("id['timestamp']").value(id.getTimestamp()));
        context.assertIsSatisfied();
    }

    @Test
    public void shouldFindBookableVehiclesForTheGivenCompanyWhenACompanyUserIsLoggedIn() throws Exception {
        final ObjectId id = ObjectId.get();

        final Vehicle vehicle = new Vehicle();
        vehicle.setCompanyId(companyId);

        String fromString = new DateTime(from).toDateTimeISO().toString();
        String toString = new DateTime(to).toDateTimeISO().toString();

        vehicle.setId(id);
        final Set<Vehicle> vehicles = new HashSet<>(Arrays.asList(vehicle));
        VehicleDomainService domainService = mock(VehicleDomainService.class);

        controller.domainService = domainService;

        when(domainService.bookable(companyId, pageable, bookedRange)).thenReturn(vehicles);

        mockMvc.perform(get(String.format("/%s/bookable/%s/%s/%s/%d/%d/", VIEW_PAGE, id, fromString, toString, skip, limit)).
                with(user(companyUser(companyId)))).
                andExpect(status().isOk()).
                andExpect(jsonPath("$").isArray()).
                andExpect(jsonPath("$[0]['companyId']['timestamp']").value(companyId.getTimestamp())).
                andExpect(jsonPath("$[0]['id']['timestamp']").value(id.getTimestamp()));

        verify(domainService).bookable(objectIdArgumentCaptor.capture(), pageableArgumentCaptor.capture(), bookedRangeArgumentCaptor.capture());

        assertThat(pageableArgumentCaptor.getValue(), is(pageable));
        assertThat(objectIdArgumentCaptor.getValue(), is(companyId));
        assertThat(bookedRangeArgumentCaptor.getValue(), is(bookedRange));
    }

    @Test
    public void shouldNotReturnAnyVehiclesWhenNoUserIsLoggedIn() throws Exception {
        context.checking(new Expectations() {{
            never(domainService).bookable(with(any(ObjectId.class)), with(any(Pageable.class)), with(any(BookedRange.class)));
        }});

        controller.setService(service);
        String fromString = new DateTime(from).toDateTimeISO().toString();
        String toString = new DateTime(to).toDateTimeISO().toString();

        mockMvc.perform(get(String.format("/%s/bookable/%s/%s/%s/%d/%d", VIEW_PAGE, id, fromString, toString, skip, limit))).
                andExpect(status().isOk()).andExpect(jsonPath("$[0]").doesNotExist());

        context.assertIsSatisfied();
    }

    @Test
    public void shouldReturnBookableVehiclesForTheGivenCompanyIdWhenAWorldUserIsLoggedIn() throws Exception {
        Vehicle vehicle = new Vehicle();
        vehicle.setCompanyId(companyId);

        Set<Vehicle> vehicles = new HashSet<>(Arrays.asList(vehicle));

        VehicleDomainService domainService = Mockito.mock(VehicleDomainService.class);
        controller.domainService = domainService;

        when(domainService.bookable(companyId, pageable, bookedRange)).thenReturn(vehicles);

        mockMvc.perform(get(String.format("/%s/bookable/%s/%s/%s/%d/%d", VIEW_PAGE, companyId.toString(), fromString, toString, skip, limit)).
                with(user(world(id)))).
                andExpect(status().isOk()).
                andExpect(jsonPath("$").isArray()).
                andExpect(jsonPath("$[0]['companyId']['timestamp']").value(companyId.getTimestamp()));

        verify(domainService).bookable(objectIdArgumentCaptor.capture(), pageableArgumentCaptor.capture(), bookedRangeArgumentCaptor.capture());

        assertThat(bookedRange, is(bookedRangeArgumentCaptor.getValue()));
        assertThat(companyId, is(objectIdArgumentCaptor.getValue()));
        assertThat(pageable, is(pageableArgumentCaptor.getValue()));
    }

    @Test
    public void shouldReturnBookableVehiclesForTheGivenCompanyIdWhenAnAdminIsLoggedIn() throws Exception {
        Vehicle vehicle = new Vehicle();
        vehicle.setCompanyId(vehicleCompanyId);
        Set<Vehicle> bookable = new HashSet<>();
        bookable.add(vehicle);

        VehicleDomainService domainService = Mockito.mock(VehicleDomainService.class);
        when(domainService.bookable(vehicleCompanyId, pageable, bookedRange)).thenReturn(bookable);
        controller.domainService = domainService;

        mockMvc.perform(get(String.format("/%s/bookable/%s/%s/%s/%d/%d", VIEW_PAGE, vehicleCompanyId, fromString, toString, skip, limit)).
                contentType(MediaType.APPLICATION_JSON)
                .with(user(admin(id)))).
                andExpect(status().isOk()).
                andExpect(jsonPath("$[0]['companyId']['timestamp']").value(vehicleCompanyId.getTimestamp()));

        verify(domainService).bookable(objectIdArgumentCaptor.capture(), pageableArgumentCaptor.capture(), bookedRangeArgumentCaptor.capture());

        assertThat(bookedRange, is(bookedRangeArgumentCaptor.getValue()));
        assertThat(vehicleCompanyId, is(objectIdArgumentCaptor.getValue()));
        assertThat(pageable, is(pageableArgumentCaptor.getValue()));

    }

}
