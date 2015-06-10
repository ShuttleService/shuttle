package com.real.apps.shuttle.domain.model.service;

import com.real.apps.shuttle.domain.model.User;

/**
 * Created by zorodzayi on 15/06/09.
 */
public interface UserDomainService {
    void changePassword(User user, String newPassword);
    void changePassword(String username,String oldPassword,String newPassword);
}
