package com.real.apps.shuttle.config;

import com.real.apps.shuttle.repository.RepositoryConfig;
import com.real.apps.shuttle.security.SecurityConfig;
import com.real.apps.shuttle.service.ServiceConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Created by zorodzayi on 14/10/04.
 */
public class WebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{SecurityConfig.class, ServiceConfig.class, RepositoryConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{MvcConfiguration.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
