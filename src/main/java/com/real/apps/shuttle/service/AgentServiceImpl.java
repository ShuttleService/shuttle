package com.real.apps.shuttle.service;

import com.real.apps.shuttle.model.Agent;
import com.real.apps.shuttle.repository.AgentRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Created by zorodzayi on 14/12/17.
 */
@Service
public class AgentServiceImpl implements AgentService {
    @Autowired
    private AgentRepository repository;

    @Override
    public Page<Agent> page(int skip, int limit) {
        return repository.findAll(new PageRequest(skip,limit));
    }

    @Override
    public Agent findOne(ObjectId id) {
        return repository.findOne(id);
    }

    @Override
    public Agent insert(Agent agent) {
        return repository.save(agent);
    }

    public void setRepository(AgentRepository repository) {
        this.repository = repository;
    }
}
