package com.real.apps.shuttle.security;

import com.real.apps.shuttle.config.MvcConfiguration;
import com.real.apps.shuttle.model.User;
import com.real.apps.shuttle.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import javax.servlet.Filter;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;

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
    @Autowired
    private UserRepository repository;
    @Autowired
    private MongoOperations mongoTemplate;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Before
    public void init(){
        assertNotNull(webApplicationContext);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilters(springSecurityFilterChain).alwaysDo(print()).build();
        assertNotNull(springSecurityFilterChain);
    }

    @Test
    public void shouldSuccessfullyLogInAUserWithValidCredentials() throws Exception {
        String username = "Test User Name To Be Used For Login";
        String password = "Test Password To Be Used For Login";
        String role = "ROLE_WORLD";
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setAuthorities(Arrays.asList(new SimpleGrantedAuthority(role)));
        repository.save(user);
        mockMvc.perform(formLogin().user(username).password(password)).andExpect(authenticated().withRoles("WORLD"));
        mongoTemplate.dropCollection("user");
    }
    @Test
    public void shouldNotLogInAUserWithInvalidCredentials() throws Exception {
        mockMvc.perform(formLogin().user("user").password("wrongPassword")).andExpect(unauthenticated());
    }
}
