package com.real.apps.shuttle.model;

import org.bson.types.ObjectId;

/**
 * Created by zorodzayi on 14/10/04.
 */
public class User {
    private ObjectId id;
    private String userName;
    private String password;

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
}
