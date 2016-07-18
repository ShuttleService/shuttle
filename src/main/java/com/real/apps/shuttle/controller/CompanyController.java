package com.real.apps.shuttle.controller;

import com.real.apps.shuttle.domain.model.Company;
import com.real.apps.shuttle.domain.model.User;
import com.real.apps.shuttle.service.CompanyService;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;

import static com.real.apps.shuttle.miscellaneous.Role.*;

/**
 * Created by zorodzayi on 14/10/16.
 */
@Controller
public class CompanyController {
    @Autowired
    private CompanyService service;
    private Logger logger = Logger.getLogger(CompanyController.class);
    public static final String VIEW_PAGE = "company";

    @RequestMapping(method = RequestMethod.GET, value = "/company-add")
    public String renderAdd() {
        return "company-add";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/" + VIEW_PAGE)
    public String render() {
        return VIEW_PAGE;
    }

    @RequestMapping(method = RequestMethod.GET, value = VIEW_PAGE + "/{skip}/{limit}")
    @ResponseBody
    public Page<Company> page(@PathVariable("skip") int skip, @PathVariable("limit") int limit, @AuthenticationPrincipal User user) {
        logger.debug(String.format("Getting List With {skip:%d,limit:%d,user:%s}", skip, limit, user));
        Page<Company> emptyPage = new PageImpl<>(new ArrayList<Company>());
        if (user == null) {
            logger.debug("There is no logged in user. Returning empty page");
            return emptyPage;
        }

        String role = user.getAuthorities() != null && user.getAuthorities().iterator().hasNext() ? user.getAuthorities().iterator().next().getAuthority() : "";
        logger.debug(String.format("Finding Companies For Role %s", role));
        if (ADMIN.equals(role) || WORLD.equals(role)) {
            logger.debug("We have Either An Admin or A World Logged in. Finding all companies");
            return service.page(skip, limit);
        } else if (AGENT.equals(role)) {
            ObjectId agentId = user.getAgentId();
            logger.debug(String.format("We have an agent finding companies {agentId:%s} ", agentId));
            if (agentId == null) {
                logger.debug("Agent Id null and return empty page");
                return emptyPage;
            }

            logger.debug("Agent Id not null searching the database for companies belonging to the agent id ");
            return service.pageByAgentId(user.getAgentId(), skip, limit);
        } else if (COMPANY_USER.equals(role)) {
            ObjectId companyId = user.getCompanyId();
            logger.debug("We have a Company User. Finding The Company. {CompanyId:" + companyId + "}");
            if (companyId == null) {
                logger.debug("Company Id is null returning Empty Page");
                return emptyPage;
            } else {
                logger.debug(String.format("Finding One Company {CompanyId:%s}", companyId));
                Company company = service.findOne(companyId);
                if (company == null) {
                    logger.debug("Could not find the Company with Id " + companyId);
                } else {
                    logger.debug(String.format("Adding found company to page list"));
                    return new PageImpl<>(Arrays.asList(company));
                }
            }
        }

        logger.debug("There was a logged in user but returning empty page still.");
        return emptyPage;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/" + VIEW_PAGE)
    public Company post(@RequestBody Company company, @AuthenticationPrincipal User user) {
        logger.debug(String.format("Posting Company {company:%s,Logged In User:%s}", company, user));
        if (user == null) {
            logger.debug("No User Is Logged In Therefore Not Inserting Any Company. Returning The Un Saved Company");
            return company;
        } else {
            String role = user.getAuthorities() != null && !user.getAuthorities().isEmpty() ? user.getAuthorities().iterator().next().getAuthority() : "";
            logger.debug(String.format("{Role:%s}", role));
            switch (role) {
                case ADMIN: {
                    logger.debug(String.format("Logged In As Admin. Inserting the user as is"));
                    return service.insert(company);
                }
                case AGENT: {
                    ObjectId agentId = user.getAgentId();
                    String agentName = user.getAgentName();
                    logger.debug(String.format("Logged In As Agent. Setting The Agent Id And Agent Name To The One On The Logged In User {AgentId:%s,AgentName:%s}", agentId, agentName));
                    company.setAgentId(agentId);
                    company.setAgentName(agentName);
                    return service.insert(company);
                }
            }
        }
        logger.debug("Did not do anything to the company. Returning it");
        return company;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT, value = "/" + VIEW_PAGE)
    public Company put(@RequestBody Company company) {
        logger.debug(String.format("Putting With Company: %s", company));
        return service.update(company);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE, value = "/" + VIEW_PAGE)
    public Company delete(@RequestBody Company company, @AuthenticationPrincipal User user) {
        logger.debug(String.format("Deleting Company {Company:%s,LoggedInUser:%s", company, user));
        if (user == null) {
            logger.debug(String.format("Attempting To Delete Company With No User Logged In. Delete Will Not Proceed. Returning The Company That Was Not Deleted"));
            return company;
        } else {
            logger.debug("There is A User Logged In. ");
            String role = user.getAuthorities() != null && !user.getAuthorities().isEmpty() ? user.getAuthorities().iterator().next().getAuthority() : "";
            logger.debug(String.format("{role:%s}", role));
            switch (role) {
                case ADMIN: {
                    logger.debug(String.format("Logged In As Admin. Going Ahead With The Delete And Returning The Deleted Company"));
                    return service.delete(company);
                }
                case AGENT: {
                    logger.debug(String.format("Logged In As An Agent. An Agent Cannot Delete A Company For Now. Returning The Undeleted Company"));
                    return company;
                }
            }
        }
        return company;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/" + VIEW_PAGE + "/one/{id}")
    public Company getOne(@PathVariable("id") ObjectId id) {
        logger.debug(String.format("Getting One With Company:%s", id));
        return service.findOne(id);
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

    }

    public void setService(CompanyService service) {
        this.service = service;
    }
}
