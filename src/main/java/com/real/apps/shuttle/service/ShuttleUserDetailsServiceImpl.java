package com.real.apps.shuttle.service;

import com.real.apps.shuttle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by Zorodzayi on 14/12/06.
 */
@Service
public class ShuttleUserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository repository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return repository.findByUsername(username);
  }

  public void setRepository(UserRepository repository) {
    this.repository = repository;
  }
}
