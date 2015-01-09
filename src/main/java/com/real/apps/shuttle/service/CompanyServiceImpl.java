package com.real.apps.shuttle.service;

import com.real.apps.shuttle.model.Company;
import com.real.apps.shuttle.repository.CompanyRepository;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Created by zorodzayi on 14/10/16.
 */
@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRepository repository;
    private Logger logger = Logger.getLogger(CompanyServiceImpl.class);
    @Override
    public Company insert(Company company) {

        return repository.save(company);
    }

    @Override
    public Page<Company> page(int skip, int limit) {
        logger.debug(String.format("Finding Companies {skip:%d,limit:%d}",skip,limit));
        return repository.findAll(new PageRequest(skip,limit));
    }

    @Override
    public Page<Company> pageByAgentId(ObjectId agentId,int skip, int limit) {
        logger.debug(String.format("Finding Companies {AgentId:%s,skip:%d,limit:%d}",agentId,skip,limit));
        return repository.findByAgentId(agentId,new PageRequest(skip,limit));
    }

    @Override
    public Company update(Company company) {
        return null;
    }

    @Override
    public Company findOne(ObjectId id) {
        return null;
    }

    @Override
    public Company delete(Company company) {
        return null;
    }

    public void setRepository(CompanyRepository repository) {
        this.repository = repository;
    }
}
