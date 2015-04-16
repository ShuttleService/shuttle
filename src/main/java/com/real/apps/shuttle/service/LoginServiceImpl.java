package com.real.apps.shuttle.service;

import com.real.apps.shuttle.domain.model.User;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Created by zorodzayi on 14/10/04.
 */
@Service
public class LoginServiceImpl implements LoginService {

    private Logger logger = Logger.getLogger(LoginServiceImpl.class);

    @Override
    public User login(User login) {
        logger.debug(String.format("Logging in with details %s",login));
        return null;
    }
}
