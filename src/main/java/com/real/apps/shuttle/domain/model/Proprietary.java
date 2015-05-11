package com.real.apps.shuttle.domain.model;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * Created by zorodzayi on 14/12/15.
 */
public abstract class Proprietary extends Identifiable{
    @Indexed
    protected ObjectId companyId;
    @Indexed
    protected String companyName;
    @Indexed
    private String companyReference;

    protected Proprietary() {
        id = ObjectId.get();
        reference = id.toString();
    }

    public ObjectId getCompanyId() {
        return companyId;
    }

    public void setCompanyId(ObjectId companyId) {
        setCompanyReference(companyId.toString());
        this.companyId = companyId;
    }

    public String getCompanyReference() {
        return companyReference;
    }

    public void setCompanyReference(String companyReference) {
        this.companyReference = companyReference;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    public void setCompanyIdAndCompanyName(Proprietary model, Logger logger) {
        ObjectId id = model.getCompanyId();
        String name = model.getCompanyName();
        logger.debug(String.format("Setting The Company Name And Company Id To {CompanyName:%s,CompanyId:%s}", name, id));
        this.setCompanyId(id);
        this.setCompanyName(name);
    }

    @Override
    public String toString() {
        return String.format("{id:%s,CompanyId:%s,CompanyName:%s,Reference:%s,CompanyReference:%s}", id, companyId, companyName, reference, companyReference);
    }
}
