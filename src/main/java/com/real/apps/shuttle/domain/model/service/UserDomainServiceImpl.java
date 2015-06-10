package com.real.apps.shuttle.domain.model.service;

import com.real.apps.shuttle.domain.model.User;
import com.real.apps.shuttle.repository.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * Created by zorodzayi on 15/06/09.
 */
public class UserDomainServiceImpl implements UserDomainService {
    private static final Logger LOGGER = Logger.getLogger(UserDomainServiceImpl.class);
    PasswordEncoder passwordEncoder;
    UserRepository repository;

    @Override
    public void changePassword(User user, String newPassword) {
        String encryptedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encryptedPassword);
        repository.save(user);
    }

    @Override
    public void changePassword(String username, String oldPassword, String newPassword) {
        LOGGER.debug(String.format("Changing Password For {username:%s,oldPassword:%s,newPassword:%s}", username, oldPassword, newPassword));
        User user = repository.findOneByUsername(username);
        LOGGER.debug(String.format("Found User : %s ", user));

        String encryptedOldPassword = passwordEncoder.encode(oldPassword);

        if (user.getPassword().equals(encryptedOldPassword)) {
            LOGGER.debug("Password Are The Same, Going Ahead Changing Them");
            changePassword(user, newPassword);
        }else{
            LOGGER.debug("Password Are Different. Not Changing The Passwords");
        }
    }
}
