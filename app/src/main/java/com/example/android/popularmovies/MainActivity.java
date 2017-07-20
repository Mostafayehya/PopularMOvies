package com.example.android.popularmovies;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    //this is a comment to be able to add the file to the git

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int[] imagesDB = {R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4,
                R.drawable.img5,
                R.drawable.img6,
                R.drawable.img7,
                R.drawable.img8

        };

        MovieAdapter imageAdapter = new MovieAdapter(this,this);

        imageAdapter.setMoviesImages(imagesDB);

        RecyclerView imageRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_images);
        imageRecyclerView.setHasFixedSize(true);

        //this part to handle the orientation of the device and adjusting the GridLayoutManger accordingly
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            imageRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            imageRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }
        imageRecyclerView.setAdapter(imageAdapter);


    }

    @Override
    public void onClick(int imgResourceID) {

        Intent intentToStartMovieDetailsActivity = new Intent (this,MovieDetails.class);
        intentToStartMovieDetailsActivity.putExtra("resourceId",imgResourceID);
        startActivity(intentToStartMovieDetailsActivity);
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

        if (id == R.id.action_sortby_popularity) {
            //excute task that takes the url requesting the most popularity movies
            return true;
        }

        if (id == R.id.action_sortby_rating) {
            //excute task that request top rated movies
        }

        return super.onOptionsItemSelected(item);
    }
}
