package com.real.apps.shuttle.controller;

import com.real.apps.shuttle.domain.model.BookedRange;
import com.real.apps.shuttle.domain.model.User;
import com.real.apps.shuttle.domain.model.Vehicle;
import com.real.apps.shuttle.domain.model.service.VehicleDomainService;
import com.real.apps.shuttle.miscellaneous.Role;
import com.real.apps.shuttle.service.VehicleService;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static com.real.apps.shuttle.miscellaneous.Role.*;

/**
 * Created by zorodzayi on 14/10/09.
 */
@Controller
@RequestMapping(value = VehicleController.VIEW_PAGE)
public class VehicleController {

    public static final String VIEW_PAGE = "vehicle";
    @Autowired
    private VehicleService service;
    VehicleDomainService domainService;
    private Logger logger = Logger.getLogger(VehicleController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String render() {
        return VIEW_PAGE;
    }

    @RequestMapping(value = "/{skip}/{limit}")
    @ResponseBody
    public Page<Vehicle> page(@PathVariable("skip") int skip, @PathVariable("limit") int limit, @AuthenticationPrincipal User user) {
        logger.debug(String.format("Receiving a list request for {skip:%d,limit:%d, user:%s}", skip, limit, user));
        Page<Vehicle> emptyPage = new PageImpl<>(new ArrayList<>());

        if (user == null) {
            logger.debug(String.format("There Is No User Logged In. The User Is Null. Returning An Empty Page"));
            return emptyPage;
        } else {
            String role = Role.role(user);

            logger.debug(String.format("Logged In User Role Will Be %s ", role));
            if (ADMIN.equals(role) || WORLD.equals(role)) {
                logger.debug(String.format("Logged In As Admin Or As World. Finding All The Vehicles"));
                return service.page(skip, limit);
            } else if (COMPANY_USER.equals(role)) {
                logger.debug(String.format("User Will Have The Role Company User."));
                ObjectId companyId = user.getCompanyId();

                logger.debug(String.format("Finding Vehicle {companyId:%s}", companyId));

                if (companyId == null) {
                    logger.debug(String.format("The Logged In User Has No Company Id But Is Supposedly A Company User According To The Role. Returning An Empty Page"));
                    return emptyPage;
                } else {
                    logger.debug(String.format("The Logged In User Has A Company Id. Finding Vehicle By The Company Id"));
                    return service.pageByCompanyId(companyId, skip, limit);
                }
            }
        }

        logger.debug(String.format("Returning An Empty Page. "));
        return emptyPage;
    }

    @RequestMapping(value = "/bookable/{skip}/{limit}")
    @ResponseBody
    public Set<Vehicle> bookable(@PathVariable("skip") int skip, @PathVariable("limit") int limit, @RequestBody BookedRange bookedRange,
                                 @AuthenticationPrincipal User user) {
        logger.debug(String.format("Finding Bookable Vehicles {BookedRange:%s,skip:%d,limit:%d,\nuser:%s}", bookedRange, skip, limit, user));
        if (user == null) {
            return new HashSet<>();
        }

        String role = Role.role(user);

        switch (role) {
            case COMPANY_USER: {
                return domainService.bookable(user.getCompanyId(), new PageRequest(skip, limit), bookedRange);
            }
        }
        return new HashSet<>();
    }


    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Vehicle post(@RequestBody Vehicle vehicle) {
        logger.debug("Posting The Vehicle  " + vehicle);
        return service.insert(vehicle);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public Vehicle put(@RequestBody Vehicle vehicle) {
        logger.debug("Putting The Vehicle  " + vehicle);
        return service.update(vehicle);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/one/{id}")
    @ResponseBody
    public Vehicle getOne(@PathVariable("id") ObjectId id) {
        logger.debug(String.format("Getting One With {id:%s}", id));
        return service.findOne(id);
    }

    public void setService(VehicleService service) {
        this.service = service;
    }
}
