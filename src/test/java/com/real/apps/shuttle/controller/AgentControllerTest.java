package com.real.apps.shuttle.controller;

import com.google.gson.Gson;
import com.real.apps.shuttle.config.MvcConfiguration;
import com.real.apps.shuttle.domain.model.Agent;
import com.real.apps.shuttle.service.AgentService;
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
import static org.junit.Assert.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by zorodzayi on 14/12/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MvcConfiguration.class})
@WebAppConfiguration
public class AgentControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private AgentController controller;
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();
    @Mock
    private AgentService service;
    @Autowired
    private AgentService agentService;
    @Autowired
    private Filter springSecurityFilterChain;
    private MockMvc mockMvc;
    private static final String VIEW_PAGE = AgentController.VIEW_NAME;
    private final int skip = 0, limit = 2;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).alwaysDo(print()).addFilters(springSecurityFilterChain).build();
    }

    @After
    public void cleanUp() {
        assertNotNull(agentService);
        controller.setService(agentService);
    }

    @Test
    public void renderAgentPage() throws Exception {
        mockMvc.perform(get("/" + VIEW_PAGE).with(user(admin(ObjectId.get())))).
                andExpect(status().isOk()).andExpect(view().name(VIEW_PAGE));
    }

    @Test
    public void doNotCallServiceIfThereIsNoUserLoggedIn() throws Exception {
        context.checking(new Expectations() {{
            never(service).page(skip, limit);
        }});
        controller.setService(service);
        mockMvc.perform(get(String.format("/%s/%d/%d", VIEW_PAGE, skip, limit))).
                andExpect(status().is3xxRedirection());
    }

    @Test
    public void callServicePageIfAnAdminIsLoggedIn() throws Exception {
        String agentName = "Test Agent Name To Be Returned In The List Of Find All";
        final Agent agent = new Agent();
        agent.setFullName(agentName);
        final List<Agent> agents = Arrays.asList(agent);
        final Page<Agent> page = new PageImpl<>(agents);

        context.checking(new Expectations() {{
            oneOf(service).page(skip, limit);
            will(returnValue(page));
        }});
        controller.setService(service);
        mockMvc.perform(get(String.format("/%s/%d/%d", VIEW_PAGE, skip, limit)).with(user(admin(ObjectId.get())))).
                andExpect(status().isOk()).
                andExpect(jsonPath("$['content'][0]['fullName']").value(agentName));
        context.assertIsSatisfied();
    }

    @Test
    public void callServiceFindOneIfAnAgentIsLoggedIn() throws Exception {
        String agentName = "Test Agent Name To Be Returned By Find One.";
        final ObjectId id = ObjectId.get();
        final Agent agent = new Agent();
        agent.setFullName(agentName);
        agent.setId(id);

        context.checking(new Expectations() {{
            oneOf(service).findOne(id);
            will(returnValue(agent));
        }});

        controller.setService(service);

        mockMvc.perform(get(String.format("/%s/%d/%d", VIEW_PAGE, skip, limit)).with(user(agent(id)))).
                andExpect(status().isOk()).
                andExpect(jsonPath("$['content'][0]['id']['timestamp']").value(id.getTimestamp()));
    }

    @Test
    public void callInsertOnSaveWhenAnAdminIsLoggedIn() throws Exception {
        String agentName = "Test Agent Name To Be Mock Inserted";
        final Agent agent = new Agent();
        agent.setFullName(agentName);
        Gson gson = new Gson();
        String agentString = gson.toJson(agent);
        context.checking(new Expectations() {{
            oneOf(service).insert(with(any(Agent.class)));
            will(returnValue(agent));
        }});

        controller.setService(service);
        mockMvc.perform(post("/" + VIEW_PAGE).contentType(MediaType.APPLICATION_JSON).content(agentString).with(user(admin(ObjectId.get()))))
                .andExpect(status().isOk()).
                andExpect(jsonPath("$['fullName']").value(agentName));
    }

    @Test
    public void doNotInsertWhenNonAdminIsLoggedIn() throws Exception {
        String agentName = "Test Agent Name To Be Mock Inserted";
        final Agent agent = new Agent();
        agent.setFullName(agentName);
        Gson gson = new Gson();
        String agentString = gson.toJson(agent);
        context.checking(new Expectations() {{
            never(service).insert(with(any(Agent.class)));
        }});
        ObjectId id = ObjectId.get();
        controller.setService(service);
        mockMvc.perform(post(String.format("/%s", VIEW_PAGE)).contentType(MediaType.APPLICATION_JSON).content(agentString).with(user(agent(id))))
                .andExpect(status().isOk());
    }

}