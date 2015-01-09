package com.real.apps.shuttle.service;

import com.real.apps.shuttle.model.User;
import com.real.apps.shuttle.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by zorodzayi on 14/10/22.
 */
@Service
public class UserServiceImpl implements UserService {
  @Autowired
  private UserRepository repository;
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public Page<User> page(int skip, int limit) {
    return repository.findAll(new PageRequest(skip, limit));
  }

  @Override
  public Page<User> pageByCompanyId(ObjectId companyId, int skip, int limit) {
    return null;
  }

  @Override
  public User findOne(ObjectId id) {
    return repository.findOne(id);
  }

  @Override
  public User insert(User user) {
    if (user.getPassword() != null) {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
    return repository.save(user);
  }

  public void setRepository(UserRepository repository) {
    this.repository = repository;
  }

  public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }
}
