package com.real.apps.shuttle.domain.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by zorodzayi on 14/10/16.
 */
@Document
public class Company extends Identifiable {
    @Indexed(unique = true)
    private String slug;
    @Indexed(unique = true)
    private String tradingAs;
    @Indexed(unique = true)
    private String fullName;
    private String description;
    @Indexed(unique = true)
    private String registrationNumber;
    @Indexed(unique = true)
    private String vatNumber;
    private String logo;
    @Indexed
    private String agentName;
    private String idString;
    @Indexed
    private ObjectId agentId;
    private Set<BookedRange> bookedRanges = new HashSet<>();

    public Set<BookedRange> getBookedRanges() {
        return bookedRanges;
    }

    public void setBookedRanges(Set<BookedRange> bookedRanges) {
        this.bookedRanges = bookedRanges;
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

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public ObjectId getAgentId() {
        return agentId;
    }

    public void setAgentId(ObjectId agentId) {
        this.agentId = agentId;
    }

    @Override
    public String toString() {
        return String.format("{id:%s,slug:%s,tradingAs:%s,fullName:%s,description:%s,registrationNumber:%s,vatNumber:%s,agentName:%s,agentID:%s}", id, slug, tradingAs, fullName,
                description, registrationNumber, vatNumber, agentName, agentId);
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (id == null) {
            return false;
        }

        if (!(object instanceof Company)) {
            return false;
        }
        Company company = (Company) object;
        return company.getId() != null && company.getId().equals(id);
    }
}
