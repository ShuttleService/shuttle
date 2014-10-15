package com.real.apps.shuttle.controller;

import com.real.apps.shuttle.model.User;
import com.real.apps.shuttle.service.LoginService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by zorodzayi on 14/10/04.
 */
@Controller
public class LoginController {

    private static final String VIEW_NAME = "login";
    @Autowired
    private LoginService service;
    private Logger logger = Logger.getLogger(LoginController.class);

    @RequestMapping(method = RequestMethod.GET, value = {"/","/"+VIEW_NAME})
    public String render() {
        return VIEW_NAME;
    }

    @RequestMapping(method = RequestMethod.POST, value = {"/","/"+VIEW_NAME})
    public String login(User login, Model model) {
        logger.debug("Showing Login Controller ");
        User user = service.login(login);
        model.addAttribute("user", user);
        return "redirect:home";
    }

    @ModelAttribute(value = "login")
    public User prepopulateLogin() {
        return new User();
    }


    public void setService(LoginService service) {
        this.service = service;
    }

}
