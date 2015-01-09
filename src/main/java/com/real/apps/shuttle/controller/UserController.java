package com.real.apps.shuttle.controller;

import com.real.apps.shuttle.model.User;
import com.real.apps.shuttle.service.UserService;
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

import static com.real.apps.shuttle.miscellaneous.Role.*;
/**
 * Created by zorodzayi on 14/10/22.
 */
@Controller
@RequestMapping(value = UserController.VIEW_NAME)
public class UserController {
    @Autowired
    private UserService service;
    private Logger logger = Logger.getLogger(UserController.class);
    public static final String VIEW_NAME = "user";

    @RequestMapping(method = RequestMethod.GET)
    public String render() {
        return VIEW_NAME;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{skip}/{limit}")
    @ResponseBody
    public Page<User>page(@PathVariable("skip") int skip, @PathVariable("limit") int limit,@AuthenticationPrincipal User user) {
        logger.debug(String.format("Listing users {skip:%s,limit:%s,user:%s}", skip, limit,user));
        Page<User> emptyPage = new PageImpl<>(new ArrayList<User>());

        if(user == null ){
            logger.debug(String.format("There is no user logged in The User is null. Returning an empty page"));
            return emptyPage;
        }

        String role = user.getAuthorities() != null && !user.getAuthorities().isEmpty() ? user.getAuthorities().iterator().next().getAuthority(): "";
        logger.debug(String.format("Logged On User Has Role %s ",role));

        if(ADMIN.equals(role)){
            logger.debug(String.format("Logged On As Admin. Finding All Users"));
            return service.page(skip,limit);
        }else if(COMPANY_USER.equals(role)){
            logger.debug("Logged In As A Company User.");
            ObjectId companyId = user.getCompanyId();
            logger.debug(String.format("The Company Id For The Logged In User Will Be %s",companyId));

            if(companyId == null){
                logger.debug(String.format("The Company Id Is Null. Returning An Empty Page"));
                return emptyPage;
            }else{
                logger.debug(String.format("Finding The Users For The Company Id %s",companyId));
                return service.pageByCompanyId(companyId,skip,limit);
            }
        }else if(WORLD.equals(role)){
            ObjectId id = user.getId();
            logger.debug(String.format("World User Logged In. {id:%s}",id));

            if(id == null ){
                logger.debug("The Logged In User Has No Id. This Is Not Supposed To Happen But It Did. Returning An Empty Page");
                return emptyPage;
            }else{
                User actual = service.findOne(id);
                if(actual == null){
                    logger.debug(String.format("The User {id:%s} Was Not Found In The Database. Returning Empty Page",id));
                }else{
                    logger.debug(String.format("Found The User {id:%s}. Adding It To A Page With It As The Only User",id));
                    return new PageImpl<>(Arrays.asList(actual));
                }
            }

        }
        logger.debug(String.format("Returning An Empty Page. "));
        return emptyPage;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public User post(@RequestBody User user) {
        logger.debug("Posting User " + user);
        return service.insert(user);
    }

    public void setService(UserService service) {
        this.service = service;
    }
}
