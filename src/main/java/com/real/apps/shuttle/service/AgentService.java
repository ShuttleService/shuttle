package com.real.apps.shuttle.service;

import com.real.apps.shuttle.domain.model.Agent;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;

/**
 * Created by zorodzayi on 14/12/17.
 */
public interface AgentService {
    Page<Agent> page(int skip,int limit);
    Agent findOne(ObjectId id);
    Agent insert(Agent agent);
}
