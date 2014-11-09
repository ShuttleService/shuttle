package com.real.apps.shuttle.respository;

import com.real.apps.shuttle.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

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

    @Before
    public void init() {
        assertThat(repository,notNullValue());
    }

    @Test
    public void testSave() {
        String firstName = "Test First Name For User To Be Saved";
        User user = new User();
        user.setFirstName(firstName);
        user = repository.save(user);
        assertThat(user,notNullValue());
        Query query = new Query(Criteria.where("firstName").is(firstName));
        User actual = template.findOne(query, User.class);
        assertThat(actual.getFirstName(), is(firstName));
        template.remove(query,User.class);
    }
    @Test
    public void shouldFindNNumberOfUsers(){
        int skip = 0;
        int limit = 2;
        User user = new User();
        template.save(user);
        template.save(user);
        Page<User> page = repository.findAll(new PageRequest(skip,limit));
        assertThat(page.getSize(),is(limit));
        template.dropCollection("user");
    }
}
