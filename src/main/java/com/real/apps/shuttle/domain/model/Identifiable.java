package com.real.apps.shuttle.domain.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * Created by zorodzayi on 15/05/11.
 */
public abstract class Identifiable {
    protected ObjectId id;
    @Indexed(unique = true)
    protected String reference;

    protected Identifiable() {
        setId(ObjectId.get());
        setReference(getId().toString());
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
        setReference(getId().toString());
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
