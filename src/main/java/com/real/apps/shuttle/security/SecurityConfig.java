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
@Configuration()
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableGlobalAuthentication
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private ShuttleUserDetailsServiceImpl userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    @Autowired
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.inMemoryAuthentication().withUser("root").password("$2a$10$SvGiqPDqZPop1iCYrEbhme6hcyuERpzARj2LdREeJ0gggwd4Z35Ve").roles("admin").and().passwordEncoder(passwordEncoder);
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
          .authorizeRequests().antMatchers("/resources/**").permitAll()
                .anyRequest().hasRole("admin")
                .and()
                .anonymous().principal("anonymous").authorities("ROLE_ANONYMOUS")
                .and()
                .rememberMe()
                .and()
                .csrf().disable()
                //.and()
                .formLogin().loginPage("/login").permitAll()
                .defaultSuccessUrl("/home")
                .and()
                .logout();

    }
}
