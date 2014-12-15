package com.real.apps.shuttle.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * Created by zorodzayi on 14/12/15.
 */
public abstract class CompanyModel {
    @Indexed
    protected ObjectId companyId;
    @Indexed
    protected String companyName;

    public ObjectId getCompanyId() {
        return companyId;
    }

    public void setCompanyId(ObjectId companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
