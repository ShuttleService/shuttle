package com.real.apps.shuttle.controller;

import com.real.apps.shuttle.domain.model.Agent;
import com.real.apps.shuttle.domain.model.User;
import com.real.apps.shuttle.service.AgentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.real.apps.shuttle.miscellaneous.Role.ADMIN;
import static com.real.apps.shuttle.miscellaneous.Role.AGENT;

/**
 * Created by zorodzayi on 14/12/17.
 */
@Controller
@RequestMapping
public class AgentController {
    public static final String VIEW_NAME = "agent";
    @Autowired
    private AgentService service;
    private Logger logger = LoggerFactory.getLogger(AgentController.class);

    @RequestMapping("/agent-add")
    public String renderAdd() {
        return "agent-add";
    }

    @RequestMapping("/" + VIEW_NAME)
    public String render() {
        return VIEW_NAME;
    }

    @RequestMapping(value = "/" + VIEW_NAME + "/{skip}/{limit}")
    @ResponseBody
    public Page<Agent> page(@PathVariable("skip") int skip, @PathVariable("limit") int limit, @AuthenticationPrincipal User user) {
        logger.debug(String.format("Finding Agents {skip:%d,limit:%d,authenticatedUser:%s}", skip, limit, user));
        Page<Agent> emptyPage = new PageImpl<>(new ArrayList<Agent>());
        if (user == null) {
            logger.debug(String.format("There Is No Authenticated User. Will Return Empty Page"));
            return emptyPage;
        }

        String role = user.getAuthorities() != null && user.getAuthorities().iterator().hasNext() ?
                user.getAuthorities().iterator().next().getAuthority() : "";

        if (ADMIN.equals(role)) {
            Page<Agent> agents = service.page(skip, limit);
            logger.info(String.format("Admin logged in found and returning %s agents ", agents));
            return agents;
        } else if (AGENT.equals(role) && user.getAgentId() != null) {
            Agent agent = service.findOne(user.getAgentId());
            List<Agent> agents = new ArrayList<>();
            if (agent != null) {
                agents.add(agent);
            }
            logger.info(String.format("Agent logged in found and returning %s agents ", agents));
            return new PageImpl<>(agents);
        }
        return emptyPage;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/" + VIEW_NAME)
    @ResponseBody
    public Agent post(@RequestBody Agent agent, @AuthenticationPrincipal User user) {
        logger.debug(String.format("Posting agent %s {user:%s}", agent, user));
        if (user == null) {
            logger.debug("There is no user logged in. Returning without saving the agent");
        } else {
            String role = user.getAuthorities() != null && !user.getAuthorities().isEmpty() ? user.getAuthorities().iterator().next().getAuthority() : "";
            switch (role) {
                case ADMIN: {
                    logger.debug(String.format("An Admin Is Logged In. Inserting The Agent"));
                    return service.insert(agent);
                }
                case AGENT: {
                    logger.debug(String.format("An Agent Is Logged In. Not Inserting The Agent. An Agent Cannot Create Another Agent"));
                    return agent;
                }
            }
        }
        logger.debug("Could Not Insert Agent. Returning unsaved agent");
        return agent;
    }

    public void setService(AgentService service) {
        this.service = service;
    }

}
