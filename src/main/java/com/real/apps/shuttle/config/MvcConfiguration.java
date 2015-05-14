package com.real.apps.shuttle.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.real.apps.shuttle.converter.SimpleGrantedAuthorityInstanceCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

import java.text.DateFormat;

@Configuration
@ComponentScan(basePackages = "com.real.apps.shuttle")
@EnableWebMvc
public class MvcConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    public TilesViewResolver viewResolver() {
        TilesViewResolver resolver = new TilesViewResolver();
        resolver.setViewClass(TilesView.class);
        return resolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Bean
    public TilesConfigurer tilesConfigurer() {
        TilesConfigurer configurer = new TilesConfigurer();
        configurer.setDefinitions("/WEB-INF/views/views.xml");
        return configurer;
    }

    @Override
    public void configureMessageConverters(java.util.List<org.springframework.http.converter.HttpMessageConverter<?>> converters) {
        Gson gson = new GsonBuilder().registerTypeAdapter(SimpleGrantedAuthority.class, new SimpleGrantedAuthorityInstanceCreator())
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();

        GsonHttpMessageConverter messageConverter = new GsonHttpMessageConverter();
        messageConverter.setGson(gson);

        converters.add(messageConverter);
    }


}
