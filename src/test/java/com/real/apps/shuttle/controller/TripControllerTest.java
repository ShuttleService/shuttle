package com.real.apps.shuttle.controller;

import com.google.gson.Gson;
import com.real.apps.shuttle.config.MvcConfiguration;
import com.real.apps.shuttle.model.Trip;
import com.real.apps.shuttle.repository.RepositoryConfig;
import com.real.apps.shuttle.service.ServiceConfig;
import com.real.apps.shuttle.service.TripService;
import org.bson.types.ObjectId;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static com.real.apps.shuttle.controller.UserDetailsUtils.admin;
import static com.real.apps.shuttle.controller.UserDetailsUtils.companyUser;
import static com.real.apps.shuttle.controller.UserDetailsUtils.world;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by zorodzayi on 14/10/05.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MvcConfiguration.class, ServiceConfig.class, RepositoryConfig.class})
@WebAppConfiguration
public class TripControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private MockMvc mockMvcWithSecurity;
    private final String VIEW_PAGE = TripController.VIEW_NAME;
    @Autowired
    private TripController controller;
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();
    @Mock
    private TripService service;
    @Autowired
    private TripService tripService;
    private  final int skip = 0, limit = 2;


    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).alwaysDo(print()).build();
    }

    @After
    public void cleanUp(){
        controller.setService(tripService);
    }

    @Test
    public void shouldRenderTripPage() throws Exception {
        mockMvc.perform(get("/" + VIEW_PAGE)).andDo(print()).andExpect(view().name(VIEW_PAGE)).andExpect(status().isOk());
    }

    @Test
    public void shouldReturnAPageOfAllTripsWhenAnAdminIsLoggedIn() throws Exception {
        final String source = "Test Source To List";
        Trip trip = new Trip();
        trip.setSource(source);
        List<Trip> trips = Arrays.asList(trip);
        final Page<Trip> page = new PageImpl<Trip>(trips);
        context.checking(new Expectations() {{
            oneOf(service).page(skip, limit);
            will(returnValue(page));
        }});

        controller.setService(service);

        mockMvc.perform(get("/" + VIEW_PAGE + "/" + skip + "/" + limit).with(user(admin(ObjectId.get())))).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.content[0].source").value(source));
    }

    @Test
    public void shouldCallServiceInsertAndReturnTheInsertedTrip() throws Exception {
        String source = "Test Source To Insert";
        final Trip trip = new Trip();
        trip.setSource(source);

        controller.setService(service);

        context.checking(new Expectations() {
            {
                oneOf(service).insert(with(any(Trip.class)));
                will(returnValue(trip));
            }
        });

        String tripString = new Gson().toJson(trip);
        mockMvc.perform(post("/" + VIEW_PAGE).contentType(MediaType.APPLICATION_JSON).content(tripString)).andDo(print()).andExpect(jsonPath("$.source").value(source));
    }

    @Test
    public void shouldCallServiceDeleteAndReturnTheDeletedTrip() throws Exception {
        String source = "Test Source To Delete";
        final Trip trip = new Trip();
        trip.setSource(source);
        String tripString = new Gson().toJson(trip);
        controller.setService(service);
        context.checking(new Expectations() {
            {
                oneOf(service).delete(with(any(Trip.class)));
                will(returnValue(trip));
            }
        });

        mockMvc.perform(delete("/" + VIEW_PAGE).contentType(MediaType.APPLICATION_JSON).content(tripString)).andDo(print()).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.source").value(source));
    }

    @Test
    public void shouldCallServiceUpdateAndReturnTheUpdatedTrip() throws Exception {
        String source = "Test Source To Update";
        final Trip trip = new Trip();
        trip.setSource(source);
        controller.setService(service);

        context.checking(new Expectations() {{
            oneOf(service).update(with(any(Trip.class)));
            will(returnValue(trip));
        }});
        String tripString = new Gson().toJson(trip);
        mockMvc.perform(put("/" + VIEW_PAGE).contentType(MediaType.APPLICATION_JSON).content(tripString)).andDo(print()).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.source").value(source));
    }

    @Test
    public void shouldCallServiceFindOneAndReturnTheOneTrip() throws Exception {
        final ObjectId id = ObjectId.get();
        final Trip trip = new Trip();
        trip.setId(id);

        controller.setService(service);

        context.checking(new Expectations() {{
            oneOf(service).findOne(id);
            will(returnValue(trip));
        }});

        mockMvc.perform(get("/"+VIEW_PAGE+"/one/"+id)).andDo(print()).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.id._time").value(id.getTimestamp()));
    }

    @Test
    public void shouldReturnTripsOfTheCompanyWhenACompanyUserIsLoggedIn() throws Exception {
        final ObjectId id = ObjectId.get();
        final Trip trip = new Trip();
        trip.setCompanyId(id);
        final Page<Trip> page = new PageImpl<>(Arrays.asList(trip));

        controller.setService(service);

        context.checking(new Expectations(){{
            oneOf(service).pageByCompanyId(id,skip,limit);
            will(returnValue(page));
        }});

        mockMvc.perform(get(String.format("/%s/%d/%d",VIEW_PAGE,skip,limit)).with(user(companyUser(id)))).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.content[0].companyId._time").value(id.getTimestamp()));

        context.assertIsSatisfied();
    }

    @Test
    public void shouldReturnTheLoggedInUserTripsWhenAWorldIsLoggedIn() throws Exception {
        final ObjectId id = ObjectId.get();
        final Trip trip = new Trip();
        trip.setClientId(id);
        final Page<Trip> page = new PageImpl<>(Arrays.asList(trip));

        controller.setService(service);

        context.checking(new Expectations(){{
            oneOf(service).pageByUserId(id,skip,limit);
            will(returnValue(page));
        }});

        mockMvc.perform(get(String.format("/%s/%d/%d",VIEW_PAGE,skip,limit)).with(user(world(id)))).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.content[0].clientId._time").value(id.getTimestamp()));

        context.assertIsSatisfied();
    }

}