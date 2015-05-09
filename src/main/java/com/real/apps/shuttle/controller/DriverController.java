package com.real.apps.shuttle.controller;

import com.real.apps.shuttle.domain.model.BookedRange;
import com.real.apps.shuttle.domain.model.Driver;
import com.real.apps.shuttle.domain.model.User;
import com.real.apps.shuttle.domain.model.service.DriverDomainService;
import com.real.apps.shuttle.miscellaneous.Role;
import com.real.apps.shuttle.service.DriverService;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static com.real.apps.shuttle.miscellaneous.Role.*;

/**
 * Created by zorodzayi on 14/10/05.
 */
@Controller
@RequestMapping(value = DriverController.VIEW_NAME)
public class DriverController {
    @Autowired
    private DriverService service;
    @Autowired
    DriverDomainService domainService;
    public static final String VIEW_NAME = "driver";
    private Logger logger = Logger.getLogger(DriverController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String render() {
        logger.debug("Showing Driver Controller");
        return VIEW_NAME;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{skip}/{limit}")
    @ResponseBody
    public Page<Driver> list(@PathVariable("skip") int skip, @PathVariable("limit") int limit, @AuthenticationPrincipal User user) {
        logger.debug(String.format("Finding Drivers {skip:%d,limit:%d,user:%s}", skip, limit, user));
        Page<Driver> emptyPage = new PageImpl<>(new ArrayList<Driver>());
        if (user == null) {
            logger.debug("No User is logged in. Returning empty list of drivers ");
            return emptyPage;
        }

        String role = user.getAuthorities() != null && !user.getAuthorities().isEmpty() ? user.getAuthorities().iterator().next().getAuthority() : "";
        logger.debug("The User Role Will Be " + role);

        if (ADMIN.equals(role) || WORLD.equals(role)) {
            logger.debug(String.format("We have Either An Admin  Or A World Logged in. Finding all drivers"));
            return service.page(skip, limit);
        } else if (COMPANY_USER.equals(role)) {
            logger.debug(String.format("We have a Company User logged in. Finding the drivers for that company"));
            ObjectId companyId = user.getCompanyId();
            if (companyId == null) {
                logger.debug(String.format("Company Id on the user is null returning empty list"));
                return emptyPage;
            } else {
                logger.debug(String.format("Company Id will be %s. Finding Drivers For the Company", companyId));
                return service.pageByCompanyId(companyId, skip, limit);
            }
        }
        return emptyPage;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Driver post(@RequestBody Driver driver, @AuthenticationPrincipal User user) {
        logger.debug(String.format("Posting. Prior To Inserting The Driver {Driver:%s,Logged In User:%s}", driver, user));

        if (user == null) {
            logger.debug("There Is No User Logged In. Not Updating The Driver. Returning The Driver As Is");
            return driver;
        }

        String role = role(user);
        logger.debug(String.format("{Role:%s}", role));

        switch (role) {
            case ADMIN: {
                logger.debug("Logged In As Admin. Updating The Driver As Is And Returning The Updated Driver");
                return service.insert(driver);
            }
            case COMPANY_USER: {
                String companyName = user.getCompanyName();
                ObjectId companyId = user.getCompanyId();
                driver.setCompanyName(companyName);
                driver.setCompanyId(companyId);
                logger.debug(String.format("Logged In As A Company User. Setting The Driver Company Name And Id To That Of The Logged In User {CompanyName:%s,CompanyId:%s}, Updating And Returning The Updated Driver.", companyName, companyId));
                return service.insert(driver);
            }
        }
        return driver;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE)
    public Driver delete(@RequestBody Driver driver, @AuthenticationPrincipal User user) {
        logger.debug(String.format("Deleting Driver {Driver:%s,Logged In User:%s}", driver, user));

        if (user == null) {
            logger.debug("There Is No Logged In User. Not Deleting The Driver. Returning The Driver As Is");
            return driver;
        } else {
            String role = role(user);
            logger.debug(String.format("{role:%s}", role));

            switch (role) {
                case ADMIN: {
                    logger.debug("An Admin Is Logged In. Deleting The Driver");
                    return service.delete(driver);
                }
                case COMPANY_USER: {
                    logger.debug("An Company User Is Logged In. A Company User is not allowed To Delete Drivers. Returning The Drive intact. UnDeleted");
                    return driver;
                }
            }
        }
        logger.debug("At The End Of The Method. Have Not Deleted Anything. Returning The Driver As Is");
        return driver;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public Driver put(@RequestBody Driver driver, @AuthenticationPrincipal User user) {
        logger.debug(String.format("Putting The Driver. {Driver:%s,Logged In User:%s}", driver, user));

        if (user == null) {
            logger.debug("There Is No User Logged In. Not Updating The Driver. Returning The Driver As Is");
            return driver;
        }

        String role = role(user);

        switch (role) {
            case ADMIN: {
                logger.debug(String.format("Logged In As An Admin. Updating The Driver And Returning The Updated Driver"));
                return service.update(driver);
            }
            case COMPANY_USER: {
                String companyName = user.getCompanyName();
                ObjectId companyId = user.getCompanyId();

                logger.debug(String.format("Logged In As A Company User. Setting The Company Name And Id To The Logged In User Company Details {CompanyName:%s,CompanyId:%s}, updating the driver and returning the updated driver", companyName, companyId));
                driver.setCompanyId(companyId);
                driver.setCompanyName(companyName);
                service.update(driver);
            }
        }

        return driver;
    }

    @RequestMapping("/bookable/{companyId}/{from}/{to}/{skip}/{limit}")
    @ResponseBody
    public Set<Driver> bookable(@PathVariable("companyId") final ObjectId companyId, @PathVariable("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date from,
                                @PathVariable("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date to, @PathVariable("skip") int skip,
                                @PathVariable("limit") int limit, @AuthenticationPrincipal User user) {
        final BookedRange bookedRange = new BookedRange(from, to);
        final Pageable pageable = new PageRequest(skip, limit);
        logger.debug(String.format("Finding Bookable Drivers {BookedRange:%s, LoggedInUser:%s, skip:%d,limit:%d}", bookedRange, user, skip, limit));

        if (user == null) {
            return new HashSet<>();
        }

        String role = Role.role(user);

        logger.debug(String.format("Finding Bookable Drivers. Logged In User Role : %s ", role));
        switch (role) {
            case COMPANY_USER:
                logger.debug(String.format("Finding Bookable Drivers For Company:%s. Logged In As Company User ", user.getCompanyName()));
                return domainService.bookableDrivers(user.getCompanyId(), pageable, bookedRange);

            case ADMIN:
            case WORLD: {
                logger.debug(String.format("Finding Bookable Drivers For Company:%s. Logged In As World Or Admin", companyId));
                return domainService.bookableDrivers(companyId, pageable, bookedRange);
            }
        }

        return new HashSet<>();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/one/{id}")
    public Driver getOne(@PathVariable("id") ObjectId id) {
        logger.debug(String.format("Getting One With id %s", id));
        return service.findOne(id);
    }

    public void setService(DriverService service) {
        this.service = service;
    }
}
