package com.real.apps.shuttle.controller;

import com.real.apps.shuttle.model.Agent;
import com.real.apps.shuttle.model.User;
import com.real.apps.shuttle.service.AgentService;
import com.real.apps.shuttle.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.real.apps.shuttle.miscellaneous.Role.*;
/**
 * Created by zorodzayi on 14/12/17.
 */
@Controller
@RequestMapping(AgentController.VIEW_NAME)
public class AgentController {
    public static final String VIEW_NAME = "agent";
    @Autowired
    private AgentService service;
    @Autowired
    private UserService userService;
    private Logger logger = LoggerFactory.getLogger(AgentController.class);
    @RequestMapping
    public String render(){
        return  VIEW_NAME;
    }
    @RequestMapping(value = "/{skip}/{limit}")
    @ResponseBody
    public Page<Agent> page(@PathVariable("skip") int skip,@PathVariable("limit") int limit,@AuthenticationPrincipal User user){
        logger.debug(String.format("Finding Agents {skip:%d,limit:%d,authenticatedUser:%s}",skip,limit,user));
        Page<Agent> emptyPage = new PageImpl<>(new ArrayList<Agent>());
        if(user == null){
            logger.debug(String.format("There Is No Authenticated User. Will Return Empty Page"));
            return emptyPage;
        }

        String role = user.getAuthorities() != null && user.getAuthorities().iterator().hasNext() ?
        user.getAuthorities().iterator().next().getAuthority() : "";

        if(ADMIN.equals(role)){
            return service.page(skip,limit);
        }else if(AGENT.equals(role) && user.getAgentId() != null ){
            Agent agent = service.findOne(user.getAgentId());
            List<Agent> agents = new ArrayList<>();
            if(agent != null ){
                agents.add(agent);
            }
            return new PageImpl<>(agents);
        }
        return emptyPage;
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
