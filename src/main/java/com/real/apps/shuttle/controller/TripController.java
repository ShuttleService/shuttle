package com.real.apps.shuttle.controller;

import com.real.apps.shuttle.domain.model.Trip;
import com.real.apps.shuttle.domain.model.User;
import com.real.apps.shuttle.service.TripService;
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
    public Page<Trip> page(@PathVariable("skip") int skip, @PathVariable("limit") int limit,@AuthenticationPrincipal User user) {
        logger.debug(String.format("Receiving request for list of trips {skip:%d,limit:%d,user:%s}", skip, limit,user));
        Page<Trip> emptyPage = new PageImpl<>(new ArrayList<Trip>());

        if(user == null){
            logger.debug("The user is null. No user is logged in. Returning an empty page ");
            return emptyPage;
        }
        String role = user.getAuthorities() != null && !user.getAuthorities().isEmpty() ? user.getAuthorities().iterator().next().getAuthority() : "";

        logger.debug(String.format("Logged on with role {role:%s}",role));

        if(ADMIN.equals(role)){
            logger.debug(String.format("Logged On As Admin. Finding All Trips "));
            return service.page(skip,limit);
        }else if(COMPANY_USER.equals(role)){
            logger.debug(String.format("Logged In As Company User. Finding The Trips For The Company"));
            ObjectId companyId = user.getCompanyId();

            if(companyId == null){
                logger.debug(String.format("Company Id is null therefore returning empty page"));
                return emptyPage;
            }else {
                logger.debug(String.format("Company Id Will be %s. Finding Trips for The Company ",companyId));
                return service.pageByCompanyId(companyId,skip,limit);
            }
        }else if(WORLD.equals(role)){
            logger.debug(String.format("Logged In As Word. Finding Trips For The User"));
            ObjectId userId = user.getId();
            if(userId == null){
                logger.debug(String.format("The User Has No id. Weird Should Not Happen. But it did. Returning An Empty Page"));
                return emptyPage;
            }else{
                logger.debug(String.format("The User Id Will Be %s. Finding Trips For The Logged In User",userId));
                return service.pageByUserId(userId,skip,limit);
            }
        }

        return emptyPage;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Trip post(@RequestBody Trip trip,@AuthenticationPrincipal User user) {
        logger.debug(String.format("Posting The Trip {trip:%s,user:%s}", trip,user));

        if(user == null){
            logger.debug("There Is No User Logged In. Will Not Add The Trip. Returning The Trip Unsaved");
            return trip;
        }

        String role = role(user);
        logger.debug(String.format("{role:%s}",role));
        switch(role){
            case ADMIN:{
                logger.debug("Logged In As An Admin. Inserting The Trip As Is");
                return service.insert(trip);
            }
            case COMPANY_USER:{
                trip.setCompanyIdAndCompanyName(user,logger);
                return service.insert(trip);
            }
            case WORLD:{
                ObjectId id = user.getId();
                String name = user.getFirstName() + " "+user.getSurname();

                logger.debug(String.format("Logged In As World Setting The Trip Client Details To The Logged In User Details {name:%s,id:%s}",name,id));
                trip.setClientId(id);
                trip.setClientName(name);
                return service.insert(trip);
            }
        }
        return trip;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public Trip delete(@RequestBody Trip trip,@AuthenticationPrincipal User user) {
        logger.debug(String.format("Deleting The Trip {trip:%s,user:%s}", trip,user));

        if(user == null){
            logger.debug("There Is No User Logged In. The trip is not being deleted. Returning The Trip As Is");
            return trip;
        }

        String role = role(user);

        logger.debug(String.format("{role:%s}",role));

        switch (role){
            case ADMIN:{
                logger.debug("An Admin Is Logged In. Deleting The Trip And Returning The Deleted Trip");
                return service.delete(trip);
            }
        }

        logger.debug("Almost At The End Of The Method. Not Deleting The Trip. Returning It As Is");
        return trip;
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public Trip put(@RequestBody Trip trip,@AuthenticationPrincipal User user){
        logger.debug(String.format("Putting The Trip {trip:%s,user:%s}", trip,user));

        if(user == null){
            logger.debug("There Is No User Logged In. Will Not Update The Trip. Returning The Trip Unsaved");
            return trip;
        }

        String role = role(user);
        logger.debug(String.format("{role:%s}",role));
        switch(role){
            case ADMIN:{
                logger.debug("Logged In As An Admin. Updating The Trip As Is");
                return service.update(trip);
            }
            case COMPANY_USER:{
                trip.setCompanyIdAndCompanyName(user,logger);
                return service.update(trip);
            }
            case WORLD:{
                ObjectId id = user.getId();
                String name = user.getFirstName() + " "+user.getSurname();

                logger.debug(String.format("Logged In As World Setting The Trip Client Details To The Logged In User Details {name:%s,id:%s}",name,id));
                trip.setClientId(id);
                trip.setClientName(name);
                return service.update(trip);
            }
        }
        return trip;
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
