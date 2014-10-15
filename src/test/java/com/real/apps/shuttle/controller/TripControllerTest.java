package com.real.apps.shuttle.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.real.apps.shuttle.config.MvcConfiguration;
import com.real.apps.shuttle.model.Trip;
import com.real.apps.shuttle.service.TripService;
import org.bson.types.ObjectId;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
@ContextConfiguration(classes = {MvcConfiguration.class})
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
        final List<Trip> trips = Arrays.asList(trip);

        context.checking(new Expectations() {{
            oneOf(service).list(skip, limit);
            will(returnValue(trips));
        }});

        controller.setService(service);

        mockMvc.perform(get("/" + VIEW_PAGE + "/list/" + skip + "/" + limit)).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$[0].source").value(source));
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

        ObjectMapper mapper = new ObjectMapper();
        byte[] tripByte = mapper.writeValueAsBytes(trip);
        mockMvc.perform(post("/" + VIEW_PAGE).contentType(MediaType.APPLICATION_JSON).content(tripByte)).andDo(print()).andExpect(jsonPath("$.source").value(source));
    }

    @Test
    public void shouldCallServiceDeleteAndReturnTheDeletedTrip() throws Exception {
        String source = "Test Source To Delete";
        final Trip trip = new Trip();
        trip.setSource(source);
        byte[] tripByte = new ObjectMapper().writeValueAsBytes(trip);
        controller.setService(service);
        context.checking(new Expectations() {
            {
                oneOf(service).delete(with(any(Trip.class)));
                will(returnValue(trip));
            }
        });

        mockMvc.perform(delete("/" + VIEW_PAGE).contentType(MediaType.APPLICATION_JSON).content(tripByte)).andDo(print()).
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
        byte[] tripByte = new ObjectMapper().writeValueAsBytes(trip);
        mockMvc.perform(put("/" + VIEW_PAGE).contentType(MediaType.APPLICATION_JSON).content(tripByte)).andDo(print()).
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

        byte tripByte[] = new ObjectMapper().writeValueAsBytes(trip);

        mockMvc.perform(get("/"+VIEW_PAGE+"/one/"+id)).andDo(print()).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.id.timestamp").value(id.getTimestamp()));
    }


}
