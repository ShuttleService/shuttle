package com.real.apps.shuttle.controller;

import com.real.apps.shuttle.model.User;
import org.bson.types.ObjectId;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.jws.soap.SOAPBinding;

import static com.real.apps.shuttle.miscellaneous.Role.*;
import java.util.Arrays;

/**
 * Created by zorodzayi on 15/01/07.
 */
public class UserDetailsUtils {

    public static User anonymous(ObjectId companyId){
        User user = new User();
        user.setUsername("agent");
        user.setPassword("agentPassword");
        user.setCompanyId(companyId);
        user.setAgentId(companyId);
        user.setAuthorities(Arrays.asList(new SimpleGrantedAuthority("anonymous")));
        return user;
    }

    public static User agent(ObjectId companyId){
        User user = new User();
        user.setUsername("agent");
        user.setPassword("agentPassword");
        user.setCompanyId(companyId);
        user.setAgentId(companyId);
        user.setAuthorities(Arrays.asList(new SimpleGrantedAuthority(AGENT)));
        return user;
    }

    public static User world(ObjectId id){
        User user = new User();
        user.setUsername("world");
        user.setPassword("worldPassword");
        user.setId(id);
        user.setCompanyId(id);
        user.setAuthorities(Arrays.asList(new SimpleGrantedAuthority(WORLD)));
        return user;
    }

    public static User admin(ObjectId companyId){
        User user = new User();
        user.setUsername("admin");
        user.setPassword("adminPassword");
        user.setCompanyId(companyId);
        user.setAuthorities(Arrays.asList(new SimpleGrantedAuthority(ADMIN)));
        return user;
    }

    public static User companyUser(ObjectId companyId){
        User user = new User();
        user.setUsername("companyUser");
        user.setPassword("companyUserPassword");
        user.setCompanyId(companyId);
        user.setAuthorities(Arrays.asList(new SimpleGrantedAuthority(COMPANY_USER)));
        return user;
    }
}
