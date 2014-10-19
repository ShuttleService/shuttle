package com.real.apps.shuttle.controller;

import com.real.apps.shuttle.model.Trip;
import com.real.apps.shuttle.service.TripService;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by zorodzayi on 14/10/05.
 */
@Controller
@RequestMapping(value = TripController.VIEW_NAME)
public class TripController {
    public final static String VIEW_NAME = "trip";
    private Logger logger = Logger.getLogger(TripController.class);
    @Autowired
    private TripService service;

    @RequestMapping(method = RequestMethod.GET)
    public String render() {
        logger.debug("Showing Trip Controller");
        return VIEW_NAME;
    }

    @RequestMapping(value = "/{skip}/{limit}")
    @ResponseBody
    public List<Trip> list(@PathVariable("skip") int skip, @PathVariable("limit") int limit) {

        logger.debug(String.format("Receiving request for list of trips {skip:%d,limit:%d}", skip, limit));
        return service.list(skip, limit);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Trip post(@RequestBody Trip trip) {
        logger.debug(String.format("Posting The Trip %s", trip));
        return service.insert(trip);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public Trip delete(@RequestBody Trip trip) {
        logger.debug(String.format("Deleting The Trip %s", trip));
        return service.delete(trip);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public Trip put(@RequestBody Trip trip){
        logger.debug(String.format("Putting Trip %s ",trip));
        return service.update(trip);
    }

    @RequestMapping(method = RequestMethod.GET,value = "/one/{id}")
    @ResponseBody
    public Trip getOne(@PathVariable("id")ObjectId id){
        logger.debug(String.format("Getting One id:%s",id));
        return service.findOne(id);
    }
    public void setService(TripService service) {
        this.service = service;
    }
}
