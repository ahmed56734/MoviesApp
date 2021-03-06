package com.example.ahmed.mal_movies_app;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

/**
 * Created by ahmed on 11/30/16.
 */

public class ReviewsLoader extends AsyncTaskLoader<List<Review>> {

    private int movieID;
    private static final String reviewsBaseUrl = "http://api.themoviedb.org/3/movie/{ID}/reviews?api_key="+KEYS.MOVIES_API_KEY;

    ReviewsLoader(Context context, int movieID){
        super(context);
        this.movieID = movieID;


    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Review> loadInBackground() {

        List<Review> reviews ;

        String json = Utils.downloadJSON(reviewsBaseUrl.replace("{ID}", String.valueOf(movieID)));
        reviews =  Utils.parseReviewsJSON(json);

        return reviews;
    }
}
