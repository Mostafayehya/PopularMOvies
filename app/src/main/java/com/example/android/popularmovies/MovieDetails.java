package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

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

        movieName = (TextView) findViewById(R.id.movie_name);
        releaseDate = (TextView) findViewById(R.id.release_date);
        duration = (TextView) findViewById(R.id.duration);
        rating = (TextView) findViewById(R.id.rating);
        summary = (TextView) findViewById(R.id.movie_summary);
        thumbnail = (ImageView) findViewById(R.id.movie_thumbnail);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra("resourceId")) {
                thumbnail.setImageResource(intentThatStartedThisActivity.getIntExtra("resourceId", 0));
            }
        }
    }
}
