package com.example.android.popularmovies;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

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

        RecyclerViewAdapter imageAdapter = new RecyclerViewAdapter(this, imagesDB);

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
}
