package com.real.apps.shuttle.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by zorodzayi on 14/10/05.
 */
@Controller
public class AdminController {

    private final String VIEW_NAME = "admin";
    private Logger logger = Logger.getLogger(AdminController.class);

    @RequestMapping(value = "/"+VIEW_NAME,method = RequestMethod.GET)
    public String render(){
        logger.debug("Showing The Admin Page");
        return VIEW_NAME;
    }
}