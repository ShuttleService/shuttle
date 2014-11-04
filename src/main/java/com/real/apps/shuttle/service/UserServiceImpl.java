package com.real.apps.shuttle.service;

import com.real.apps.shuttle.model.User;
import com.real.apps.shuttle.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Created by zorodzayi on 14/10/22.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repository;

    @Override
    public Page<User> list(int skip, int limit) {
        return repository.findAll(new PageRequest(skip, limit));
    }

    @Override
    public User insert(User user) {
        return repository.save(user);
    }

    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }
}
