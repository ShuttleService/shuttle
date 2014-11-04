package com.real.apps.shuttle.service;

import com.real.apps.shuttle.model.User;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by zorodzayi on 14/10/22.
 */
public interface UserService {
    Page<User> list(int skip,int limit);
    User insert(User user);
}
