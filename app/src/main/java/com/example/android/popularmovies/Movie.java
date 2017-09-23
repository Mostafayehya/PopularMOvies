package com.example.android.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mostafayehya on 21/07/17.
 */


public class Movie implements Parcelable {
    final String MOVIE_POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185/";
    String name;
    String rating;
    String summary;
    String year;
    String imageURLRelativePath;
    int id;
    String jsonTrailers;
    String jsonReviews;
    public int isStoredInDB;

    public Movie() {
        this.name = "";
        this.rating = "";
        this.summary = "";
        this.year = "";
        this.imageURLRelativePath = "";
        this.id = 0;
        this.isStoredInDB = 0;
    }

    public Movie(String name, String rating, String summary, String year, String image, int id, String jsonTrailers, String jsonReviews, int isStoredInDB) {
        this.name = name;
        this.rating = rating;
        this.summary = summary;
        this.year = year;

        this.imageURLRelativePath = image;
        this.id = id;
        this.jsonTrailers = jsonTrailers;
        this.jsonReviews = jsonReviews;
        this.isStoredInDB = isStoredInDB;
    }

    private Movie(Parcel in) {
        name = in.readString();
        rating = in.readString();
        summary = in.readString();
        year = in.readString();
        imageURLRelativePath = in.readString();
        jsonReviews = in.readString();
        jsonTrailers = in.readString();
        id = in.readInt();
        isStoredInDB = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(rating);
        parcel.writeString(summary);
        parcel.writeString(year);
        parcel.writeString(imageURLRelativePath);
        parcel.writeString(jsonReviews);
        parcel.writeString(jsonTrailers);
        parcel.writeInt(id);
        parcel.writeInt(isStoredInDB);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }

    };
}