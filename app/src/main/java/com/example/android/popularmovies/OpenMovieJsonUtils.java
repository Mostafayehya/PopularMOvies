package com.example.android.popularmovies;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mostafayehya on 21/07/17.
 */

public class OpenMovieJsonUtils {

    static final String TITLE = "title";
    static final String POSTER_PATH = "poster_path";
    static final String RATING = "vote_average";
    static final String OVERVIEW = "overview";
    static final String RELEASE_DATE = "release_date";


    public static ArrayList<Movie> getArrayListOfMoviesFromJson(Context context, String jsonMovieResponse) throws JSONException {


        JSONObject jsonReponse = new JSONObject(jsonMovieResponse);
        JSONArray moviesArray = jsonReponse.getJSONArray("results");

        ArrayList<Movie> parsedMovies = new ArrayList<>(20);

        for (int i = 0; i < moviesArray.length(); i++) {

            JSONObject currentObject = moviesArray.getJSONObject(i);
            Movie currentMovie = new Movie();
            currentMovie.name = currentObject.getString(TITLE);
            currentMovie.imageURLRelativePath = currentObject.getString(POSTER_PATH);
            currentMovie.rating = currentObject.getString(RATING);
            currentMovie.summary = currentObject.getString(OVERVIEW);
            currentMovie.year = currentObject.getString(RELEASE_DATE);

            parsedMovies.add(currentMovie);


        }

        return parsedMovies;
    }
}
