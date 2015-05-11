package com.real.apps.shuttle.domain.model;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by zorodzayi on 15/01/12.
 */
public class ProprietaryTest {

    @Test
    public void shouldSetCompanyIdAndCompanyNameOnTheCompanyModel() {
        Proprietary user = new User();
        ObjectId id = ObjectId.get();
        String companyName = "Test Company Name To Set On The Proprietary";

        user.setCompanyName(companyName);
        user.setCompanyId(id);

        Driver driver = new Driver();
        driver.setCompanyIdAndCompanyName(user, Logger.getLogger(ProprietaryTest.class));
        assertThat(driver.getCompanyId(), is(id));
        assertThat(driver.getCompanyName(), is(companyName));
    }

    @Test
    public void shouldSetTheReferenceWhenTheIdIsSet() {
        Proprietary model = new Proprietary() {
        };
        model.setId(ObjectId.get());
        model.setCompanyName("Test Name");
        assertThat(model.getReference(), is(model.getId().toString()));
    }

    @Test
    public void shouldSetTheIdAndReferenceOnInit() {
        Proprietary proprietary = new Proprietary() {
        };

        assertThat(proprietary.getId(), is(notNullValue()));
        assertThat(proprietary.getReference(), is(notNullValue()));
        assertThat(proprietary.getId().toString(), is(proprietary.getReference()));
    }

    @Test
    public void shouldSetReferenceToTheIdString() {
        Proprietary model = new Proprietary() {
        };
        assertThat(model.getId(), is(notNullValue()));
        assertThat(model.getId().toString(), is(model.getReference()));
    }

    @Test
    public void shouldSetCompanyReferenceToTheCompanyIdString() {
        Proprietary proprietary = new Proprietary() {
        };
        proprietary.setCompanyId(ObjectId.get());
        assertThat(proprietary.getCompanyReference(), is(proprietary.getCompanyId().toString()));
    }
}
