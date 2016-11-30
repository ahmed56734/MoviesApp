package com.example.ahmed.mal_movies_app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


/**
 * Created by ahmed on 11/30/16.
 */

public class TrailersAdapter extends ArrayAdapter<Trailer> {


    public TrailersAdapter(Context context, List<Trailer> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.trailer_item, parent, false);

        Trailer trailer = getItem(position);
        Log.i("trailer", trailer.getName() + " " + trailer.getUrl());

        TextView trailerNameView = (TextView)convertView.findViewById(R.id.trailer_name);
        trailerNameView.setText(trailer.getName());


        return convertView;
    }
}
