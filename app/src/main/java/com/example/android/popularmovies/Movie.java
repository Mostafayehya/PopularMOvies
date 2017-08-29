package com.example.android.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mostafayehya on 21/07/17.
 */


public class Movie implements Parcelable {
    String name;
    String rating;
    String summary;
    String year;
    String imageURLRelativePath;
    final String MOVIE_POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185/";
    int id;

    public Movie() {
        this.name = "";
        this.rating = "";
        this.summary = "";
        this.year = "";
        this.imageURLRelativePath = "";
        this.id =0;
    }

    public Movie(String name, String rating, String summary, String year, String image,int id) {
        this.name = name;
        this.rating = rating;
        this.summary = summary;
        this.year = year;

        this.imageURLRelativePath = image;
        this.id =id;
    }

    private Movie(Parcel in) {
        name = in.readString();
        rating = in.readString();
        summary = in.readString();
        year = in.readString();
        imageURLRelativePath = in.readString();
        id = in.readInt();
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
        parcel.writeInt(id);
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