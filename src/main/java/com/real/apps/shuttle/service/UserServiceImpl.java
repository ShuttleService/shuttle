package com.real.apps.shuttle.service;

import com.real.apps.shuttle.miscellaneous.Role;
import com.real.apps.shuttle.model.User;
import com.real.apps.shuttle.repository.UserRepository;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;

/**
 * Created by zorodzayi on 14/10/22.
 */
@Service
public class UserServiceImpl implements UserService {
  @Autowired
  private UserRepository repository;
  @Autowired
  private PasswordEncoder passwordEncoder;
  private Logger logger = Logger.getLogger(UserServiceImpl.class);

  @Override
  public Page<User> page(int skip, int limit) {
    return repository.findAll(new PageRequest(skip, limit));
  }

  @Override
  public Page<User> pageByCompanyId(ObjectId companyId, int skip, int limit) {
    return repository.findByCompanyId(companyId,new PageRequest(skip,limit));
  }

  @Override
  public User findOneByUsername(String username) {
    return repository.findOneByUsername(username);
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

  @PostConstruct
  public void saveRootUser(){
    String username = "root";
    User actual = repository.findOneByUsername(username);

    if(actual != null){
      logger.debug(String.format("%s has already been created thus returning",username));
      return;
    }
    User user = new User();
    user.setPassword("$2a$10$SvGiqPDqZPop1iCYrEbhme6hcyuERpzARj2LdREeJ0gggwd4Z35Ve");
    user.setUsername(username);
    user.setEmail("mukuya@gmail.com");
    user.setAuthorities(Arrays.asList(new SimpleGrantedAuthority(Role.ADMIN)));
    user.setFirstName("Default Admin");
    user.setSurname("Administrator");
    logger.debug(String.format("%s has not been added to the database. Adding him to the database",username));
    repository.save(user);
  }

  public void setRepository(UserRepository repository) {
    this.repository = repository;
  }

  public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }
}
