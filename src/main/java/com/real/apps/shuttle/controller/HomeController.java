package com.real.apps.shuttle.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    private Logger logger = Logger.getLogger(HomeController.class);
    private final String VIEW_PAGE = "home";
    @RequestMapping(value ={ "/","/"+VIEW_PAGE})
    public ModelAndView test(HttpServletResponse response) throws IOException {
        logger.debug("Showing The Home Page");
        return new ModelAndView("home");
    }
}
