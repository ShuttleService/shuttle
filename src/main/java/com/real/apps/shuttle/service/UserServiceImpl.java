package com.real.apps.shuttle.service;

import com.real.apps.shuttle.model.User;
import com.real.apps.shuttle.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zorodzayi on 14/10/22.
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository repository;
    @Override
    public List<User> list(int skip, int limit) {
        return null;
    }
    @Override
    public User insert(User user) {
        return repository.save(user);
    }

    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }
}
