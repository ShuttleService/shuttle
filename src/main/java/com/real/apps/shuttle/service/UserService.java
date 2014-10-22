package com.real.apps.shuttle.service;

import com.real.apps.shuttle.model.User;

import java.util.List;

/**
 * Created by zorodzayi on 14/10/22.
 */
public interface UserService {
    List<User> list(int skip,int limit);
}
