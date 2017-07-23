package com.example.android.popularmovies;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mostafayehya on 21/07/17.
 */

public class OpenMovieJsonUtils {

    //"results"
//        "vote_count": 623,
//                "id": 324852,
//                "video": false,
//                "vote_average": 6.2,
//                "title": "Despicable Me 3",
//                "popularity": 307.527553,
//                "poster_path": "\/5qcUGqWoWhEsoQwNUrtf3y3fcWn.jpg",
//                "original_language": "en",
//                "original_title": "Despicable Me 3",
//                "genre_ids": [
//        878,
//                12,
//                16,
//                35,
//                10751
//      ],
//        "backdrop_path": "\/puV2PFq42VQPItaygizgag8jrXa.jpg",
//                "adult": false,
//                "overview": "Gru and his wife Lucy must stop former '80s child star Balthazar Bratt from achieving world domination.",
//                "release_date": "2017-06-29"

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
