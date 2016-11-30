package com.example.ahmed.mal_movies_app;

/**
 * Created by ahmed on 11/30/16.
 */

public class Review {

    private String author;
    private String review;

    public Review(String author, String review) {
        this.author = author;
        this.review = review;
    }

    public String getAuthor() {
        return author;
    }

    public String getReview() {
        return review;
    }
}
