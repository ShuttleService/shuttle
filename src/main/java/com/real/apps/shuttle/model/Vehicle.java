package com.real.apps.shuttle.model;

import org.bson.types.ObjectId;

/**
 * Created by zorodzayi on 14/10/10.
 */
public class Vehicle {
    private ObjectId id;
    private String licenseNumber;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    @Override
    public String toString() {
        return String.format("{licenseNumber:%s}", licenseNumber);
    }
}
