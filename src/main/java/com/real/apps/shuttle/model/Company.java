package com.real.apps.shuttle.model;

import org.bson.types.ObjectId;

/**
 * Created by zorodzayi on 14/10/16.
 */

public class Company {
    private ObjectId id;
    private String slug;
    private String tradingAs;
    private String fullName;
    private String description;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTradingAs() {
        return tradingAs;
    }

    public void setTradingAs(String tradingAs) {
        this.tradingAs = tradingAs;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("{id:%s,slug:%s,tradingAs:%s,fullName:%s,description:%s}", id, slug, tradingAs, fullName, description);
    }

    @Override
    public boolean equals(Object object) {
        if(this == object){
            return true;
        }
        if(id == null){
            return false;
        }

        if(! (object instanceof Company) ){
            return false;
        }
        Company company = (Company)object;
        return company.getId() != null && company.getId().equals(id);
    }
}
