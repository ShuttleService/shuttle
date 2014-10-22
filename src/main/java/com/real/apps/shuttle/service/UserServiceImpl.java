package com.real.apps.shuttle.service;

import com.real.apps.shuttle.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zorodzayi on 14/10/22.
 */
@Service
public class UserServiceImpl implements UserService{
    @Override
    public List<User> list(int skip, int limit) {
        return null;
    }
}
