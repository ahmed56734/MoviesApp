package com.example.ahmed.mal_movies_app;


import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;


import com.example.ahmed.mal_movies_app.data.MovieDbHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment  {


    private MoviesPostersAdapter moviesPostersAdapter;
    private GridView moviesGridView;
    private static final int SORT_BY_POPULARITY = 1;
    //private static final int SORT_BY_TOP_RATED = 2;
    private static final int FAVORITE_MOVIES = 3;
    private int currentViewOption = SORT_BY_POPULARITY; //default value
    private static final String popularityURL = "http://api.themoviedb.org/3/movie/popular?api_key="+KEYS.MOVIES_API_KEY;
    private static final String topRatedURL = "http://api.themoviedb.org/3/movie/top_rated?api_key="+KEYS.MOVIES_API_KEY;
    private  MovieListener movieListener ;

    public MainFragment() {
        // Required empty public constructor
    }

    void setMovieListener(MovieListener movieListener){
        this.movieListener = movieListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_refresh:
                updateMoviesUi();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        moviesPostersAdapter = new MoviesPostersAdapter(getContext(), new ArrayList<Movie>());
        moviesGridView = (GridView)view.findViewById(R.id.posters_grid);
        moviesGridView.setAdapter(moviesPostersAdapter);

        moviesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie selectedMovie = moviesPostersAdapter.getItem(position);
                movieListener.showSelectedMovieDetails(selectedMovie);
            }
        });

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onStart() {
        super.onStart();
        updateMoviesUi();
    }

    private void updateMoviesUi(){

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String option = sharedPreferences.getString(getString(R.string.pref_sort_key), getString(R.string.pref_sort_popular) );


        if(option.equals(getString(R.string.pref_sort_popular)) || option.equals(getString(R.string.pref_sort_top_rated)) )
            new MoviesTask().execute(getMoviesUrl(option));

        else if(option.equals(getString(R.string.pref_sort_favorite))){

            MovieDbHelper movieDbHelper = new MovieDbHelper(getContext());

            List<Movie> movies = movieDbHelper.getFavoriteMovies();

            moviesPostersAdapter.clear();
            moviesPostersAdapter.addAll(movies);

            movieDbHelper.close();



        }
    }

    private String getMoviesUrl(String option){


        if(option.equals(getString(R.string.pref_sort_popular)))
            return popularityURL;

        return topRatedURL;
    }



    class MoviesTask extends AsyncTask<String, Void, List<Movie> > {

        @Override
        protected List<Movie> doInBackground(String... urls) {

            String json = Utils.downloadJSON(urls[0]);
            return Utils.parseMoviesJSON(json);
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {

            if(movies != null){
                moviesPostersAdapter.clear();
                moviesPostersAdapter.addAll(movies);
            }

        }
    }






}
