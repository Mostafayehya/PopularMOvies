package com.example.android.popularmovies;

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
        GridLayoutManager gridManager= new GridLayoutManager(this,2);



        imageRecyclerView.setAdapter(imageAdapter);

        imageRecyclerView.setLayoutManager(gridManager);

    }
}
