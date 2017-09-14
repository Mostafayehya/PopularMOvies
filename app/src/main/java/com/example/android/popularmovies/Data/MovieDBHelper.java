package com.example.android.popularmovies.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mostafayehya on 14/09/17.
 */

public class MovieDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "favouriteMovies.db";

    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sqlCreationStatement = "CREATE TABLE " + MovieContract.FavouriteMovies.TABLE_NAME + " (" +

                MovieContract.FavouriteMovies._ID + " INTEGER PRIMARY KEY, " +
                MovieContract.FavouriteMovies.COLUMN_NAME + " TEXT NOT NULL, " +
                MovieContract.FavouriteMovies.COLUMN_IMAGE_URL + " TEXT NOT NULL, " +
                MovieContract.FavouriteMovies.COLUMN_DATE + " TEXT NOT NULL, " +
                MovieContract.FavouriteMovies.COLUMN_RATE + " TEXT NOT NULL," +
                MovieContract.FavouriteMovies.COLUMN_DESCRIBTION + " TEXT NOT NULL, " +
                MovieContract.FavouriteMovies.COLUMN_TRAILER_1 + " TEXT, " +
                MovieContract.FavouriteMovies.COLUMN_TRAILER_2 + " TEXT, " +
                MovieContract.FavouriteMovies.COLUMN_TRAILER_3 + " TEXT, " +
                MovieContract.FavouriteMovies.COLUMN_REVIEW_1 + " TEXT, " +
                MovieContract.FavouriteMovies.COLUMN_REVIEW_2 + " TEXT, " +
                MovieContract.FavouriteMovies.COLUMN_REVIEW_3 + " TEXT " +
                ");";

        db.execSQL(sqlCreationStatement);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ MovieContract.FavouriteMovies.TABLE_NAME);
        onCreate(db);

    }
}
