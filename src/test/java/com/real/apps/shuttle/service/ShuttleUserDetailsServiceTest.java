package com.real.apps.shuttle.service;

import com.real.apps.shuttle.domain.model.User;
import com.real.apps.shuttle.repository.RepositoryConfig;
import com.real.apps.shuttle.repository.UserRepository;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
/**
 * Created by zorodzayi on 14/12/06.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceConfig.class, RepositoryConfig.class} )
public class ShuttleUserDetailsServiceTest {

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();
  @Mock
  private UserRepository repository;
  @Autowired
  private ShuttleUserDetailsServiceImpl service;

  @Test
  public void sanity(){
    assertThat(service,notNullValue());
  }
  @Test
  public void shouldReturnTheRepositoryUserDetails(){
    final User user = new User();
    final String username = "Test User Name Existing In The Repository";
    user.setUsername(username);
    service.setRepository(repository);

    context.checking(new Expectations(){{
      oneOf(repository).findOneByUsername(username);
      will(returnValue(user));
    }});

    User actual = (User)service.loadUserByUsername(username);
    assertThat(actual,is(user));
  }
}
