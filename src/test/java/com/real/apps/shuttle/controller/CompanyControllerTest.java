package com.real.apps.shuttle.controller;

import com.google.gson.Gson;
import com.real.apps.shuttle.config.MvcConfiguration;
import com.real.apps.shuttle.model.Agent;
import com.real.apps.shuttle.model.Company;
import com.real.apps.shuttle.model.User;
import com.real.apps.shuttle.service.CompanyService;
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
import org.springframework.data.mongodb.core.MongoOperations;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static com.real.apps.shuttle.controller.UserDetailsUtils.*;

/**
 * Created by zorodzayi on 14/10/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MvcConfiguration.class})
@WebAppConfiguration
public class CompanyControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private CompanyController controller;
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();
    private MockMvc mockMvc;
    @Autowired
    private Filter springSecurityFilterChain;
    @Mock
    private CompanyService service;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private MongoOperations mongoTemplate;
    private static final String VIEW_PAGE = CompanyController.VIEW_PAGE;
    private final int skip = 0, limit = 100;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilter(springSecurityFilterChain).alwaysDo(print()).build();
    }

    @After
    public void cleanUp() {
        controller.setService(companyService);
    }

    @Test
    public void shouldRenderCompanyPage() throws Exception {
        mockMvc.perform(get("/" + VIEW_PAGE).with(user(admin(ObjectId.get())))).
                andExpect(status().isOk()).
                andExpect(view().name(VIEW_PAGE));
    }

    @Test
    public void shouldCallServicePageAndReturnAPageOfCompaniesWhenWorldIsLoggedIn() throws Exception {
        String slug = "Test List Company Slug";
        Company company = new Company();
        company.setSlug(slug);
        final List<Company> list = Arrays.asList(company);
        final Page<Company> page = new PageImpl<Company>(list);
        controller.setService(service);
        context.checking(new Expectations() {{
            oneOf(service).page(skip, limit);
            will(returnValue(page));
        }});

        mockMvc.perform(get("/" + VIEW_PAGE + "/" + skip + "/" + limit).with(user(world(ObjectId.get())))).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.content[0].slug").value(slug));
    }

    @Test
    public void shouldCallServiceInsertAndReturnTheInsertedCompanyWhenAdminIsLoggedIn() throws Exception {
        final Company company = new Company();
        String slug = "Test Slug To Insert";
        company.setSlug(slug);

        controller.setService(service);

        context.checking(new Expectations() {{
            oneOf(service).insert(with(any(Company.class)));
            will(returnValue(company));
        }});

        Gson gson = new Gson();
        String companyString = gson.toJson(company);

        mockMvc.perform(post("/" + VIEW_PAGE).with(user(admin(ObjectId.get()))).contentType(MediaType.APPLICATION_JSON).content(companyString)).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.slug").value(slug));
    }

    @Test
    public void shouldSetTheAgentIdAndAgentNameToTheLoggedInAgentCallServiceInsertAndReturnTheInsertedCompanyWhenAgentIsLoggedIn() throws Exception {
        final Company company = new Company();

        String agentName = "Test Agent Name  To Insert";
        ObjectId agentId = ObjectId.get();
        User agent = agent(agentId);

        agent.setAgentId(agentId);
        agent.setAgentName(agentName);

        Gson gson = new Gson();
        String companyString = gson.toJson(company);

        mockMvc.perform(post("/" + VIEW_PAGE).with(user(agent)).contentType(MediaType.APPLICATION_JSON).content(companyString)).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.agentName").value(agentName)).
                andExpect(jsonPath("$.agentId._time").value(agentId.getTimestamp()));
        mongoTemplate.dropCollection("company");
    }

    @Test
    public void shouldCallServiceUpdateAndReturnUpdatedCompany() throws Exception {
        String slug = "Test Slug To Update";
        final Company company = new Company();
        company.setSlug(slug);
        controller.setService(service);

        context.checking(new Expectations() {{
            oneOf(service).update(with(any(Company.class)));
            will(returnValue(company));
        }});

        String companyString = new Gson().toJson(company);

        mockMvc.perform(put("/" + VIEW_PAGE).contentType(MediaType.APPLICATION_JSON).content(companyString).with(user(admin(ObjectId.get())))).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.slug").value(slug));

    }

    @Test
    public void shouldCallServiceDeleteAndReturnTheDeletedCompanyWhenLoggedOnAsAdmin() throws Exception {
        String slug = "Test Slug To Delete";
        final Company company = new Company();
        company.setSlug(slug);

        controller.setService(service);

        final String companyString = new Gson().toJson(company);
        context.checking(new Expectations() {{
            oneOf(service).delete(with(any(Company.class)));
            will(returnValue(company));
        }});

        mockMvc.perform(delete("/" + VIEW_PAGE).contentType(MediaType.APPLICATION_JSON).with(user(admin(ObjectId.get()))).content(companyString)).andDo(print()).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.slug").value(slug));
    }

    @Test
    public void nonAdminShouldBeNotADeleteCompany() throws Exception {
        String slug = "Test Slug To Delete";
        final Company company = new Company();
        company.setSlug(slug);

        controller.setService(service);

        final String companyString = new Gson().toJson(company);
        context.checking(new Expectations() {{
            never(service).delete(with(any(Company.class)));
            will(returnValue(company));
        }});

        mockMvc.perform(delete("/" + VIEW_PAGE).contentType(MediaType.APPLICATION_JSON).with(user(agent(ObjectId.get()))).content(companyString)).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.slug").value(slug));
    }

    @Test
    public void shouldCallServiceFindOneAndReturnTheFoundCompany() throws Exception {
        final ObjectId id = ObjectId.get();
        final Company company = new Company();
        company.setId(id);

        controller.setService(service);

        context.checking(new Expectations() {{
            oneOf(service).findOne(id);
            will(returnValue(company));
        }});

        mockMvc.perform(get("/" + VIEW_PAGE + "/one/" + id).with(user(admin(id)))).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.id._time").value(id.getTimestamp()));
    }

    @Test
    public void shouldCallServicePageIfAnAdminIsLoggedIn() throws Exception {
        final ObjectId id = ObjectId.get();
        final Company company = new Company();
        company.setId(id);
        final Page<Company> page = new PageImpl<>(Arrays.asList(company));

        controller.setService(service);

        context.checking(new Expectations() {{
            oneOf(service).page(skip, limit);
            will(returnValue(page));
        }});

        mockMvc.perform(get(String.format("/%s/%d/%d", VIEW_PAGE, skip, limit)).with(user(admin(id)))).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.content[0].id._time").value(id.getTimestamp()));
    }

    @Test
    public void shouldRedirectToLoginPageWhenNoUserIsLoggedIn() throws Exception {
        mockMvc.perform(get(String.format("/%s/%d/%d", VIEW_PAGE, skip, limit))).
                andExpect(status().is3xxRedirection());
    }

    @Test
    public void shouldCallServiceFindByAgentIdIfAnAgentIsLoggedIn() throws Exception {
        final ObjectId id = ObjectId.get();
        final Company company = new Company();

        company.setAgentId(id);
        company.setId(id);

        final Page<Company> page = new PageImpl<>(Arrays.asList(company));

        context.checking(new Expectations() {{
            oneOf(service).pageByAgentId(id, skip, limit);
            will(returnValue(page));
        }});

        controller.setService(service);

        mockMvc.perform(get(String.format("/%s/%d/%d", VIEW_PAGE, skip, limit)).with(user(agent(id)))).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.content[0].id._time").value(id.getTimestamp()));

        context.assertIsSatisfied();
    }

    @Test
    public void shouldReturnTheCompanyWhenACompanyUserIsLoggedOn() throws Exception {
        final ObjectId id = ObjectId.get();
        final Company company = new Company();
        company.setId(id);

        context.checking(new Expectations() {{
            oneOf(service).findOne(id);
            will(returnValue(company));
        }});

        controller.setService(service);

        mockMvc.perform(get(String.format("/%s/%d/%d", VIEW_PAGE, skip, limit)).with(user(companyUser(id)))).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.content[0].id._time").value(id.getTimestamp()));
        context.assertIsSatisfied();
    }
}
