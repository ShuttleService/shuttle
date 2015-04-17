package com.real.apps.shuttle.domain.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private BookingRange bookingRange;

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

    public BookingRange getBookingRange() {
        return bookingRange;
    }

    public void setBookingRange(BookingRange bookingRange) {
        this.bookingRange = bookingRange;
    }

    @Override
    public String toString() {

        return String.format("{id:%s,firstName:%s,surname:%s,email:%s,driversLicenseNumber:%s,driversLicenseClass:%s,companyId:%s,CompanyName:%s,BookingRange}", id, firstName,
                surname, email, driversLicenseNumber, driversLicenseClass, companyId, companyName,bookingRange);
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
