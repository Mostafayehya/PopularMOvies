package com.example.android.popularmovies.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mostafayehya on 14/09/17.
 */

public class MovieDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "favouriteMovies.db";

    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sqlCreationStatement = "CREATE TABLE " + MovieContract.FavouriteMovies.TABLE_NAME + " (" +

                MovieContract.FavouriteMovies._ID + " INTEGER PRIMARY KEY, " +
                MovieContract.FavouriteMovies.COLUMN_NAME + " TEXT NOT NULL, " +       //1
                MovieContract.FavouriteMovies.COLUMN_IMAGE_URL + " TEXT NOT NULL, " +  //2
                MovieContract.FavouriteMovies.COLUMN_DATE + " TEXT NOT NULL, " +       //3
                MovieContract.FavouriteMovies.COLUMN_RATE + " TEXT NOT NULL," +        //4
                MovieContract.FavouriteMovies.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +//5
                MovieContract.FavouriteMovies.COLUMN_TRAILERS + " TEXT, " +            //6
                MovieContract.FavouriteMovies.COLUMN_REVIEWS + " TEXT, " +             //7
                MovieContract.FavouriteMovies.ID + " INTEGER " +                       //8
                ");";

        db.execSQL(sqlCreationStatement);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.FavouriteMovies.TABLE_NAME);
        onCreate(db);

    }
}
