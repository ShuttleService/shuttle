package com.real.apps.shuttle.model;

import org.bson.types.ObjectId;

/**
 * Created by zorodzayi on 14/10/10.
 */
public class Driver {
    private ObjectId id;
    private String firstName;
    private String surname;
    private String email;
    private String driversLicenseNumber;
    private String driversLicenseClass;
    private ObjectId companyId;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDriversLicenseNumber() {
        return driversLicenseNumber;
    }

    public void setDriversLicenseNumber(String driversLicenseNumber) {
        this.driversLicenseNumber = driversLicenseNumber;
    }

    public String getDriversLicenseClass() {
        return driversLicenseClass;
    }

    public void setDriversLicenseClass(String driversLicenseClass) {
        this.driversLicenseClass = driversLicenseClass;
    }

    public ObjectId getCompanyId() {
        return companyId;
    }

    public void setCompanyId(ObjectId companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {

        return String.format("{id:%s,firstName:%s,surname:%s,email:%s,driversLicenseNumber:%s,driversLicenseClass:%s,companyId:%s}",id,firstName,
                surname,email,driversLicenseNumber,driversLicenseClass,companyId);
    }
}
