package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class MovieDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        ImageView thumbnail = (ImageView) findViewById(R.id.movie_thumbnail);

        Intent intentThatStartedThisActivity = getIntent();

        if(intentThatStartedThisActivity !=null){
            if(intentThatStartedThisActivity.hasExtra("resourceId")){
                thumbnail.setImageResource(intentThatStartedThisActivity.getIntExtra("resourceId",0));
            }
        }
    }
}
