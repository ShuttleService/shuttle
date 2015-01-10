package com.real.apps.shuttle.repository;

import com.real.apps.shuttle.model.User;
import org.bson.types.ObjectId;
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
import static org.junit.Assert.assertNotNull;
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

    User actual = repository.findOneByUsername(username);
    assertThat(actual,is(user));
  }

  @Test
  public void shouldFindOnlyUsersWithTheGivenCompanId(){
    ObjectId id = ObjectId.get();
    ObjectId control = ObjectId.get();
    User userToBeFound = new User();
    userToBeFound.setUsername("Test Username For User To Be Found");
    userToBeFound.setEmail("Test Email For User To Be Found");
    userToBeFound.setCompanyId(id);
    User userToBeFound2 = new User();
    userToBeFound2.setEmail("Test Email For User To Be Found 2");
    userToBeFound2.setUsername("Test User Name For User To Be Found 2");
    userToBeFound2.setCompanyId(id);
    User userNotToBeFound = new User();
    userNotToBeFound.setEmail("Test Email For User Not To Be Found");
    userNotToBeFound.setUsername("Test Username For User Not To Be Found");
    userNotToBeFound.setCompanyId(control);

    assertNotNull(repository.save(userToBeFound).getId());
    assertNotNull(repository.save(userToBeFound2).getId());
    assertNotNull(repository.save(userNotToBeFound).getId());

    Page<User> actual = repository.findByCompanyId(id,new PageRequest(0,100));
    assertThat(actual.getTotalElements(),is(2l));
    assertThat(actual.getContent().get(0).getCompanyId(),is(id));
    assertThat(actual.getContent().get(1).getCompanyId(),is(id));
  }

  @Test
  public void shouldCallRepositoryFindOneAndReturnTheFoundUser(){
    User user = new User();
    user.setEmail("Test Email For User To Be Found");
    user.setUsername(user.getEmail());

    User control = new User();
    control.setEmail("Test Email For User Not To Be Found");
    control.setUsername(control.getEmail());

    ObjectId id = repository.save(user).getId();
    assertNotNull(id);
    assertNotNull(repository.save(control));

    User actual = repository.findOne(id);
    assertThat(actual,is(user));
  }
}
