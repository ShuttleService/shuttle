package com.real.apps.shuttle.model;

import org.bson.types.ObjectId;

/**
 * Created by zorodzayi on 14/10/10.
 */
public class Trip {
    private ObjectId id;
    private String source;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    @Override
    public String toString() {

        return String.format("{source:%s}", source);
    }
}
