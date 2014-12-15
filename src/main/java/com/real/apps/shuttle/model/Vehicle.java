package com.real.apps.shuttle.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by zorodzayi on 14/10/10.
 */
@Document
public class Vehicle extends CompanyModel{
    private ObjectId id;
    @Indexed(unique = true)
    private String licenseNumber;
    @Indexed
    private String make;
    @Indexed
    private String model;
    private int year;
    private int seats;
    private  String type;

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


    @Override
    public String toString() {
        return String.format("{id:%s,Make:%s,Model%s,YearModel:%d,Type:%s,Seats:%d,licenseNumber:%s,companyId:%s,companyName:%s}",id,make,model,year,type,seats,licenseNumber,companyId
                ,companyName);
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (id == null) {
            return false;
        }

        if(!(object instanceof Vehicle)){
            return false;
        }

        Vehicle vehicle = (Vehicle)object;

        return id.equals(vehicle.getId());

    }
}
