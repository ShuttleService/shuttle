package com.real.apps.shuttle.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by zorodzayi on 14/10/11.
 */
@Document
public class Review {
    @Id
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
        return String.format("{id:%s,reviews:[%s]}", id, reviews);
    }
    @Override
    public boolean equals(Object object){
        if( this == object){
            return true;
        }
        if(id == null){
            return false;
        }

        if(!(object instanceof Review)){
            return false;
        }

        Review review = (Review)object;
        return id.equals(review.getId());
    }
}
