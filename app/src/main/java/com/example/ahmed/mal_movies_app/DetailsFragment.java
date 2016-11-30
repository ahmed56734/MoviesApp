package com.example.ahmed.mal_movies_app;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ListAdapter;

import com.example.ahmed.mal_movies_app.data.MovieDbHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {

    private Movie movie;
    static final int REVIEWS_LOADER_ID = 1;
    static final int TRAILER_LOADER_ID = 2;
    private ImageButton favoriteBtn;
    private ListView trailersListView;
    private ListView reviewsListView;
    private TrailersAdapter trailersAdapter;
    private ReviewsAdapter reviewsAdapter;




    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance(Movie movie){

        Bundle bundle = new Bundle();
        bundle.putParcelable("movie", movie);
        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(bundle);

        return detailsFragment;
    }


    private Movie getMovie(){
        return getArguments().getParcelable("movie");
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        movie = getMovie();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        TextView titleView = (TextView)rootView.findViewById(R.id.movie_title);
        TextView releaseYearView = (TextView) rootView.findViewById(R.id.movie_release_year);
        TextView voteAverageView = (TextView) rootView.findViewById(R.id.movie_vote_average);
        TextView overviewView = (TextView) rootView.findViewById(R.id.movie_overview);
        ImageView imageView = (ImageView) rootView.findViewById(R.id.movie_poster);
        TextView reviewEmptyView = (TextView) rootView.findViewById(R.id.reviews_empty_state);
        favoriteBtn = (ImageButton) rootView.findViewById(R.id.btn_favorite);
        trailersListView = (ListView) rootView.findViewById(R.id.trailers_list);
        reviewsListView = (ListView) rootView.findViewById(R.id.reviews_list);
        reviewsListView.setEmptyView(reviewEmptyView);


        titleView.setText(movie.getTitle());
        releaseYearView.setText(movie.getReleaseYear());
        voteAverageView.setText(movie.getVoteAverage());
        overviewView.append(movie.getOverview());
        Picasso.with(getContext()).load(movie.getFullImageUrlForMainFragment()).into(imageView);
        trailersAdapter = new TrailersAdapter(getContext(), new ArrayList<Trailer>());
        trailersListView.setAdapter(trailersAdapter);

        reviewsAdapter = new ReviewsAdapter(getContext(), new ArrayList<Review>());
        reviewsListView.setAdapter(reviewsAdapter);

        trailersListView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        reviewsListView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });




        trailersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.youtube.com/watch?v="+trailersAdapter.getItem(position))));
            }
        });





        favoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MovieDbHelper movieDbHelper = new MovieDbHelper(getContext());

                if(movieDbHelper.movieIsInFavorites(movie.getID())){
                    movieDbHelper.deleteMovie(movie.getID());
                    ((ImageButton)v).setImageResource(android.R.drawable.btn_star_big_off);
                    Toast.makeText(getContext(), "removed from favorites", Toast.LENGTH_SHORT).show();
                }
                else {
                    movieDbHelper.insertMovie(movie);
                    ((ImageButton)v).setImageResource(android.R.drawable.btn_star_big_on);
                    Toast.makeText(getContext(), "added to favorites", Toast.LENGTH_SHORT).show();

                }

                movieDbHelper.close();
            }
        });



        prepareFavoriteBtn();
        getLoaderManager().initLoader(TRAILER_LOADER_ID, null, trailersLoaderListner);
        getLoaderManager().initLoader(REVIEWS_LOADER_ID, null, reviewsLoaderListner);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    private LoaderManager.LoaderCallbacks<List<Trailer>> trailersLoaderListner = new LoaderManager.LoaderCallbacks<List<Trailer>>(){
        @Override
        public Loader<List<Trailer>> onCreateLoader(int id, Bundle args) {
            return new TrailersLoader(getContext(), movie.getID());
        }


        @Override
        public void onLoadFinished(Loader<List<Trailer>> loader, List<Trailer> data) {

            trailersAdapter.clear();

            if(data != null && !data.isEmpty()) {
                trailersAdapter.addAll(data);
                setListViewHeightBasedOnChildren(trailersListView);
            }


        }

        @Override
        public void onLoaderReset(Loader<List<Trailer>> loader) {
            trailersAdapter.clear();
        }
    };
    private LoaderManager.LoaderCallbacks<List<Review>> reviewsLoaderListner = new LoaderManager.LoaderCallbacks<List<Review>>(){
        @Override
        public Loader<List<Review>> onCreateLoader(int id, Bundle args) {
            return new ReviewsLoader(getContext(), movie.getID());
        }


        @Override
        public void onLoadFinished(Loader<List<Review>> loader, List<Review> data) {

            reviewsAdapter.clear();

            if(data != null && !data.isEmpty()) {
                reviewsAdapter.addAll(data);
                setListViewHeightBasedOnChildren(reviewsListView);
            }


        }

        @Override
        public void onLoaderReset(Loader<List<Review>> loader) {

        }
    };

    private void prepareFavoriteBtn(){

        MovieDbHelper movieDbHelper = new MovieDbHelper(getContext());

        if(movieDbHelper.movieIsInFavorites(movie.getID()))
            favoriteBtn.setImageResource(android.R.drawable.btn_star_big_on);
        else
            favoriteBtn.setImageResource(android.R.drawable.btn_star_big_off);

        movieDbHelper.close();
    }

    /**
     * Method for Setting the Height of the ListView dynamically.
     * Hack to fix the issue of not showing all the items of the ListView
     * when placed inside a ScrollView
     * source http://stackoverflow.com/questions/18367522/android-list-view-inside-a-scroll-view
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }




}
