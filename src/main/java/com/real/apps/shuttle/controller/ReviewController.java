package com.real.apps.shuttle.controller;

import com.real.apps.shuttle.model.Review;
import com.real.apps.shuttle.service.ReviewService;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by zorodzayi on 14/10/11.
 */
@Controller
@RequestMapping(value = ReviewController.VIEW_PAGE)
public class ReviewController {
    @Autowired
    private ReviewService service;
    public static final String VIEW_PAGE = "review";
    private Logger logger = Logger.getLogger(ReviewController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String render(){
        return  VIEW_PAGE;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Review post(@RequestBody Review review){
        logger.debug(String.format("Posting The Review %s",review));
        return service.insert(review);
     }
    @RequestMapping("/{skip}/{limit}")
    @ResponseBody
    public Page<Review> page(@PathVariable("skip")int skip,@PathVariable("limit") int limit){
        return service.list(skip,limit);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public Review delete(@RequestBody Review review){
        logger.debug(String.format("Deleting The Review %s",review));
        return service.delete(review);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public Review put(@RequestBody Review review){
        logger.debug(String.format("Putting The Review %s",review));
        return service.update(review);
    }

    @RequestMapping(method = RequestMethod.GET,value = "/one/{id}")
    @ResponseBody
    public Review getOne(@PathVariable("id")ObjectId id){
        logger.debug(String.format("Getting One With ObejctId:%s",id));
        return service.findOne(id);
    }

    public void setService(ReviewService service) {
        this.service = service;
    }
}
