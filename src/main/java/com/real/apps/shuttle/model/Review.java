package com.real.apps.shuttle.model;

import org.bson.types.ObjectId;

/**
 * Created by zorodzayi on 14/10/11.
 */
public class Review {
    private ObjectId id;
    private String[] reviews;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String[] getReviews() {
        return reviews;
    }

    public void setReviews(String[] reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {

        StringBuilder reviews = new StringBuilder();

        if (this.reviews != null) {

            for (String review : this.reviews) {

                reviews.append(",").append(review);
            }
        }
        return String.format("{id:%s,reviews:%s}",id,reviews);
    }
}
