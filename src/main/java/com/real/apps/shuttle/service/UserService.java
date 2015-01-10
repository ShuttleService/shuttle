package com.real.apps.shuttle.service;

import com.real.apps.shuttle.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by zorodzayi on 14/10/22.
 */
public interface UserService {
    Page<User> page(int skip,int limit);
    Page<User> pageByCompanyId(ObjectId companyId,int skip, int limit);
    User findOneByUsername(String username);
    User findOne(ObjectId id);
    User insert(User user);
}
