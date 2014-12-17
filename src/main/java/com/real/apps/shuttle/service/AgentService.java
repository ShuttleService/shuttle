package com.real.apps.shuttle.service;

import com.real.apps.shuttle.model.Agent;
import org.springframework.data.domain.Page;

/**
 * Created by zorodzayi on 14/12/17.
 */
public interface AgentService {
    Page<Agent> list(int skip,int limit);
    Agent insert(Agent agent);
}
