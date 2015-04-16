package com.real.apps.shuttle.repository;

import com.real.apps.shuttle.domain.model.Company;
import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by zorodzayi on 14/10/31.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RepositoryConfig.class)
public class CompanyRepositoryTest {
    @Autowired
    private MongoTemplate operations;
    @Autowired
    private CompanyRepository repository;
    private int skip=0,limit = 100;

    @After
    public void init(){
        operations.dropCollection("company");
    }
    @Test
    public void shouldSaveCompanyIntoMongo() {
        String name = "Test Company Name To Be Saved";
        Company company = new Company();
        company.setFullName(name);
        company = repository.save(company);
        Query query = new Query(Criteria.where("fullName").is(name));
        Company actual = operations.findOne(query, Company.class);
        assertThat(company.getId(), notNullValue());
        assertThat(company.getFullName(), is(name));
        assertThat(actual, notNullValue());
        assertThat(actual.getId(), is(company.getId()));
        operations.remove(query, Company.class);
    }

    @Test
    public void shouldFindNNumberOfCompanies() {
        Company company = new Company();
        operations.save(company);
        operations.save(company);
        Page<Company> page = repository.findAll(new PageRequest(skip, limit));
        assertThat(page.getSize(), is(limit));
        operations.dropCollection("company");
    }

    @Test
    public void shouldFindCompaniesByAgentId(){
        ObjectId id = ObjectId.get();
        Company companyToBeFound = new Company();
        companyToBeFound.setAgentId(id);
        companyToBeFound.setSlug("Test Slug To Be Found ");
        companyToBeFound.setFullName("Test Full Name To Be Found");
        companyToBeFound.setRegistrationNumber("Test Registration Number To Be Found");
        companyToBeFound.setVatNumber("Test Vat Number To Be Found");
        companyToBeFound.setTradingAs("Test Registration Number To Be Found ");
        assertNotNull(repository.save(companyToBeFound));
        Company companyToBeFound2 = new Company();
        companyToBeFound2.setSlug("Test Slug To Be Found 2");
        companyToBeFound2.setFullName("Test Full Name To Be Found 2");
        companyToBeFound2.setRegistrationNumber("Test Registration Number To Be Found 2");
        companyToBeFound2.setVatNumber("Test Vat Number To Be Found 2");
        companyToBeFound2.setTradingAs("Test Trading As To Be Found 2");
        companyToBeFound2.setAgentId(id);
        assertNotNull(repository.save(companyToBeFound2));
        Company companyNotToBeFound = new Company();
        companyNotToBeFound.setAgentId(ObjectId.get());
        assertNotNull(repository.save(companyNotToBeFound));

        Page<Company> page = repository.findByAgentId(id,new PageRequest(skip,limit));
        assertThat(page.getTotalElements(),is(2l));
        assertThat(page.getContent().get(0).getAgentId(),is(id));
        assertThat(page.getContent().get(1).getAgentId(),is(id));
    }
}
