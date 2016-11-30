package com.example.ahmed.mal_movies_app.data;

import android.provider.BaseColumns;

import com.example.ahmed.mal_movies_app.Movie;

/**
 * Created by ahmed on 11/26/16.
 */

public final class Contract {

    public static class MovieEntry implements BaseColumns{

        public static final String TABLE_NAME = "favorite_movies";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_title = "title";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_IMAGE_URL = "image_url";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_OVERVIEW = "overview";

    }
}
