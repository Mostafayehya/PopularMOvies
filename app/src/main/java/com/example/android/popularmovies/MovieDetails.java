package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetails extends AppCompatActivity {
    private TextView movieName;
    private TextView releaseDate;
    private TextView duration;
    private TextView rating;
    private TextView summary;
    private ImageView thumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Bundle recievedBundle;
        Movie thisMovie = new Movie();
        movieName = (TextView) findViewById(R.id.movie_name);
        releaseDate = (TextView) findViewById(R.id.release_date);
        rating = (TextView) findViewById(R.id.rating);
        summary = (TextView) findViewById(R.id.movie_summary);
        thumbnail = (ImageView) findViewById(R.id.movie_thumbnail);

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
}
