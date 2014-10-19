package com.real.apps.shuttle.controller;

import com.real.apps.shuttle.model.Vehicle;
import com.real.apps.shuttle.service.VehicleService;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by zorodzayi on 14/10/09.
 */
@Controller
@RequestMapping(value = VehicleController.VIEW_PAGE)
public class VehicleController {

    public static final String VIEW_PAGE = "vehicle";
    @Autowired
    private VehicleService service;
    private Logger logger = Logger.getLogger(VehicleController.class);

    @RequestMapping( method = RequestMethod.GET)
    public String render() {
        return VIEW_PAGE;
    }

    @RequestMapping(value = "/{skip}/{limit}")
    @ResponseBody
    public List<Vehicle> list(@PathVariable("skip") int skip,@PathVariable("limit") int limit){
        logger.debug(String.format("Reciving a list request for {skip%d,limit%d}",skip,limit));
        return service.list(skip,limit);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Vehicle post(@RequestBody Vehicle vehicle){
        logger.debug("Posting The Vehicle  "+vehicle);
        return service.insert(vehicle);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public Vehicle put(@RequestBody Vehicle vehicle){
        logger.debug("Putting The Vehicle  "+vehicle);
        return service.update(vehicle);
    }

    @RequestMapping(method = RequestMethod.GET,value = "/one/{id}")
    @ResponseBody
    public Vehicle getOne(@PathVariable("id")ObjectId id){
        logger.debug(String.format("Getting One With {id:%s}",id));
        return service.findOne(id);
    }
    public void setService(VehicleService service) {
        this.service = service;
    }
}
