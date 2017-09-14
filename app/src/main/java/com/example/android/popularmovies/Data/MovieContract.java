package com.example.android.popularmovies.Data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by mostafayehya on 13/09/17.
 */

public class MovieContract {

    public static final String CONTENT_AUTHORITY = "com.example.android.favouriteprovider";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_FAVOURITE_MOVIES = "favourite_movies";


    public static final class FavouriteMovies implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVOURITE_MOVIES).build();

        // Defining schema
        public static final String TABLE_NAME = "favouriteMovies";

        public static final String COLUMN_NAME = "movieName";
        public static final String COLUMN_IMAGE = "movieImage";
        public static final String COLUMN_DATE = "movieDate";
        public static final String COLUMN_RATE = "movieRate";
        public static final String COLUMN_DESCRIBTION = "movieDescribtion";
        public static final String COLUMN_TRAILER_1 = "movieTrailer_1";
        public static final String COLUMN_TRAILER_2 = "movieTrailer_2";
        public static final String COLUMN_TRAILER_3 = "movieTrailer_3";
        public static final String COLUMN_REVIEW_1 = "movieReview_1";
        public static final String COLUMN_REVIEW_2 = "movieReview_2";
        public static final String COLUMN_REVIEW_3 = "movieReview_3";

        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }


}
