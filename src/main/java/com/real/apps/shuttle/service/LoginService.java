package com.real.apps.shuttle.service;

import com.real.apps.shuttle.controller.LoginController;
import com.real.apps.shuttle.model.User;

/**
 * Created by zorodzayi on 14/10/04.
 */
public interface LoginService {
    User login(User login);
}
