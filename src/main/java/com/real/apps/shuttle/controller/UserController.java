package com.real.apps.shuttle.controller;

import com.real.apps.shuttle.model.User;
import com.real.apps.shuttle.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zorodzayi on 14/10/22.
 */
@Controller
@RequestMapping(value = UserController.VIEW_NAME)
public class UserController {
    @Autowired
    private UserService service;
    private Logger logger = Logger.getLogger(UserController.class);
    public static final String VIEW_NAME = "user";

    @RequestMapping(method = RequestMethod.GET)
    public String render() {
        return VIEW_NAME;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{skip}/{limit}")
    @ResponseBody
    public Page<User>page(@PathVariable("skip") int skip, @PathVariable("limit") int limit) {
        logger.debug(String.format("Listing users {skip:%s,limit:%s}", skip, limit));
        return service.list(skip, limit);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public User post(@RequestBody User user) {
        logger.debug("Posting User " + user);
        return service.insert(user);
    }

    public void setService(UserService service) {
        this.service = service;
    }
}
