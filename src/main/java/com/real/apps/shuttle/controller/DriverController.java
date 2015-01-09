package com.real.apps.shuttle.controller;

import com.real.apps.shuttle.model.Driver;
import com.real.apps.shuttle.model.User;
import com.real.apps.shuttle.service.DriverService;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import static com.real.apps.shuttle.miscellaneous.Role.*;
/**
 * Created by zorodzayi on 14/10/05.
 */
@Controller
@RequestMapping( value = DriverController.VIEW_NAME)
public class DriverController {
    @Autowired
    private DriverService service;
    public static final String VIEW_NAME = "driver";
    private Logger logger = Logger.getLogger(DriverController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String render() {
        logger.debug("Showing Driver Controller");
        return VIEW_NAME;
    }

    @RequestMapping(method = RequestMethod.GET,value = "/{skip}/{limit}" )
    @ResponseBody
    public Page<Driver> list(@PathVariable("skip") int skip,@PathVariable("limit") int limit,@AuthenticationPrincipal User user){
        logger.debug(String.format("Finding Drivers {skip:%d,limit:%d,user:%s}",skip,limit,user));
        Page<Driver> emptyPage = new PageImpl<>(new ArrayList<Driver>());
        if(user == null){
            logger.debug("No User is logged in. Returning empty list of drivers ");
            return emptyPage;
        }

        String role = user.getAuthorities() != null && !user.getAuthorities().isEmpty() ? user.getAuthorities().iterator().next().getAuthority() : "";
        logger.debug("The User Role Will Be "+role);

        if(ADMIN.equals(role) || WORLD.equals(role)){
            logger.debug(String.format("We have Either An Admin  Or A World Logged in. Finding all drivers"));
            return service.page(skip,limit);
        }else if(COMPANY_USER.equals(role)){
            logger.debug(String.format("We have a Company User logged in. Finding the drivers for that company"));
            ObjectId companyId = user.getCompanyId();
            if(companyId == null){
                logger.debug(String.format("Company Id on the user is null returning empty list"));
                return emptyPage;
            }else {
                logger.debug(String.format("Company Id will be %s. Finding Drivers For the Company",companyId));
                return service.pageByCompanyId(companyId,skip,limit);
            }
        }
        return emptyPage;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Driver post(@RequestBody Driver driver){
        logger.debug(String.format("Posting. Prior To Inserting The Driver %s",driver));
        return service.insert(driver);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE)
    public Driver delete(@RequestBody Driver driver){
        logger.debug(String.format("Deleting Driver %s",driver));
        return service.delete(driver);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public Driver put(@RequestBody Driver driver){
        logger.debug(String.format("Putting The Driver %s",driver));
        return service.update(driver);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET,value = "/one/{id}")
    public Driver getOne(@PathVariable("id")ObjectId id){
        logger.debug(String.format("Getting One With id %s",id));
        return service.findOne(id);
    }


    public void setService(DriverService service) {
        this.service = service;
    }
}
