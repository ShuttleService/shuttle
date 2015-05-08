package com.real.apps.shuttle.domain.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by zorodzayi on 14/10/10.
 */
@Document
public class Driver extends CompanyModel {
    private ObjectId id;
    @Indexed
    private String firstName;
    @Indexed
    private String surname;
    @Indexed(unique = true)
    private String email;
    private String driversLicenseNumber;
    private String driversLicenseClass;
    private Set<BookedRange> bookedRanges = new HashSet<>();

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

    public Set<BookedRange> getBookedRanges() {
        return bookedRanges;
    }

    public void setBookedRanges(Set<BookedRange> bookedRanges) {
        this.bookedRanges = bookedRanges;
    }

    @Override
    public String toString() {

        return String.format("{id:%s,firstName:%s,surname:%s,email:%s,driversLicenseNumber:%s,driversLicenseClass:%s,companyId:%s,CompanyName:%s,BookedRanges:%s}", id, firstName,
                surname, email, driversLicenseNumber, driversLicenseClass, companyId, companyName, bookedRanges);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (id == null) {
            return false;
        }
        if (!(object instanceof Driver)) {
            return false;
        }

        Driver driver = (Driver) object;

        return driver.getId() != null && driver.getId().equals(id);
    }
}
