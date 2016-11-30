package com.example.ahmed.mal_movies_app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.List;

/**
 * Created by ahmed on 11/30/16.
 */

public class ReviewsAdapter extends ArrayAdapter<Review> {

    public ReviewsAdapter(Context context, List<Review> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.review_item, parent, false);

        Review review = getItem(position);

        ((TextView) convertView.findViewById(R.id.movie_review_author_name)).setText(review.getAuthor());
        ((TextView) convertView.findViewById(R.id.movie_review)).setText(review.getReview());

        return convertView;
    }
}
