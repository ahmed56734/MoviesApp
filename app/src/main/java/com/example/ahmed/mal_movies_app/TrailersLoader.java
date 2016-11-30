package com.example.ahmed.mal_movies_app;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;

/**
 * Created by ahmed on 11/30/16.
 */

public class TrailersLoader extends AsyncTaskLoader<List<Trailer>> {

    private static final String trailersBaseUrl = "http://api.themoviedb.org/3/movie/{ID}/videos?api_key="+KEYS.MOVIES_API_KEY;
    private int movieID;
    TrailersLoader(Context context, int movieID){
        super(context);
        this.movieID = movieID;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Trailer> loadInBackground() {
        List<Trailer> result ;
        String json;

        json = Utils.downloadJSON(trailersBaseUrl.replace("{ID}", String.valueOf(movieID)));
//        Log.i("json", json);
//        Log.i("id", String.valueOf(movieID));
        result = Utils.parseTrailersJSON(json);

        return  result;
    }
}
