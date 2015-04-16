package com.real.apps.shuttle.controller;

import com.real.apps.shuttle.config.MvcConfiguration;
import com.real.apps.shuttle.domain.model.User;
import com.real.apps.shuttle.service.LoginService;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by zorodzayi on 14/10/04.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MvcConfiguration.class})
@WebAppConfiguration
public class LoginControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();
    @Mock
    private LoginService service;
    @Autowired
    private LoginController controller;

    final String LOGIN = "login";

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldRenderLoginPage() throws Exception {
        mockMvc.perform(get("/" + LOGIN)).andDo(print()).andExpect(status().isOk()).andExpect(view().name(LOGIN));
    }

    @Test
    public void shouldPopulateLoginCommandObject() throws Exception {
        MvcResult result = mockMvc.perform(get("/" + LOGIN)).andDo(print()).andExpect(model().attributeExists(LOGIN)).andReturn();
        assertThat(result.getModelAndView().getModel().get(LOGIN),instanceOf(User.class));
    }

    @Test
    public void loginShouldCallLoginService() throws Exception {
        final User user = new User();

        controller.setService(service);

        context.checking(new Expectations() {{

            exactly(2).of(service).login(with(any(User.class)));
            will(returnValue(user));
        }});

        MvcResult result = mockMvc.perform(post("/" + LOGIN)).andDo(print()).andReturn();
        assertPost(result,user);
        result = mockMvc.perform(post("/"+LOGIN)).andDo(print()).andReturn();
        assertPost(result,user);
        context.assertIsSatisfied();
    }

    private void assertPost(MvcResult result,User user){
        assertThat(result.getModelAndView().getViewName(),is("redirect:home"));
        assertThat((User)result.getModelAndView().getModel().get("user"),is(user));

    }
}
