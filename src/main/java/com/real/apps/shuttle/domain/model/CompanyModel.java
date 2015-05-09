package com.real.apps.shuttle.domain.model;

import org.apache.log4j.Logger;
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
    private String idString;

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

    public String getIdString() {
        if (companyId != null) {
            idString = companyId.toString();
        }
        return idString;
    }

    public void setCompanyIdAndCompanyName(CompanyModel model, Logger logger) {
        ObjectId id = model.getCompanyId();
        String name = model.getCompanyName();
        logger.debug(String.format("Setting The Company Name And Company Id To {CompanyName:%s,CompanyId:%s}", name, id));
        this.setCompanyId(id);
        this.setCompanyName(name);
    }
}
