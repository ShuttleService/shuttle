package com.real.apps.shuttle.controller;

import com.real.apps.shuttle.model.Company;
import com.real.apps.shuttle.model.Driver;
import com.real.apps.shuttle.service.DriverService;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Page<Driver> list(@PathVariable("skip") int skip,@PathVariable("limit") int limit){
        logger.debug(String.format("Getting List With {skip%d,limit%d}",skip,limit));
        return service.list(skip,limit);
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
