package com.real.apps.shuttle.converter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by zorodzayi on 14/12/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConverterConfig.class)
public class GrantedAuthorityToStringConverterTest {
    @Autowired
    private GrantedAuthorityToStringConverter converter;

    @Test
    public void shouldConvertRoleToString(){
        String role = "admin";
        GrantedAuthority authority = new SimpleGrantedAuthority(role);
        assertThat(converter.convert(authority),is(role));
    }

}
