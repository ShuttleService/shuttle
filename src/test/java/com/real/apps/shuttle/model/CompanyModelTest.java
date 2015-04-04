package com.real.apps.shuttle.model;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by zorodzayi on 15/01/12.
 */
public class CompanyModelTest {

    @Test
    public void shouldSetCompanyIdAndCompanyNameOnTheCompanyModel(){
        CompanyModel user = new User();
        ObjectId id = ObjectId.get();
        String companyName = "Test Company Name To Set On The CompanyModel";

        user.setCompanyName(companyName);
        user.setCompanyId(id);

        Driver driver = new Driver();
        driver.setCompanyIdAndCompanyName(user, Logger.getLogger(CompanyModelTest.class));
        assertThat(driver.getCompanyId(),is(id));
        assertThat(driver.getCompanyName(),is(companyName));
    }
}
