package com.example.ahmed.mal_movies_app;

/**
 * Created by ahmed on 11/30/16.
 */

public class Trailer {

    private String name;
    private String url;

    public Trailer(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
