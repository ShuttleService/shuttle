package com.real.apps.shuttle.controller;

import static com.real.apps.shuttle.miscellaneous.Role.*;

import com.real.apps.shuttle.model.Agent;
import com.real.apps.shuttle.model.Company;
import com.real.apps.shuttle.model.User;
import com.real.apps.shuttle.service.CompanyService;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by zorodzayi on 14/10/16.
 */
@Controller
@RequestMapping(value = CompanyController.VIEW_PAGE)
public class CompanyController {
    @Autowired
    private CompanyService service;
    private Logger logger = Logger.getLogger(CompanyController.class);
    public static final String VIEW_PAGE = "company";

    @RequestMapping(method = RequestMethod.GET)
    public String render() {
        return VIEW_PAGE;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{skip}/{limit}")
    @ResponseBody
    public Page<Company> page(@PathVariable("skip") int skip, @PathVariable("limit") int limit,@AuthenticationPrincipal User user) {
        logger.debug(String.format("Getting List With {skip:%d,limit:%d,user:%s}", skip, limit,user));
        Page<Company> emptyPage = new PageImpl<>(new ArrayList<Company>());
        if (user == null){
            logger.debug("There is no logged in user. Returning empty page");
            return emptyPage;
        }

        String role = user.getAuthorities() != null && user.getAuthorities().iterator().hasNext() ? user.getAuthorities().iterator().next().getAuthority() : "";
        logger.debug(String.format("Finding Companies For Role %s",role));
        if(ADMIN.equals(role) || WORLD.equals(role)){
            logger.debug("We have Either An Admin or A World Logged in. Finding all companies");
            return service.page(skip,limit);
        }
        else if(AGENT.equals(role)){
            ObjectId agentId = user.getAgentId();
            logger.debug(String.format("We have an agent finding companies {agentId:%s} ",agentId));
            if(agentId == null ){
                logger.debug("Agent Id null and return empty page");
                return emptyPage;
            }

            logger.debug("Agent Id not null searching the database for companies belonging to the agent id ");
            return service.pageByAgentId(user.getAgentId(),skip,limit);
        }else if(COMPANY_USER.equals(role)){
            ObjectId companyId = user.getCompanyId();
            logger.debug("We have a Company User. Finding The Company. {CompanyId:"+companyId+"}");
            if(companyId == null){
                logger.debug("Company Id is null returning Empty Page");
                return emptyPage;
            }else{
                logger.debug(String.format("Finding One Company {CompanyId:%s}",companyId));
                Company company = service.findOne(companyId);
                if(company == null ){
                    logger.debug("Could not find the Company with Id "+companyId);
                }else{
                    logger.debug(String.format("Adding found company to page list"));
                    return new PageImpl<>(Arrays.asList(company));
                }
            }
        }

        logger.debug("There was a logged in user but returning empty page still.");
        return emptyPage;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Company post(@RequestBody Company company) {
        logger.debug(String.format("Posting With Company:%s", company));
        return service.insert(company);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public Company put(@RequestBody Company company){
        logger.debug(String.format("Putting With Company: %s",company));
        return service.update(company);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE)
    public Company delete(@RequestBody Company company){
        logger.debug(String.format("Deleting With Company:%s",company));
        return service.delete(company);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET,value = "/one/{id}")
    public Company getOne(@PathVariable("id")ObjectId id){
        logger.debug(String.format("Getting One With Company:%s",id));
        return service.findOne(id);
    }

    public void setService(CompanyService service) {
        this.service = service;
    }
}
