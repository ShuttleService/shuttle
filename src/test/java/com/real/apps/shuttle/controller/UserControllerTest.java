package com.real.apps.shuttle.controller;

import com.google.gson.Gson;
import com.real.apps.shuttle.config.MvcConfiguration;
import com.real.apps.shuttle.domain.model.User;
import com.real.apps.shuttle.service.UserService;
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

import static com.real.apps.shuttle.controller.UserDetailsUtils.*;
import static com.real.apps.shuttle.miscellaneous.Role.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
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
    private final int skip = 12, limit = 34;

    @Autowired
    private Filter springSecurityFilterChain;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilters(springSecurityFilterChain).alwaysDo(print()).build();
    }

    @After
    public void cleanUp() {
        controller.setService(userService);
    }

    @Test
    public void shouldRenderUserHomePage() throws Exception {
        mockMvc.perform(get("/" + VIEW_PAGE)).andDo(print()).
                andExpect(status().isOk()).
                andExpect(view().name(VIEW_PAGE));
    }

    @Test
    public void shouldReturnAllUsersWhenAnAdminIsLoggedIn() throws Exception {
        User user = new User();
        String firstName = "Test First Name To Be Return From Service List";
        user.setFirstName(firstName);
        List<User> list = Arrays.asList(user);
        final Page<User> page = new PageImpl<User>(list);

        context.checking(new Expectations() {{
            oneOf(service).page(skip, limit);
            will(returnValue(page));
        }});

        controller.setService(service);
        mockMvc.perform(get("/" + VIEW_PAGE + "/" + skip + "/" + limit).with(user(admin(ObjectId.get())))).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.content[0].firstName").value(firstName));
        context.assertIsSatisfied();
    }

    @Test
    public void shouldCallUserServiceInsertAndReturnTheInsertedUserWhenAnonymousOrAdminIsLoggedIn() throws Exception {
        String firstName = "Test First Name For User To Insert ";
        final User user = new User();
        user.setFirstName(firstName);

        context.checking(new Expectations() {{
            exactly(2).of(service).insert(with(any(User.class)));
            will(returnValue(user));
        }});

        String userJsonString = new Gson().toJson(user);

        controller.setService(service);

        mockMvc.perform(post("/" + VIEW_PAGE).with(user(admin(ObjectId.get()))).contentType(MediaType.APPLICATION_JSON).content(userJsonString))
                .andExpect(status().isOk()).
                andExpect(jsonPath("$.firstName").value(firstName));

        mockMvc.perform(post("/" + VIEW_PAGE).contentType(MediaType.APPLICATION_JSON).content(userJsonString))
                .andExpect(status().isOk()).
                andExpect(jsonPath("$.firstName").value(firstName));

        context.assertIsSatisfied();
    }

    @Test
    public void shouldNotCallServiceInsertWhenWorldIsLoggedInAsAWorldShouldNotCreateOtherUsers() throws Exception {
        final User user = new User();
        final ObjectId id = ObjectId.get();

        user.setId(id);

        context.checking(new Expectations() {{
            never(service).insert(with(any(User.class)));
        }});

        String jsonUser = new Gson().toJson(user);

        controller.setService(service);

        mockMvc.perform(post(String.format("/%s",VIEW_PAGE)).with(user(world(ObjectId.get()))).contentType(MediaType.APPLICATION_JSON).content(jsonUser)).
                andExpect(status().isOk()).andExpect(jsonPath("$.id._time").value(id.getTimestamp()));
    }



    @Test
    public void shouldFindUsersForTheCompanyWhenACompanyUserIsLoggedOn() throws Exception {
        final ObjectId id = ObjectId.get();
        User user = new User();
        user.setCompanyId(id);
        final Page<User> page = new PageImpl<>(Arrays.asList(user));

        controller.setService(service);

        context.checking(new Expectations() {{
            oneOf(service).pageByCompanyId(id, skip, limit);
            will(returnValue(page));
        }});

        mockMvc.perform(get(String.format("/%s/%d/%d", VIEW_PAGE, skip, limit)).with(user(companyUser(id)))).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.content[0].companyId._time").value(id.getTimestamp()));
        context.assertIsSatisfied();
    }

    @Test
    public void shouldReturnTheLoggedInUserWhenWorldIsLoggedIn() throws Exception {
        final ObjectId id = ObjectId.get();
        final User user = new User();
        user.setId(id);

        controller.setService(service);

        context.checking(new Expectations() {{
            oneOf(service).findOne(id);
            will(returnValue(user));
        }});

        mockMvc.perform(get(String.format("/%s/%d/%d", VIEW_PAGE, skip, limit)).with(user(world(id)))).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.content[0].id._time").value(id.getTimestamp()));
        context.assertIsSatisfied();
    }

    @Test
    public void shouldReturnAllRolesWhenAnAdminIsLoggedIn() throws Exception {
        mockMvc.perform(get(String.format("/%s/role", VIEW_PAGE)).with(user(admin(ObjectId.get())))).
                andExpect(status().isOk()).
                andExpect(jsonPath("$[0].role").value(ADMIN)).
                andExpect(jsonPath("$[1].role").value(AGENT)).
                andExpect(jsonPath("$[2].role").value(COMPANY_USER)).
                andExpect(jsonPath("$[3].role").value(WORLD));
    }

    @Test
    public void shouldReturnCompanyUserAndWorldRolesWhenCompanyUserIsLoggedIn() throws Exception {
        mockMvc.perform(get(String.format("/%s/role", VIEW_PAGE)).with(user(companyUser(ObjectId.get())))).
                andExpect(status().isOk()).
                andExpect(jsonPath("$[0].role").value(COMPANY_USER)).
                andExpect(jsonPath("$[1].role").value(WORLD)).
                andExpect(jsonPath("$[2].role").doesNotExist());
    }

    @Test
    public void shouldReturnWorldRoleWhenAWorldIsLoggedIn() throws Exception {
        mockMvc.perform(get(String.format("/%s/role", VIEW_PAGE)).with(user(world(ObjectId.get())))).
                andExpect(status().isOk()).
                andExpect(jsonPath("$[0].role").value(WORLD)).
                andExpect(jsonPath("$[1].role").doesNotExist());
    }

    @Test
    public void shouldReturnWorldRoleWhenUserIsAnonymous() throws Exception {
        mockMvc.perform(get(String.format("/%s/role", VIEW_PAGE))).
                andExpect(status().isOk()).
                andExpect(jsonPath("$[0].role").value(WORLD)).
                andExpect(jsonPath("$[1].role").doesNotExist());
    }

}
