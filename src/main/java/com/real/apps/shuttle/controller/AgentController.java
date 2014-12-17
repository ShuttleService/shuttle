package com.real.apps.shuttle.controller;

import com.real.apps.shuttle.model.Agent;
import com.real.apps.shuttle.service.AgentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by zorodzayi on 14/12/17.
 */
@Controller
@RequestMapping(AgentController.VIEW_NAME)
public class AgentController {
    public static final String VIEW_NAME = "agent";
    @Autowired
    private AgentService service;
    private Logger logger = LoggerFactory.getLogger(AgentController.class);
    @RequestMapping
    public String render(){
        return  VIEW_NAME;
    }
    @RequestMapping(value = "/{skip}/{limit}")
    @ResponseBody
    public Page<Agent> page(@PathVariable("skip") int skip,@PathVariable("limit") int limit){
        logger.debug(String.format("Finding Agents {skip:%d,limit:%d}",skip,limit));
        return service.list(skip,limit);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Agent post(@RequestBody Agent agent){
        logger.debug(String.format("Posting agent %s",agent));
        return service.insert(agent);
    }

    public void setService(AgentService service) {
        this.service = service;
    }

}
