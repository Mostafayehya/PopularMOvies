package com.example.android.popularmovies;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mostafayehya on 21/07/17.
 */

public class OpenMovieJsonUtils {

    static final String TITLE = "title";
    static final String POSTER_PATH = "poster_path";
    static final String RATING = "vote_average";
    static final String OVERVIEW = "overview";
    static final String RELEASE_DATE = "release_date";


    public static Movie[] getArrayOfMoviesFromJson(Context context, String jsonMovieResponse) throws JSONException {


        JSONObject jsonReponse = new JSONObject(jsonMovieResponse);
        JSONArray moviesArray = jsonReponse.getJSONArray("results");

        Movie[] parsedMovies = new Movie[moviesArray.length()];

        for (int i = 0; i < moviesArray.length(); i++) {

            JSONObject currentObject = moviesArray.getJSONObject(i);
            parsedMovies[i] = new Movie();
            parsedMovies[i].name = currentObject.getString(TITLE);
            parsedMovies[i].imageURLRelativePath = currentObject.getString(POSTER_PATH);
            parsedMovies[i].rating = currentObject.getString(RATING);
            parsedMovies[i].summary = currentObject.getString(OVERVIEW);
            parsedMovies[i].year = currentObject.getString(RELEASE_DATE);

        }

        return parsedMovies;
    }
}
