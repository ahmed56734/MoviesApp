package com.example.ahmed.mal_movies_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements MovieListener {

    private boolean isTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainFragment mainFragment = new MainFragment();
        mainFragment.setMovieListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, mainFragment).commit();

        if(findViewById(R.id.details_fragment) != null)
            isTwoPane = true;

    }

    @Override
    public void showSelectedMovieDetails(Movie selectedMovie) {


        if(isTwoPane) {

            DetailsFragment detailsFragment = DetailsFragment.newInstance(selectedMovie);
            getSupportFragmentManager().beginTransaction().replace(R.id.details_fragment, detailsFragment).commit();
        }

        else {

            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra("movie", selectedMovie);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_settings:
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
