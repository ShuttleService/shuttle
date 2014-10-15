package com.real.apps.shuttle.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.real.apps.shuttle.config.MvcConfiguration;
import com.real.apps.shuttle.model.Vehicle;
import com.real.apps.shuttle.service.VehicleService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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

    private MockMvc mockMvc;
    private static final String VIEW_PAGE = "vehicle";
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();
    @Mock
    private VehicleService service;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldRenderVehiclePage() throws Exception {

        mockMvc.perform(get("/" + VIEW_PAGE)).andDo(print()).andExpect(view().name(VIEW_PAGE)).andExpect(status().isOk());
    }

    @Test
    public void shouldCallListOnServiceAndReturnTheListOfVehicles() throws Exception {
        final int skip = 2;
        final int limit = 3;
        String licenseNumber = "Test License Number To List";
        final Vehicle vehicle = new Vehicle();
        vehicle.setLicenseNumber(licenseNumber);
        final List<Vehicle> vehicles = Arrays.asList(vehicle);
        controller.setService(service);

        context.checking(new Expectations() {{
            oneOf(service).list(skip, limit);
            will(returnValue(vehicles));
        }});

        mockMvc.perform(get("/" + VIEW_PAGE + "/list/" + skip + "/" + limit)).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$[0].licenseNumber").value(licenseNumber));
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
        byte[] vehicleBytes = new ObjectMapper().writeValueAsBytes(vehicle);
        mockMvc.perform(post("/" + VIEW_PAGE).contentType(MediaType.APPLICATION_JSON).content(vehicleBytes)).andDo(print()).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.licenseNumber").value(licenseNumber));
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

        byte[] vehicleBytes = new ObjectMapper().writeValueAsBytes(vehicle);

        mockMvc.perform(put("/" + VIEW_PAGE).contentType(MediaType.APPLICATION_JSON).content(vehicleBytes)).andDo(print()).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.licenseNumber").value(licenseNumber));
    }

    @Test
    public void shouldCallFindOneAndReturnTheFoundVehicle() throws Exception {
        final ObjectId id = ObjectId.get();
        final Vehicle vehicle = new Vehicle();
        vehicle.setId(id);

        controller.setService(service);

        context.checking(new Expectations(){{
            oneOf(service).findOne(id);
            will(returnValue(vehicle));
        }});

        mockMvc.perform(get("/"+VIEW_PAGE+"/one/"+id)).andDo(print()).
                andExpect(status().isOk()).
                andExpect(jsonPath("id.timestamp").value(id.getTimestamp()));
    }
}
