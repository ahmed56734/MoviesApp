package com.example.ahmed.mal_movies_app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by ahmed on 11/24/16.
 */

public class MoviesPostersAdapter extends ArrayAdapter<Movie> {



    public MoviesPostersAdapter(Context context, List<Movie> objects) {
        super(context, 0, objects);
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_poster_item, parent, false);


        Movie movie = getItem(position);

        ImageView posterView = (ImageView) convertView.findViewById(R.id.poster_item);
        Picasso.with(getContext()).load(movie.getFullImageUrlForMainFragment()).into(posterView);




        return convertView;
    }
}
