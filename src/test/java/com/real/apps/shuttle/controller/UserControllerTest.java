package com.real.apps.shuttle.controller;

import com.google.gson.Gson;
import com.real.apps.shuttle.config.MvcConfiguration;
import com.real.apps.shuttle.model.User;
import com.real.apps.shuttle.service.UserService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by zorodzayi on 14/10/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MvcConfiguration.class})
@WebAppConfiguration
public class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private UserController controller;
    private MockMvc mockMvc;
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();
    private static final String VIEW_PAGE = UserController.VIEW_NAME;
    @Mock
    private UserService service;
    @Autowired
    private UserService userService;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @After
    public void cleanUp(){
        controller.setService(userService);
    }
    @Test
    public void shouldRenderUserHomePage() throws Exception {

        mockMvc.perform(get("/" + VIEW_PAGE)).andDo(print()).andExpect(status().isOk()).andExpect(view().name(VIEW_PAGE));
    }

    @Test
    public void shouldCallUserServiceAndReturnAListOfUsers() throws Exception {
        User user = new User();
        String firstName = "Test First Name To Be Return From Service List";
        user.setFirstName(firstName);
        List<User> list = Arrays.asList(user);
        final Page<User> page = new PageImpl<User>(list);
        final int skip = 12;
        final int limit = 34;

        context.checking(new Expectations() {{
            oneOf(service).list(skip, limit);
            will(returnValue(page));
        }});

        controller.setService(service);
        mockMvc.perform(get("/" + VIEW_PAGE + "/" + skip + "/" + limit)).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.content[0].firstName").value(firstName));
        context.assertIsSatisfied();
    }

    @Test
    public void shouldCallUserServiceInsertAndReturnTheInsertedUser() throws Exception {
        String firstName = "Test First Name For User To Insert ";
        final  User user = new User();
        user.setFirstName(firstName);

        context.checking(new Expectations(){{
            oneOf(service).insert(with(any(User.class)));
            will(returnValue(user));
        }});
        String userJsonString = new Gson().toJson(user);

        controller.setService(service);

        mockMvc.perform(post("/"+VIEW_PAGE).contentType(MediaType.APPLICATION_JSON).content(userJsonString)).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.firstName").
                value(firstName));
        context.assertIsSatisfied();
    }
}
