package com.real.apps.shuttle.service;

import com.real.apps.shuttle.model.User;
import com.real.apps.shuttle.repository.RepositoryConfig;
import com.real.apps.shuttle.repository.UserRepository;
import org.bson.types.ObjectId;
import org.jmock.Expectations;
import org.jmock.auto.Mock;

import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jws.soap.SOAPBinding;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


/**
 * Created by zorodzayi on 14/11/01.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceConfig.class, RepositoryConfig.class})
public class UserServiceTest {
    @Autowired
    private UserServiceImpl impl;
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();
    @Mock
    private UserRepository repository;
    @Mock
    private PasswordEncoder encoder;
    @Autowired
    private UserRepository userRepository;
    private final int skip = 0, limit = 10;
    @After
    public void cleanUp(){
        impl.setRepository(userRepository);
    }
    @Test
    public void shouldCallSaveOnRepository() {
        final User user = new User();

        context.checking(new Expectations() {{
            oneOf(repository).save(user);
            will(returnValue(user));
        }});

        impl.setRepository(repository);
        User inserted = impl.insert(user);
        assertThat(inserted, is(user));
    }

    @Test
    public void shouldEncryptThePasswordBeforeSaving(){
      final User user = new User();
      final String plainPassword = "Test Plain Password To Be Encrypted";
      final String encryptedPassword = "Test Encrypted Password To Be Saved";
      user.setPassword(plainPassword);

      context.checking(new Expectations(){{
        oneOf(encoder).encode(plainPassword);
        will(returnValue(encryptedPassword));
        oneOf(repository).save(user);
        will(returnValue(user));
      }});

      impl.setRepository(repository);
      impl.setPasswordEncoder(encoder);

      User actual = impl.insert(user);
      assertThat(actual.getPassword(),is(encryptedPassword));
    }

    @Test
    public void shouldCallRepositoryListWithTheGivenSkipAndLimit(){
        final Page<User> page = new PageImpl<User>(Arrays.asList(new User()));
        context.checking(new Expectations(){{
            oneOf(repository).findAll(new PageRequest(skip,limit));
            will(returnValue(page));
        }});

        impl.setRepository(repository);
        Page<User> actual = impl.page(skip,limit);
        assertThat(actual.getContent().get(0),is(page.getContent().get(0)));
    }

    @Test
    public void shouldCallRepositoryFindOneAndReturnTheFoundUser(){
        final User user = new User();
        final ObjectId id = ObjectId.get();
        user.setId(id);

        impl.setRepository(repository);

        context.checking(new Expectations(){{
            oneOf(repository).findOne(id);
            will(returnValue(user));
        }});

        User actual = impl.findOne(id);
        assertThat(actual,is(user));
    }

    @Test
    public void shouldCallRepositoryFindOneByUsername(){
        final  User user = new User();
        final String username = "Test Username To Be Found";
        user.setUsername(username);

        impl.setRepository(repository);

        context.checking(new Expectations(){{
            oneOf(repository).findOneByUsername(username);
            will(returnValue(user));
        }});

        User actual = impl.findOneByUsername(username);
        assertThat(actual,is(user));
    }

    @Test
    public void shouldCallRepositoryFindByCompanyId(){
        final User user = new User();
        final ObjectId id = ObjectId.get();
        user.setCompanyId(id);
        final Page<User> page = new PageImpl<>(Arrays.asList(user));
        impl.setRepository(repository);

        context.checking(new Expectations(){{
            oneOf(repository).findByCompanyId(id,new PageRequest(skip,limit));
            will(returnValue(page));
        }});

        Page<User> actual = impl.pageByCompanyId(id,skip,limit);
        assertThat(actual,is(page));
    }

}
