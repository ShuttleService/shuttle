package com.real.apps.shuttle.controller;

import com.google.gson.Gson;
import com.real.apps.shuttle.config.MvcConfiguration;
import com.real.apps.shuttle.model.Driver;
import com.real.apps.shuttle.service.DriverService;
import org.apache.log4j.Logger;
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
public class DriverControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private DriverController controller;
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();
    @Mock
    private DriverService service;

    private Logger logger = Logger.getLogger(DriverControllerTest.class);
    private MockMvc mockMvc;
    private final String VIEW_PAGE = "driver";


    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldRenderDriverPage() throws Exception {
        mockMvc.perform(get("/" + VIEW_PAGE)).andDo(print()).andExpect(status().isOk()).andExpect(view().name(VIEW_PAGE));
    }

    @Test
    public void shouldCallDriverServiceAndReturnAListOfDrivers() throws Exception {
        final Driver driver = new Driver();
        final String firstName = "Test First Name";
        driver.setFirstName(firstName);
        final List<Driver> drivers = Arrays.asList(driver);
        controller.setService(service);
        final int skip = 1;
        final int limit = 2;

        context.checking(new Expectations() {{
            oneOf(service).list(skip, limit);
            will(returnValue(drivers));
        }});
        mockMvc.perform(get("/" + VIEW_PAGE + "/" + skip + "/" + limit)).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$[0].firstName").value(firstName));
    }

    @Test
    public void shouldCallDriverServiceIsertAndReturnTheInsertedDriver() throws Exception {
        final Driver driver = new Driver();
        String firstName = "Test Driver First Name To Delete";
        driver.setFirstName(firstName);

        controller.setService(service);

        context.checking(new Expectations() {{
            oneOf(service).insert(with(any(Driver.class)));
            will(returnValue(driver));
        }});

        String driverString = new Gson().toJson(driver);

        logger.debug("The String From The Object Mapped Object " + driverString);
        mockMvc.perform(post("/" + VIEW_PAGE).contentType(MediaType.APPLICATION_JSON).content(driverString)).
                andDo(print()).
                andExpect(status().
                        isOk()).
                andExpect(jsonPath("$.firstName").value(firstName));
    }

    @Test
    public void shouldCallServiceDeleteAndReturnTheDeletedDriver() throws Exception {
        String firstName = "Test Driver First Name To Delete";
        final Driver driver = new Driver();
        driver.setFirstName(firstName);
        controller.setService(service);
        context.checking(new Expectations() {{
            oneOf(service).delete(with(any(Driver.class)));
            will(returnValue(driver));
        }});

        String driverString = new Gson().toJson(driver);
        mockMvc.perform(delete("/" + VIEW_PAGE).contentType(MediaType.APPLICATION_JSON).content(driverString)).andDo(print()).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.firstName").value(firstName));
    }

    @Test
    public void shouldCallServiceUpateAndReturnTheUpdatedDriver() throws Exception {
        String firstName = "Test Driver Name To Update";
        final Driver driver = new Driver();
        driver.setFirstName(firstName);
        controller.setService(service);
        context.checking(new Expectations() {{
            oneOf(service).update(with(any(Driver.class)));
            will(returnValue(driver));
        }});

        String driverString = new Gson().toJson(driver);
        mockMvc.perform(put("/" + VIEW_PAGE).contentType(MediaType.APPLICATION_JSON).content(driverString)).andDo(print()).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.firstName").value(firstName));
    }

    @Test
    public void shouldCallServiceGetOneAndReturnTheFoundDriver() throws Exception {
        final ObjectId id = ObjectId.get();
        final Driver driver = new Driver();
        driver.setId(id);

        context.checking(new Expectations() {{
            oneOf(service).findOne(id);
            will(returnValue(driver));
        }});

        controller.setService(service);

        mockMvc.perform(get("/" + VIEW_PAGE + "/one/" + id)).andDo(print()).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.id._time").value(id.getTimestamp()));
    }
}