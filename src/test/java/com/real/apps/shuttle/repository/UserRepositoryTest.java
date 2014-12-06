package com.real.apps.shuttle.repository;

import com.real.apps.shuttle.model.User;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by zorodzayi on 14/10/31.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RepositoryConfig.class)
public class UserRepositoryTest {
  @Autowired
  private UserRepository repository;
  @Autowired
  private MongoOperations template;

  @After
  public void cleanUp() {
    template.dropCollection("user");
  }

  @Test
  public void shouldSaveUser() {
    String firstName = "Test First Name For User To Be Saved";
    User user = new User();
    user.setUsername("Test User Name");
    user.setFirstName(firstName);
    user = repository.save(user);
    assertThat(user, notNullValue());
    Query query = new Query(Criteria.where("firstName").is(firstName));
    User actual = template.findOne(query, User.class);
    assertThat(actual.getFirstName(), is(firstName));
  }

  @Test
  public void shouldFindNNumberOfUsers() {
    int skip = 0;
    int limit = 2;
    User user = new User();
    template.save(user);
    user.setUsername("Test User Name Different From The First");
    user.setEmail("Test Email Different From The First");
    template.save(user);
    Page<User> page = repository.findAll(new PageRequest(skip, limit));
    assertThat(page.getSize(), is(limit));
  }
  @Test
  public void shouldFindTheUserWithTheGivenUsername(){
    final String username = "Test User Name To Be Found In The Database";
    final String control  = "Test User Name Not To Be Found";

    User user = new User();
    user.setUsername(username);
    user.setEmail("Test Email 1");

    User user1 = new User();
    user1.setUsername(control);
    user1.setEmail("Test Email 2");

    repository.save(user);
    repository.save(user1);

    User actual = repository.findByUsername(username);
    assertThat(actual,is(user));
  }
}
