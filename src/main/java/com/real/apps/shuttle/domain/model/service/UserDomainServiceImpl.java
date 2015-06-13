package com.real.apps.shuttle.domain.model.service;

import com.real.apps.shuttle.domain.model.User;
import com.real.apps.shuttle.domain.model.exception.NoUserForGivenUsernameException;
import com.real.apps.shuttle.repository.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


/**
 * Created by zorodzayi on 15/06/09.
 */
@Service
public class UserDomainServiceImpl implements UserDomainService {
    private static final Logger LOGGER = Logger.getLogger(UserDomainServiceImpl.class);
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository repository;

    @Override
    public void changePassword(User user, String newPassword) {
        String encryptedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encryptedPassword);
        repository.save(user);
    }

    @Override
    public void changePassword(String username, String currentPassword, String newPassword) {
        LOGGER.debug(String.format("Changing Password For {username:%s,currentPassword:%s,newPassword:%s}", username, currentPassword, newPassword));
        User user = repository.findOneByUsername(username);
        LOGGER.debug(String.format("Found User : %s ", user));
        if (user == null) {
            throw new NoUserForGivenUsernameException(username);
        }
        String encryptedCurrentPassword = passwordEncoder.encode(currentPassword);

        if (user.getPassword().equals(encryptedCurrentPassword)) {
            LOGGER.debug("Password Are The Same, Going Ahead Changing Them");
            changePassword(user, newPassword);
        } else {
            LOGGER.debug("Password Are Different. Not Changing The Passwords");
        }
    }
}
