package com.real.apps.shuttle.controller;

import com.real.apps.shuttle.model.Company;
import com.real.apps.shuttle.service.CompanyService;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<Company> list(@PathVariable("skip") int skip, @PathVariable("limit") int limit) {
        logger.debug(String.format("Getting List With {skip:%d,limit:%d}", skip, limit));
        return service.list(skip, limit);
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
