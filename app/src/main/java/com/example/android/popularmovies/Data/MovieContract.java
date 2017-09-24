package com.example.android.popularmovies.Data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by mostafayehya on 13/09/17.
 */

public final class MovieContract {

    public static final String CONTENT_AUTHORITY = "com.example.android.popularmovies";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_FAVOURITE_MOVIES = "favouriteMovies";


    public static final class FavouriteMovies implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVOURITE_MOVIES).build();



        // Defining schema
        public static final String TABLE_NAME = "favouriteMovies";

        public static final String COLUMN_NAME = "movieName";
        public static final String COLUMN_IMAGE_URL = "movieImageUrl";
        public static final String COLUMN_DATE = "movieDate";
        public static final String COLUMN_RATE = "movieRate";
        public static final String COLUMN_DESCRIPTION = "movieDescription";
        public static final String COLUMN_TRAILERS = "movieTrailers";
        public static final String COLUMN_REVIEWS = "movieReviews";
        public static final String ID = "movieID";
        public static final String IS_IN_DB = "movieInDB";



        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }


}
