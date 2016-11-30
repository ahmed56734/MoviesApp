package com.example.ahmed.mal_movies_app.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ahmed.mal_movies_app.Movie;
import com.example.ahmed.mal_movies_app.data.Contract.MovieEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahmed on 11/26/16.
 */

public class MovieDbHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "movies.db";
    static final int DATABASE_VERSION = 1;

    public MovieDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_TABLE_MOVIES = "CREATE TABLE " + MovieEntry.TABLE_NAME+ "("+ MovieEntry.COLUMN_ID +" INTEGER PRIMARY KEY, "+
                MovieEntry.COLUMN_title + " TEXT, "+
                MovieEntry.COLUMN_IMAGE_URL + " TEXT, "+
                MovieEntry.COLUMN_RELEASE_DATE + " TEXT, "+
                MovieEntry.COLUMN_VOTE_AVERAGE + " TEXT, "+
                MovieEntry.COLUMN_OVERVIEW + " TEXT"+
                ");";

        Log.i("create sql", SQL_CREATE_TABLE_MOVIES);
        db.execSQL(SQL_CREATE_TABLE_MOVIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertMovie(Movie movie){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieEntry.COLUMN_ID, movie.getID());
        contentValues.put(MovieEntry.COLUMN_title, movie.getTitle());
        contentValues.put(MovieEntry.COLUMN_IMAGE_URL, movie.getImageURL());
        contentValues.put(MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
        contentValues.put(MovieEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
        contentValues.put(MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());

        long id = db.insert(MovieEntry.TABLE_NAME, null, contentValues);
        Log.i("new row id", String.valueOf(id));
        db.close();

    }

    public int deleteMovie(int  id){

        SQLiteDatabase db = getWritableDatabase();

        String selection = MovieEntry.COLUMN_ID + " = ?";
        String args[] = new String[] {String.valueOf(id)};

        int i = db.delete(MovieEntry.TABLE_NAME, selection, args);
        db.close();
        return i;
    }

    public List<Movie> getFavoriteMovies(){

        SQLiteDatabase db = getReadableDatabase();
        List<Movie> movies = new ArrayList<>();

        Cursor cursor = db.query(MovieEntry.TABLE_NAME, null, null, null, null, null, null);

        String imageURL;
        String title;
        String releaseDate;
        String voteAverage;
        String overview;
        int ID;
        while (cursor.moveToNext()){

            ID = cursor.getInt(COL_ID);
            title = cursor.getString(COL_TITLE);
            voteAverage = cursor.getString(COL_VOTE_AVERAGE);
            releaseDate =  cursor.getString(COL_RELEASE_DATE);
            imageURL = cursor.getString(COL_IMAGE_URL);
            overview = cursor.getString(COL_OVERVIEW);


            movies.add(new Movie(ID, title, voteAverage, releaseDate, imageURL, overview));

        }
        db.close();
        return movies;
    }

    public boolean movieIsInFavorites(int id){

        SQLiteDatabase db = getReadableDatabase();

        String selection = MovieEntry.COLUMN_ID + " = ?";
        String args[] = new String[] {String.valueOf(id)};

        Cursor c = db.query(MovieEntry.TABLE_NAME, null, selection, args, null, null, null);

        if(c.moveToFirst()){
            c.close();
            return true;
        }
        else{
            c.close();
            return false;
        }

    }

    private static final int COL_ID = 0;
    private static final int COL_TITLE = 1;
    private static final int COL_IMAGE_URL = 2;
    private static final int COL_RELEASE_DATE = 3;
    private static final int COL_VOTE_AVERAGE = 4;
    private static final int COL_OVERVIEW = 5;

}
