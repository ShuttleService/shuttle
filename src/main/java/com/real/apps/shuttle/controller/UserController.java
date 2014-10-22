package com.real.apps.shuttle.controller;

import com.real.apps.shuttle.model.User;
import com.real.apps.shuttle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by zorodzayi on 14/10/22.
 */
@Controller
@RequestMapping(value = UserController.VIEW_NAME)
public class UserController {
    @Autowired
    private UserService service;

    public static final String VIEW_NAME = "user";
    @RequestMapping(method = RequestMethod.GET)
    public String render(){
        return VIEW_NAME;
    }

    @RequestMapping(method = RequestMethod.GET,value = "/{skip}/{limit}")
    @ResponseBody
    public List<User> list(@PathVariable("skip") int skip,@PathVariable("limit") int limit){
        return service.list(skip,limit);
    }
    public void setService(UserService service) {
        this.service = service;
    }
}
