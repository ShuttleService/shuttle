package com.real.apps.shuttle.service;

import com.real.apps.shuttle.domain.model.Agent;

import com.real.apps.shuttle.repository.AgentRepository;
import com.real.apps.shuttle.repository.RepositoryConfig;
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
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by zorodzayi on 14/12/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceConfig.class,RepositoryConfig.class})
public class AgentServiceImplTest {

    @Autowired
    private AgentServiceImpl service;
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();
    @Mock
    private AgentRepository repository;
    @Autowired
    private AgentRepository agentRepository;
    @After
    public void cleanUp(){
        service.setRepository(agentRepository);
    }
    @Test
    public void shouldCallRepositorySave(){
        final Agent agent = new Agent();
        agent.setFullName("Test Agent Full Name To Be Mock Inserted");

        context.checking(new Expectations(){{
            oneOf(repository).save(agent);
            will(returnValue(agent));
        }});

        service.setRepository(repository);

        Agent actual = service.insert(agent);
        assertThat(actual,is(agent));
    }

    @Test
    public void shouldCallRepositoryFindAll(){
        int skip = 0;
        int limit = 1;
        List<Agent> agents = Arrays.asList(new Agent());
        final Pageable pageable = new PageRequest(skip,limit);
        final Page<Agent> page = new PageImpl<Agent>(agents);

        context.checking(new Expectations(){{
            oneOf(repository).findAll(pageable);
            will(returnValue(page));
        }});
        service.setRepository(repository);

        Page<Agent> actual = service.page(skip,limit);
        assertThat(actual,is(page));
    }

    @Test
    public void shouldCallRepositoryFindOne(){
        final ObjectId id = ObjectId.get();
        final Agent agent = new Agent();
        agent.setId(id);

        context.checking(new Expectations(){{
            oneOf(repository).findOne(id);
            will(returnValue(agent));
        }});

        service.setRepository(repository);

        Agent actual = service.findOne(id);
        assertThat(actual,is(agent));
        context.assertIsSatisfied();
    }

}
