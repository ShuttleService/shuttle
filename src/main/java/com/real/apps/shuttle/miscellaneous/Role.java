package com.real.apps.shuttle.miscellaneous;

import com.real.apps.shuttle.domain.model.User;

/**
 * Created by zorodzayi on 15/01/07.
 */
public class Role {
    public static final String WORLD = "ROLE_world";
    public static final String COMPANY_USER = "ROLE_companyUser";
    public static final String AGENT = "ROLE_agent";
    public static final String ADMIN = "ROLE_admin";
    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static String role(User user){
        return user.getAuthorities() != null && !user.getAuthorities().isEmpty() ? user.getAuthorities().iterator().next().getAuthority() : "";
    }

}
