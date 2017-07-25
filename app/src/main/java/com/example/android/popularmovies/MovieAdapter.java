package com.example.android.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by mostafayehya on 18/07/17.
 */

//this is a comment to be able to add the file to the github repo.
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private Movie[] mMovieList = new Movie[20];
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private final MovieAdapterOnClickHandler mClickHandler;
    private static final String MOVIE_POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185/";


    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    // data is passed into the constructor
    public MovieAdapter(MovieAdapterOnClickHandler clickHandler, Context context) {
        mClickHandler = clickHandler;
        mInflater = LayoutInflater.from(context);
        for (int i = 0; i < mMovieList.length; i++) {
            mMovieList[i] = new Movie();
        }
    }

    // inflates the cell layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.recycler_view_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MovieAdapter.ViewHolder holder, int position) {
        Context myContext = holder.mImageView.getContext();
        //MOVIE_POSTER_BASE_URL+mMovieList[position].imageURLRelativePath this is the complete movie image url .
        Picasso.with(myContext).load(MOVIE_POSTER_BASE_URL + mMovieList[position].imageURLRelativePath).into(holder.mImageView);
        holder.mImageView.setVisibility(View.VISIBLE);
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mMovieList.length;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.rv_image_view_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Movie movieObjectToBeSentToTheMovieDetailsActivity = mMovieList[adapterPosition];
            mClickHandler.onClick(movieObjectToBeSentToTheMovieDetailsActivity);
        }
    }

   void setMoviesList(Movie[] data) {
        System.arraycopy(data, 0, mMovieList, 0, data.length);
        notifyDataSetChanged();
    }

    // parent activity will implement this method to respond to click events
    private interface ItemClickListener {
        void onItemClick(View view, int position);
    }


}


