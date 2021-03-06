package com.real.apps.shuttle.domain.model;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by zorodzayi on 14/10/10.
 */
@Document
public class Vehicle extends Proprietary {
    @Indexed(unique = true)
    private String licenseNumber;
    @Indexed
    private String make;
    @Indexed
    private String model;
    private int year;
    private int seats;
    private String type;
    private Set<BookedRange> bookedRanges = new HashSet<>();

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<BookedRange> getBookedRanges() {
        return bookedRanges;
    }

    public void setBookedRanges(Set<BookedRange> bookedRanges) {
        this.bookedRanges = bookedRanges;
    }

    @Override
    public String toString() {
        return String.format("{id:%s,Make:%s,Model%s,YearModel:%d,Type:%s,Seats:%d,licenseNumber:%s,companyId:%s,companyName:%s,bookedRanges:%s}",
                id, make, model, year, type, seats, licenseNumber, companyId, companyName, bookedRanges);
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (id == null) {
            return false;
        }

        if (!(object instanceof Vehicle)) {
            return false;
        }

        Vehicle vehicle = (Vehicle) object;

        return id.equals(vehicle.getId());

    }
}
