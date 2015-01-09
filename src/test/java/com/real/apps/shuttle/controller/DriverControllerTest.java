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

import javax.servlet.Filter;
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
    @Autowired
    private DriverService driverService;
    private Logger logger = Logger.getLogger(DriverControllerTest.class);
    private MockMvc mockMvc;
    private final String VIEW_PAGE = "driver";
    private final int skip = 0, limit = 2;
    private MockMvc mockMvcWithSecurity;
    @Autowired
    private Filter springSecurityFilterChain;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).alwaysDo(print()).build();
        mockMvcWithSecurity = MockMvcBuilders.webAppContextSetup(webApplicationContext).alwaysDo(print()).addFilter(springSecurityFilterChain).build();
    }

    @After
    public void cleanUp(){
        controller.setService(driverService);
    }
    @Test
    public void shouldRenderDriverPage() throws Exception {
        mockMvc.perform(get("/" + VIEW_PAGE)).andDo(print()).andExpect(status().isOk()).andExpect(view().name(VIEW_PAGE));
    }

    @Test
    public void shouldCallDriverServicePageReturnAPageOfDriversIfAdminIsLoggedIn() throws Exception {
        final Driver driver = new Driver();
        final String firstName = "Test First Name";
        driver.setFirstName(firstName);
        List<Driver> drivers = Arrays.asList(driver);
        final Page<Driver> page = new PageImpl<Driver>(drivers);
        controller.setService(service);

        context.checking(new Expectations() {{
            oneOf(service).page(skip, limit);
            will(returnValue(page));
        }});
        mockMvc.perform(get("/" + VIEW_PAGE + "/" + skip + "/" + limit).with(user(admin(ObjectId.get())))).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.content[0].firstName").value(firstName));
    }

    @Test
    public void shouldCallDriverServiceInsertAndReturnTheInsertedDriver() throws Exception {
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
    public void shouldCallServiceUpdateAndReturnTheUpdatedDriver() throws Exception {
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

    @Test
    public void shouldReturnOnlyDriversFromTheCompanyWhenACompanyUserIsLoggedIn() throws Exception {
        final ObjectId id = ObjectId.get();
        Driver driver = new Driver();
        driver.setCompanyId(id);
        final Page<Driver> page = new PageImpl<>(Arrays.asList(driver));

        controller.setService(service);

        context.checking(new Expectations(){
            {
                oneOf(service).pageByCompanyId(id,skip,limit);
                will(returnValue(page));
            }
        });

        mockMvcWithSecurity.perform(get(String.format("/%s/%d/%d",VIEW_PAGE,skip,limit)).with(user(companyUser(id)))).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.content[0].companyId._time").value(id.getTimestamp()));

        context.assertIsSatisfied();
    }

    @Test
    public void shouldReturnAllDriversWhenAWorldIsLoggedIn() throws Exception {
        final ObjectId id = ObjectId.get();
        Driver driver = new Driver();
        driver.setCompanyId(id);
        final Page<Driver> page = new PageImpl<>(Arrays.asList(driver));

        controller.setService(service);

        context.checking(new Expectations(){
            {
                oneOf(service).page(skip,limit);
                will(returnValue(page));
            }
        });

        mockMvcWithSecurity.perform(get(String.format("/%s/%d/%d",VIEW_PAGE,skip,limit)).with(user(world(id)))).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.content[0].companyId._time").value(id.getTimestamp()));

        context.assertIsSatisfied();
    }

}