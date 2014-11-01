package com.real.apps.shuttle.controller;

import com.google.gson.Gson;
import com.real.apps.shuttle.config.MvcConfiguration;
import com.real.apps.shuttle.model.Company;
import com.real.apps.shuttle.service.CompanyService;
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
    @Mock
    private CompanyService service;
    private static final String VIEW_PAGE = CompanyController.VIEW_PAGE;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldRenderCompanyPage() throws Exception {
        mockMvc.perform(get("/" + VIEW_PAGE)).andDo(print()).
                andExpect(status().isOk()).
                andExpect(view().name(VIEW_PAGE));
    }

    @Test
    public void shouldCallServiceListAndReturnAListOfCompanies() throws Exception {
        final int skip = 1;
        final int limit = 10;
        String slug = "Test List Company Slug";
        Company company = new Company();
        company.setSlug(slug);
        final List<Company> list = Arrays.asList(company);

        controller.setService(service);
        context.checking(new Expectations() {{
            oneOf(service).list(skip, limit);
            will(returnValue(list));
        }});

        mockMvc.perform(get("/" + VIEW_PAGE + "/" + skip + "/" + limit)).andDo(print()).
                andExpect(status().isOk()).
                andExpect(jsonPath("$[0].slug").value(slug));
    }

    @Test
    public void shouldCallServiceInsertAndRe7turnTheInsertedCompany() throws Exception {
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

        mockMvc.perform(post("/" + VIEW_PAGE).contentType(MediaType.APPLICATION_JSON).content(companyString)).
                andDo(print()).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.slug").value(slug));
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

        mockMvc.perform(put("/" + VIEW_PAGE).contentType(MediaType.APPLICATION_JSON).content(companyString)).andDo(print()).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.slug").value(slug));

    }

    @Test
    public void shouldCallServiceDeleteAndReturnTheDeletedCompany() throws Exception {
        String slug = "Test Slug To Delete";
        final Company company = new Company();
        company.setSlug(slug);

        controller.setService(service);

        final String companyString = new Gson().toJson(company);
        context.checking(new Expectations() {{
            oneOf(service).delete(with(any(Company.class)));
            will(returnValue(company));
        }});

        mockMvc.perform(delete("/" + VIEW_PAGE).contentType(MediaType.APPLICATION_JSON).content(companyString)).andDo(print()).
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

        mockMvc.perform(get("/" + VIEW_PAGE + "/one/" + id)).andDo(print()).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.id._time").value(id.getTimestamp()));
    }
}
