package com.example.ahmed.mal_movies_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        Movie movie = getIntent().getParcelableExtra("movie");
        DetailsFragment detailsFragment = DetailsFragment.newInstance(movie);
        getSupportFragmentManager().beginTransaction().replace(R.id.details_fragment, detailsFragment, "").commit();


    }


}
