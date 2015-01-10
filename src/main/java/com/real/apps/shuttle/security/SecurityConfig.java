package com.real.apps.shuttle.security;

import com.real.apps.shuttle.service.ShuttleUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by zorodzayi on 14/11/11.
 */
@Configuration
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableGlobalAuthentication
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private ShuttleUserDetailsServiceImpl userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private static final String ROLE_ADMIN = "admin";
    private static final String ROLE_AGENT = "agent";
    private static final String ROLE_COMPANY_USER = "companyUser";
    private static final String ROLE_WORLD = "world";
    private static final String ROLE_ANONYMOUS = "ANONYMOUS";

    @Override
    @Autowired
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/home").hasAnyRole(ROLE_ADMIN, ROLE_AGENT, ROLE_COMPANY_USER, ROLE_WORLD)
                .antMatchers("/").hasAnyRole(ROLE_ADMIN, ROLE_AGENT, ROLE_COMPANY_USER, ROLE_WORLD)
                .antMatchers("/admin").hasAnyRole(ROLE_ADMIN)
                .antMatchers("/agent").hasAnyRole(ROLE_ADMIN,ROLE_AGENT)
                .antMatchers("/agent/*/*").hasAnyRole(ROLE_ADMIN, ROLE_AGENT)
                .antMatchers("/vehicle").hasAnyRole(ROLE_ADMIN, ROLE_COMPANY_USER)
                .antMatchers("/driver").hasAnyRole(ROLE_ADMIN, ROLE_COMPANY_USER)
                .antMatchers("/driver/*/*").hasAnyRole(ROLE_ADMIN, ROLE_COMPANY_USER, ROLE_WORLD)
                .antMatchers("/company").hasAnyRole(ROLE_ADMIN, ROLE_AGENT)
                .antMatchers("/company/*/*").hasAnyRole(ROLE_ADMIN, ROLE_AGENT, ROLE_COMPANY_USER, ROLE_WORLD)
                .antMatchers("/user").hasAnyRole(ROLE_ADMIN, ROLE_COMPANY_USER, ROLE_ANONYMOUS).
                antMatchers("/user/*/*").hasAnyRole(ROLE_ADMIN, ROLE_COMPANY_USER)
                .antMatchers("/trip").hasAnyRole(ROLE_ADMIN, ROLE_WORLD, ROLE_COMPANY_USER)
                .antMatchers("/trip/*/*").hasAnyRole(ROLE_ADMIN, ROLE_COMPANY_USER, ROLE_WORLD)
                .and()
                .anonymous().principal("anonymous").authorities("ROLE_ANONYMOUS")
                .and()
                .rememberMe()
                .and()
                .csrf().disable()
                .formLogin().loginPage("/login").permitAll()
                .and()
                .logout();
    }
}
