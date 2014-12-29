package com.real.apps.shuttle.repository;

import com.real.apps.shuttle.model.Agent;
import org.junit.After;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by zorodzayi on 14/12/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RepositoryConfig.class})
public class AgentRepositotyTest {
    @Autowired
    private AgentRepository repository;
    @Autowired
    private MongoOperations template;
    private String agentName = "Test Agent Name 1";
    private String agentName2 = "Test Agent Name 2";

    @After
    public void cleanUp(){
        template.dropCollection("agent");
    }
    @Test
    public void shouldInsertAnAgentIntoMongo(){
        String agentName = "Test Agent Name To Be Inserted Into Database";
        Agent agent = new Agent();
        agent.setFullName(agentName);

        Query query = new Query(Criteria.where("fullName").is(agentName));
        assertThat(template.findOne(query, Agent.class),nullValue());
        agent = repository.save(agent);
        assertThat(agent.getId(),notNullValue());
        Agent actual = template.findOne(query, Agent.class);
        assertThat(actual,notNullValue());
        assertThat(actual.getFullName(),is(agentName));
    }

    @Test
    public void shouldFindTheGivenNumberOfAgents(){
        Agent agent = new Agent();
        agent.setFullName(agentName);
        agent.setSlug(agentName);
        agent.setTradingAs(agentName);
        agent.setRegistrationNumber(agentName);
        agent.setVatNumber(agentName);
        Agent agent1 = new Agent();
        agent.setFullName(agentName2);
        agent1.setSlug(agentName2);
        agent1.setTradingAs(agentName2);
        agent1.setRegistrationNumber(agentName2);
        agent1.setVatNumber(agentName2);
        repository.save(agent);
        repository.save(agent1);

        Page<Agent> page = repository.findAll(new PageRequest(0,2));
        assertThat(page.getContent().size(),is(2));
    }
}
