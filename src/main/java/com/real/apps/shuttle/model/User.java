package com.real.apps.shuttle.model;

import org.bson.types.ObjectId;

import javax.jws.soap.SOAPBinding;
import java.util.Date;

/**
 * Created by zorodzayi on 14/10/04.
 */
public class User {
    private ObjectId id;
    private String userName;
    private String password;
    private String surname;
    private String firstName;
    private String email;
    private String cellNumber;
    private String dateOfBirth;
    private String streetAddress;
    private String suburb;
    private String town;
    private String province;
    private String postalCode;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {

        return String.format("{id:%s,userName:%s,password%s,firstName:%s,surname:%s,email:%s,cellNumber:%s,dateOfBirth:%s,streetAddress:%s,surburb:%s,town:%s,province:%s,postalCode:%s}",
                id, userName, password, firstName,surname, email, cellNumber, dateOfBirth, streetAddress, suburb, town, province, postalCode);
    }
    @Override
    public boolean equals(Object object){
        if(this == object ){
            return true;
        }
        if(id == null){
            return false;
        }

        if(!(object instanceof  User)){
            return false;
        }

        User user = (User)object;
        return id.equals(user.getId());
    }
}
