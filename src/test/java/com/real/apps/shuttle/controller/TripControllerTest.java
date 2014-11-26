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
import org.junit.Before;
import org.junit.Ignore;
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
    private final String VIEW_PAGE = TripController.VIEW_NAME;
    @Autowired
    private TripController controller;
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();
    @Mock
    private TripService service;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldRenderTripPage() throws Exception {
        mockMvc.perform(get("/" + VIEW_PAGE)).andDo(print()).andExpect(view().name(VIEW_PAGE)).andExpect(status().isOk());
    }

    @Test
    public void shouldCallListOnServiceAndReturnAListOfTrips() throws Exception {
        final int skip = 1;
        final int limit = 2;
        final String source = "Test Source To List";
        Trip trip = new Trip();
        trip.setSource(source);
        List<Trip> trips = Arrays.asList(trip);
        final Page<Trip> page = new PageImpl<Trip>(trips);
        context.checking(new Expectations() {{
            oneOf(service).list(skip, limit);
            will(returnValue(page));
        }});

        controller.setService(service);

        mockMvc.perform(get("/" + VIEW_PAGE + "/" + skip + "/" + limit)).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.content[0].source").value(source));
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

        String tripString = new Gson().toJson(trip);

        mockMvc.perform(get("/"+VIEW_PAGE+"/one/"+id)).andDo(print()).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.id._time").value(id.getTimestamp()));
    }


}
