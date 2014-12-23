package com.real.apps.shuttle.converter;

import com.google.gson.InstanceCreator;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.lang.reflect.Type;

/**
 * Created by zorodzayi on 14/12/23.
 */
public class SimpleGrantedAuthorityInstanceCreator implements InstanceCreator<SimpleGrantedAuthority> {

    @Override
    public SimpleGrantedAuthority createInstance(Type type) {
        return new SimpleGrantedAuthority("dummy_role");
    }
}
