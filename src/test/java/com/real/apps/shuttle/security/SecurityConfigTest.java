package com.real.apps.shuttle.security;

import com.real.apps.shuttle.config.MvcConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import javax.servlet.Filter;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.*;

/**
 * Created by zorodzayi on 15/01/05.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MvcConfiguration.class})
@WebAppConfiguration
public class SecurityConfigTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private Filter springSecurityFilterChain;
    private MockMvc mockMvc;

    @Before
    public void init(){
        assertNotNull(webApplicationContext);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilters(springSecurityFilterChain).alwaysDo(print()).build();
        assertNotNull(springSecurityFilterChain);
    }

    @Test
    public void shouldSuccessfullyLogInAUserWithValidCredentials(){
        formLogin().user("user").password("password");
    }
}
