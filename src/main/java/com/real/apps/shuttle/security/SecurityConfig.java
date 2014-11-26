package com.real.apps.shuttle.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

/**
 * Created by zorodzayi on 14/11/11.
 */
@Configuration
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableGlobalAuthentication
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    @Autowired
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.inMemoryAuthentication().withUser("root").password("admin").roles("admin");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/resources/**").permitAll()
                .anyRequest().hasRole("admin")
                .and()
                .anonymous().principal("anonymous").authorities("ROLE_ANONYMOUS")
                .and()
                .rememberMe()
                .and()
                .csrf().disable()
                //.and()
                .formLogin().loginPage("/login").permitAll()
                .defaultSuccessUrl("/trip")
                .and()
                .logout();

    }
}
