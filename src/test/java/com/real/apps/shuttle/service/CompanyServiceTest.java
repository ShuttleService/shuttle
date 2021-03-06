package com.real.apps.shuttle.service;

import com.real.apps.shuttle.domain.model.Company;
import com.real.apps.shuttle.repository.CompanyRepository;
import com.real.apps.shuttle.repository.RepositoryConfig;
import org.bson.types.ObjectId;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by zorodzayi on 14/11/01.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceConfig.class, RepositoryConfig.class})
public class CompanyServiceTest {
    @Autowired
    private CompanyServiceImpl service;
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();
    @Mock
    private CompanyRepository repository;
    private final int skip = 0;
    private final int limit = 10;

    @Test
    public void shouldCallRepositorySaveOnInsert() {
        ObjectId id = ObjectId.get();
        final Company company = new Company();
        company.setId(id);

        context.checking(new Expectations() {{
            oneOf(repository).save(company);
            will(returnValue(company));
        }});
        service.setRepository(repository);
        Company inserted = service.insert(company);
        assertThat(inserted, is(company));
    }

    @Test
    public void shouldCallRepositoryFindAll() {
        List<Company> companyList = Arrays.asList(new Company());
        final Page<Company> page = new PageImpl(companyList);
        context.checking(new Expectations() {{
            oneOf(repository).findAll(new PageRequest(skip, limit));
            will(returnValue(page));
        }});

        service.setRepository(repository);
        Page actual = service.page(skip, limit);
        assertThat(page.getContent().get(0),is(actual.getContent().get(0)));
    }

    @Test
    public void shouldCallRepositoryFindByAgentId(){
        final Page<Company> page = new PageImpl<>(Arrays.asList(new Company()));
        final ObjectId id = ObjectId.get();

        context.checking(new Expectations(){{
            oneOf(repository).findByAgentId(id,new PageRequest(skip,limit));
            will(returnValue(page));
        }});

        service.setRepository(repository);

        Page<Company> actual = service.pageByAgentId(id,skip,limit);
        assertThat(actual,is(page));
    }

    @Test
    public void shouldCallRepositoryFindOne(){
        final  Company company = new Company();
        final ObjectId id = ObjectId.get();
        company.setId(id);

        context.checking(new Expectations(){{
            oneOf(repository).findOne(id);
            will(returnValue(company));
        }});

        service.setRepository(repository);

        Company actual = service.findOne(id);
        assertThat(actual,is(company));
    }

    @Test
    public void shouldCallRepositoryDelete(){
        final Company company = new Company();

        context.checking(new Expectations(){
            {
                oneOf(repository).delete(company);
            }
        });

        service.setRepository(repository);
        Company actual = service.delete(company);
        assertThat(actual,is(company));
    }
}
