package com.real.apps.shuttle.repository;

import com.real.apps.shuttle.domain.model.Agent;
import org.bson.types.ObjectId;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by zorodzayi on 14/12/17.
 */
public interface AgentRepository extends PagingAndSortingRepository<Agent,ObjectId> {
}
