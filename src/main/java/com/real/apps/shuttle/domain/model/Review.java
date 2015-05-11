package com.real.apps.shuttle.domain.model;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by zorodzayi on 14/10/11.
 */
@Document
public class Review extends Proprietary {
    private String[] reviews;
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
        return String.format("{id:%s,companyId:%s,companyName:%s,reviews:[%s]}", id,companyId,companyName,reviews);
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
