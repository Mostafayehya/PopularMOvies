package com.example.android.popularmovies;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.Data.MovieContract;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

public class MovieDetails extends AppCompatActivity implements TrailerAdapter.TrailerAdapterOnClickHandler {

    RecyclerView trailersRecyclerView;
    ProgressBar mLoadingBar;
    private String movieTrailersURL;
    private String movieReviewsURL;
    ImageView thumbnail;
    TextView summary;
    TextView rating;
    TextView movieName;
    Movie thisMovie;
    Bundle recievedBundle;
    TextView releaseDate;
    TextView mErrorMessage;
    ArrayList<String> trailersList = new ArrayList<>(3);
    ArrayList<String> reviewsList = new ArrayList<>(3);
    TrailerAdapter trailerAdapter;
    TextView reviewsTextView;
    ArrayAdapter<String> reviewAdatper;
    LinearLayoutManager rvManager;
    int movieId;
    String imageURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        movieName = (TextView) findViewById(R.id.movie_name);
        releaseDate = (TextView) findViewById(R.id.release_date);
        rating = (TextView) findViewById(R.id.rating);


        summary = (TextView) findViewById(R.id.movie_summary);
        thumbnail = (ImageView) findViewById(R.id.movie_thumbnail);


        trailersRecyclerView = (RecyclerView) findViewById(R.id.trailer_rv);
        trailerAdapter = new TrailerAdapter(this, this);
        trailersRecyclerView.setAdapter(trailerAdapter);
        trailersRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        mLoadingBar = (ProgressBar) findViewById(R.id.pb_loading_indicator_details);
        mErrorMessage = (TextView) findViewById(R.id.tv_error_message_display_details);


        reviewsTextView = (TextView) findViewById(R.id.reviews_text_view);


        thisMovie = new Movie();

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
        movieId = thisMovie.id;
        imageURL = thisMovie.MOVIE_POSTER_BASE_URL + thisMovie.imageURLRelativePath;

        Picasso.with(this).load(imageURL).into(thumbnail);


        if (!isOnline()) {
            showErrorMessage();
        }


        //https://api.themoviedb.org/3/movie/{id}/reviews?api_key=c116e57a4053a96cf95605c119b5f697

        // TODO (1) plug in your API key
        movieTrailersURL = "https://api.themoviedb.org/3/movie/" + movieId + "/videos?api_key=c116e57a4053a96cf95605c119b5f697&language=en-US";
        movieReviewsURL = "https://api.themoviedb.org/3/movie/" + movieId + "/reviews?api_key=c116e57a4053a96cf95605c119b5f697&language=en-US";

        if (savedInstanceState == null || !savedInstanceState.containsKey("Trailers") || !savedInstanceState.containsKey("Reviews")) {
            loadTrailersAndReviewsData(movieTrailersURL, movieReviewsURL);
        } else {

            trailersList = savedInstanceState.getStringArrayList("Trailers");
            reviewsList = savedInstanceState.getStringArrayList("Reviews");
            trailerAdapter.setTrailersList(trailersList);
            writeReviewsToTextView(reviewsList);
        }


    }

    public void addMovieToFavouritesDB(View view) {

        ContentValues values = new ContentValues();

        values.put(MovieContract.FavouriteMovies.COLUMN_NAME, thisMovie.name);
        values.put(MovieContract.FavouriteMovies.COLUMN_IMAGE_URL, imageURL);
        values.put(MovieContract.FavouriteMovies.COLUMN_DATE, thisMovie.year);
        values.put(MovieContract.FavouriteMovies.COLUMN_RATE, thisMovie.rating);
        values.put(MovieContract.FavouriteMovies.COLUMN_DESCRIBTION, thisMovie.summary);
        values.put(MovieContract.FavouriteMovies.COLUMN_TRAILER_1, "http://www.youtube.com/watch?v=" + trailersList.get(0));
        values.put(MovieContract.FavouriteMovies.COLUMN_TRAILER_2, "http://www.youtube.com/watch?v=" + trailersList.get(1));
        values.put(MovieContract.FavouriteMovies.COLUMN_TRAILER_3, "http://www.youtube.com/watch?v=" + trailersList.get(2));
        values.put(MovieContract.FavouriteMovies.COLUMN_REVIEW_1, reviewsList.get(0));
        values.put(MovieContract.FavouriteMovies.COLUMN_REVIEW_2, reviewsList.get(1));
        values.put(MovieContract.FavouriteMovies.COLUMN_REVIEW_3, reviewsList.get(2));


        Uri uri = getContentResolver().insert(MovieContract.FavouriteMovies.CONTENT_URI, values);

        if (uri != null) {
            Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG);
        }


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putStringArrayList("Trailers", trailersList);
        outState.putStringArrayList("Reviews", reviewsList);
        super.onSaveInstanceState(outState);
    }


    void loadTrailersAndReviewsData(String trailerURL, String reviewsURL) {
        new FetchTrailersAndReviewsTask().execute(trailerURL, reviewsURL);
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
        trailersRecyclerView.setVisibility(View.VISIBLE);

                /* Then, make sure the weather data is visible */
    }


    class FetchTrailersAndReviewsTask extends AsyncTask<String, Void, ArrayList<String>> {

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

                //work around when the size is less than 3
                if (reviewsList.size() < 3) {
                    reviewsList.add(2, "");
                }

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

                writeReviewsToTextView(reviewsList);

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

    public void writeReviewsToTextView(ArrayList<String> reviews) {
        for (int i = 0; i < reviews.size(); i++) {
            String review = reviews.get(i);
            reviewsTextView.append(review);
            reviewsTextView.append("\n----------------------------------------\n");
        }
    }

    @Override
    public void onClick(String trailerKey) {

        playVideo(trailerKey);

    }

    public void playVideo(String key) {

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));

        // Check if the youtube app exists on the device
        if (intent.resolveActivity(getPackageManager()) == null) {
            // If the youtube app doesn't exist, then use the browser
            intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + key));
        }

        startActivity(intent);
    }
}
