package com.real.apps.shuttle.controller;

import com.google.gson.Gson;
import com.real.apps.shuttle.config.MvcConfiguration;
import com.real.apps.shuttle.model.Agent;
import com.real.apps.shuttle.service.AgentService;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
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
    private MockMvc mockMvc;
    private static final String VIEW_PAGE = AgentController.VIEW_NAME;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldRenderAgentPage() throws Exception {
        mockMvc.perform(get("/" + VIEW_PAGE)).andDo(print()).andExpect(status().isOk()).andExpect(view().name(VIEW_PAGE));
    }

    @Test
    public void shouldCallServiceFindAll() throws Exception {
        String agentName = "Test Agent Name To Be Return In The List Of Find All";
        final Agent agent = new Agent();
        final List<Agent> agents = Arrays.asList(agent);
        final Page<Agent> page = new PageImpl<>(agents);
        agent.setFullName(agentName);
        final int skip = 0, limit = 2;
        context.checking(new Expectations() {{
            oneOf(service).list(skip, limit);
            will(returnValue(page));
        }});
        controller.setService(service);
        mockMvc.perform(get(String.format("/%s/%d/%d", VIEW_PAGE, skip, limit))).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.content[0].fullName").value(agentName));
    }

    @Test
    public void shouldCallInsertOnSave() throws Exception {
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
        mockMvc.perform(post("/" + VIEW_PAGE).contentType(MediaType.APPLICATION_JSON).content(agentString)).andDo(print()).andExpect(status().isOk()).
                andExpect(jsonPath("$.fullName").value(agentName));
    }

    @Test
    public void shouldGetAllAgentsIfAnAdminIsLoggedIn() {

    }
}