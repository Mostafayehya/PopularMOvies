package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;
    private RecyclerView imageRecyclerView;
    private MovieAdapter movieAdapter;

    // TODO replace this with a cursor
    ArrayList<Movie> movieList = new ArrayList<>(20);

    // TODO  plug in your API key
    private final String POPULAR_QUERY_URL = "http://api.themoviedb.org/3/movie/popular?api_key=c116e57a4053a96cf95605c119b5f697";
    private final String TOP_RATED_QUERY_URL = "http://api.themoviedb.org/3/movie/top_rated?api_key=c116e57a4053a96cf95605c119b5f697";

    //TODO Create the MovieDbOpenHelper class
    //TODO Create the MovieContract class
    //TODO Create the MovieProvider class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        imageRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_images);
        movieAdapter = new MovieAdapter(this, this);
        imageRecyclerView.setHasFixedSize(true);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        //this part to handle the orientation of the device and adjusting the GridLayoutManger accordingly
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            imageRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            imageRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }
        imageRecyclerView.setAdapter(movieAdapter);

        //The default query of the movies is the most popular movies query
        //  loadMoviesData(POPULAR_QUERY_URL);

        if (!isOnline()) {
            showErrorMessage();
        }

        if (savedInstanceState == null || !savedInstanceState.containsKey("movies")) {
            loadMoviesData(POPULAR_QUERY_URL);
        } else {

            movieList = savedInstanceState.getParcelableArrayList("movies");
            movieAdapter.setMoviesList(movieList);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("movies", movieList);
        super.onSaveInstanceState(outState);
    }

    void loadMoviesData(String url) {
        new loadMovieTask().execute(url);
    }

    @Override
    public void onClick(Movie movieToBeSentToMovieDetialsActivity) {

        Intent intentToStartMovieDetailsActivity = new Intent(this, MovieDetails.class);
        intentToStartMovieDetailsActivity.putExtra("MovieObj", movieToBeSentToMovieDetialsActivity);
        startActivity(intentToStartMovieDetailsActivity);
    }

    private void showMoviesDataView() {
                /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mLoadingIndicator.setVisibility(View.INVISIBLE);
                /* Then, make sure the weather data is visible */
        imageRecyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the error message visible and hide the Movies' posters
     * View.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showErrorMessage() {
                /* First, hide the currently visible data */
        imageRecyclerView.setVisibility(View.INVISIBLE);
               /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }


    class loadMovieTask extends AsyncTask<String, Void, ArrayList<Movie>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (isOnline()) {
                imageRecyclerView.setVisibility(View.INVISIBLE);
                mLoadingIndicator.setVisibility(View.VISIBLE);
            } else {
                showErrorMessage();
            }
        }

        /*params is the the paramaters sent to this function by execute() method */
        @Override
        protected ArrayList<Movie> doInBackground(String... params) {
  /* If there's no urls , there's nothing to look up. */
            if (params.length == 0) {
                return null;
            }

            String urlStringToBeRequested = params[0];
            URL movieRequestUrl = NetworkUtils.buildUrl(urlStringToBeRequested);

            try {
                String jsonMovieResponse = NetworkUtils
                        .getResponseFromHttpUrl(movieRequestUrl);

                movieList.clear();
                movieList.addAll(OpenMovieJsonUtils
                        .getArrayListOfMoviesFromJson(MainActivity.this, jsonMovieResponse));

                return movieList;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        // TODO  add the movie list to the DB .
        @Override
        protected void onPostExecute(ArrayList<Movie> m) {
            if (m != null) {
                showMoviesDataView();
                movieAdapter.setMoviesList(m);
            }
            super.onPostExecute(m);
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu_layout, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //excute task that takes the url requesting the most popularity movies
        if (id == R.id.action_sortby_popularity) {
            loadMoviesData(POPULAR_QUERY_URL);

            return true;
        }

        //excute task that request top rated movies
        if (id == R.id.action_sortby_rating) {
            loadMoviesData(TOP_RATED_QUERY_URL);
        }

        return super.onOptionsItemSelected(item);
    }
}
