package com.real.apps.shuttle.respository;

import com.real.apps.shuttle.model.Company;
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
        int skip = 0;
        int limit = 2;

        Company company = new Company();
        operations.save(company);
        operations.save(company);
        Page<Company> page = repository.findAll(new PageRequest(skip, limit));
        assertThat(page.getSize(), is(limit));
        operations.dropCollection("company");
    }
}
