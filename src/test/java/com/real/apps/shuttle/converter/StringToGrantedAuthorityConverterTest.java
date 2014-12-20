package com.real.apps.shuttle.converter;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by zorodzayi on 14/12/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConverterConfig.class)
public class StringToGrantedAuthorityConverterTest {
    @Autowired
    private StringToGrantedAuthorityConverter converter;

    @Test
    public void shouldConvertStringToGrantedAuthority(){
        String role = "admin";
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
        GrantedAuthority actual = converter.convert(role);
        assertThat((SimpleGrantedAuthority)actual,is(authority));
    }
}
