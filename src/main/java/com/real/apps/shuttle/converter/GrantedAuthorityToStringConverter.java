package com.real.apps.shuttle.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

/**
 * Created by zorodzayi on 14/12/19.
 */
@Component
public class GrantedAuthorityToStringConverter implements Converter<SimpleGrantedAuthority,String> {
    @Override
    public String convert(SimpleGrantedAuthority grantedAuthority) {

        SimpleGrantedAuthority simpleGrantedAuthority = (SimpleGrantedAuthority)grantedAuthority;
        return simpleGrantedAuthority.getAuthority();
    }
}
