package com.real.apps.shuttle.security;

import com.real.apps.shuttle.config.MvcConfiguration;
import com.real.apps.shuttle.model.User;
import com.real.apps.shuttle.repository.UserRepository;
import org.bson.types.ObjectId;
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

import static com.real.apps.shuttle.controller.UserDetailsUtils.*;
import static org.junit.Assert.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by zorodzayi on 15/01/05.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MvcConfiguration.class})
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
    private  ObjectId id = ObjectId.get();
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

    @Test
    public void adminShouldAccessTheAdminPage() throws Exception {
        mockMvc.perform(get("/admin").with(user(admin(id)))).andExpect(status().isOk());
    }

    @Test
    public void nonAdminShouldNotAccessTheAdminPage() throws Exception {
        String admin = "/admin";
        mockMvc.perform(get(admin).with(user(agent(id)))).andExpect(status().isForbidden());
        mockMvc.perform(get(admin).with(user(world(id)))).andExpect(status().isForbidden());
        mockMvc.perform(get(admin).with(user(companyUser(id)))).andExpect(status().isForbidden());
        mockMvc.perform(get(admin)).andExpect(status().is3xxRedirection());
    }

    @Test
    public void adminShouldAccessTheAgentPage() throws Exception {
        String agent = "/agent";
        mockMvc.perform(get(agent).with(user(admin(id)))).andExpect(status().isOk());
    }

    @Test
    public void nonAdminShouldBeForbiddenAccessToTheAgentPage() throws Exception {
        String agent = "/agent";
        mockMvc.perform(get(agent).with(user(companyUser(id)))).andExpect(status().isForbidden());
        mockMvc.perform(get(agent).with(user(world(id)))).andExpect(status().isForbidden());
        mockMvc.perform(get(agent).with(user(agent(id)))).andExpect(status().isForbidden());
        mockMvc.perform(get(agent)).andExpect(status().is3xxRedirection());
    }

    @Test
    public void adminAndAgentShouldAccessTheCompanyPage() throws Exception {
        String company = "/company";
        mockMvc.perform(get(company).with(user(admin(id)))).andExpect(status().isOk());
        mockMvc.perform(get(company).with(user(agent(id)))).andExpect(status().isOk());
    }

    @Test
    public void nonAdminAndAgentShouldBeForbiddenAccessToTheCompanyPage() throws Exception {
        String company = "/company";
        mockMvc.perform(get(company).with(user(companyUser(id)))).andExpect(status().isForbidden());
        mockMvc.perform(get(company).with(user(world(id)))).andExpect(status().isForbidden());
        mockMvc.perform(get(company)).andExpect(status().is3xxRedirection());
    }

    @Test
    public void adminAndCompanyUserShouldAccessTheDriverPage() throws Exception {
        String driver = "/driver";
        mockMvc.perform(get(driver).with(user(admin(id)))).andExpect(status().isOk());
        mockMvc.perform(get(driver).with(user(companyUser(id)))).andExpect(status().isOk());
    }

    @Test
    public void adminWorldAndCompanyUserShouldAccessTheTripPage() throws Exception {
        String trip = "/trip";
        mockMvc.perform(get(trip).with(user(admin(id)))).andExpect(status().isOk());
        mockMvc.perform(get(trip).with(user(companyUser(id)))).andExpect(status().isOk());
        mockMvc.perform(get(trip).with(user(world(id)))).andExpect(status().isOk());
    }

    @Test
    public void nonAdminCompanyUserAndWorldShouldBeForbiddenAccessToTheTripPage() throws Exception {
        String trip = "/trip";
        mockMvc.perform(get(trip).with(user(agent(id)))).andExpect(status().isForbidden());
        mockMvc.perform(get(trip)).andExpect(status().is3xxRedirection());
    }

    @Test
    public void companyUserAndAdminShouldAccessTheUserPage() throws Exception {
        String user = "/user";
        mockMvc.perform(get(user).with(user(admin(id)))).andExpect(status().isOk());
        mockMvc.perform(get(user).with(user(companyUser(id)))).andExpect(status().isOk());
    }

    @Test
    public void noneAdminAndNonCompanyUserShouldBeForbiddenAccessToTheUserPage() throws Exception {
        String user = "/user";
        mockMvc.perform(get(user).with(user(agent(id)))).andExpect(status().isForbidden());
        mockMvc.perform(get(user).with(user(world(id)))).andExpect(status().isForbidden());
        mockMvc.perform(get(user)).andExpect(status().is3xxRedirection());
    }

    @Test
    public void adminAndCompanyUserShouldAccessTheVehiclePage() throws Exception {
        String vehicle = "/vehicle";
        mockMvc.perform(get(vehicle).with(user(admin(id)))).andExpect(status().isOk());
        mockMvc.perform(get(vehicle).with(user(companyUser(id)))).andExpect(status().isOk());
    }

    @Test
    public void nonAdminAndNonCompanyShouldBeForbiddenAccessToTheVehiclePage() throws Exception {
        String vehicle = "/vehicle";
        mockMvc.perform(get(vehicle).with(user(agent(id)))).andExpect(status().isForbidden());
        mockMvc.perform(get(vehicle).with(user(world(id)))).andExpect(status().isForbidden());
        mockMvc.perform(get(vehicle)).andExpect(status().is3xxRedirection());
    }

    @Test
    public void allShouldAccessReviewPage() throws Exception {
        mockMvc.perform(get("/review")).andExpect(status().isOk());
    }
}
