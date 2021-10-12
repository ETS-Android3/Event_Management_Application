package com.example.eventmanagementapplication.Models;

public class RatingModel {
    String ratingID,UserEmail,rating;

    public RatingModel(String ratingID, String userEmail, String rating) {
        this.ratingID = ratingID;
        UserEmail = userEmail;
        this.rating = rating;
    }

    public String getRatingID() {
        return ratingID;
    }

    public void setRatingID(String ratingID) {
        this.ratingID = ratingID;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
