package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

public class MovieDetails extends AppCompatActivity implements TrailerAdapter.TrailerAdapterOnClickHandler {

    RecyclerView trailersRecyclerView;
    ProgressBar mLoadingBar;
    String movieTrailersURL;
    ImageView thumbnail;
    TextView summary;
    TextView rating;
    TextView movieName;
    Movie thisMovie;
    Bundle recievedBundle;
    TextView releaseDate;
    TextView mErrorMessage;
    ArrayList<String> trailersList;
    ArrayList<String> reviewsList;
    TrailerAdapter trailerAdapter;
    int movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        thisMovie = new Movie();
        trailerAdapter = new TrailerAdapter(this, this);
        movieName = (TextView) findViewById(R.id.movie_name);
        releaseDate = (TextView) findViewById(R.id.release_date);
        rating = (TextView) findViewById(R.id.rating);
        mLoadingBar = (ProgressBar) findViewById(R.id.pb_loading_indicator_details);
        summary = (TextView) findViewById(R.id.movie_summary);
        thumbnail = (ImageView) findViewById(R.id.movie_thumbnail);
        mErrorMessage = (TextView) findViewById(R.id.tv_error_message_display_details);

        trailersRecyclerView = (RecyclerView) findViewById(R.id.trailer_rv);
        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra("MovieObj")) {
                recievedBundle = intentThatStartedThisActivity.getExtras();
                thisMovie = recievedBundle.getParcelable("MovieObj");
            }
        }

        movieName.setText(thisMovie.name);
        releaseDate.setText(thisMovie.year);
        rating.setText(thisMovie.rating);
        summary.setText(thisMovie.summary);
        Picasso.with(this).load(thisMovie.MOVIE_POSTER_BASE_URL + thisMovie.imageURLRelativePath).into(thumbnail);
    }


    private void showErrorMessage() {
                /* First, hide the currently visible data */
        trailersRecyclerView.setVisibility(View.INVISIBLE);
               /* Then, show the error */
        mErrorMessage.setVisibility(View.VISIBLE);
    }

    private void showTrailersAndMoviesDataView() {
                /* First, make sure the error is invisible */
        mErrorMessage.setVisibility(View.INVISIBLE);
        mLoadingBar.setVisibility(View.INVISIBLE);
                /* Then, make sure the weather data is visible */
    }


    class loadMovieTrailers extends AsyncTask<String, Void, ArrayList<String>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (isOnline()) {
                trailersRecyclerView.setVisibility(View.INVISIBLE);
                mLoadingBar.setVisibility(View.VISIBLE);
            } else {
                showErrorMessage();
            }
        }

        /*params is the the paramaters sent to this function by execute() method */
        @Override
        protected ArrayList<String> doInBackground(String... params) {
  /* If there's no urls , there's nothing to look up. */
            if (params.length == 0) {
                return null;
            }

            String trailersURL = params[0];
            String reviewsURL = params[1];
            URL trailersRequestUrl = NetworkUtils.buildUrl(trailersURL);
            URL reviewsRequestUrl = NetworkUtils.buildUrl(reviewsURL);

            try {
                String jsonTrailersResponse = NetworkUtils.getResponseFromHttpUrl(trailersRequestUrl);
                String jsonReviewsResponse = NetworkUtils.getResponseFromHttpUrl(reviewsRequestUrl);

                trailersList.clear();
                trailersList.addAll(OpenMovieJsonUtils
                        .getArrayListOfTrailersFromJson(MovieDetails.this, jsonTrailersResponse));
                reviewsList.clear();
                reviewsList.addAll(OpenMovieJsonUtils
                        .getArrayListOfReviewsFromJson(MovieDetails.this, jsonReviewsResponse));

                return trailersList;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<String> trailersList) {
            if (trailersList != null) {
                showTrailersAndMoviesDataView();
                //Recycler View for the trailers
                trailerAdapter.setTrailersList(trailersList);
                //ListView for the Reviews

            }
            super.onPostExecute(trailersList);
        }
    }


    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void onClick(String urlString) {

    }
}
