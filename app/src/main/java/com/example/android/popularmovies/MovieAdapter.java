package com.example.android.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mostafayehya on 18/07/17.
 */

//this is a comment to be able to add the file to the github repo.
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    //TODO repclace mMovieList with a Cursor
    private ArrayList<Movie> mMovieList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private final MovieAdapterOnClickHandler mClickHandler;
    private static final String MOVIE_POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185/";


    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    // parent activity will implement this method to respond to click events
    private interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    // data is passed into the constructor
    public MovieAdapter(MovieAdapterOnClickHandler clickHandler, Context context) {
        mClickHandler = clickHandler;
        mInflater = LayoutInflater.from(context);
        mMovieList = new ArrayList<>(20);
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

        // TODO i think we should extract the urls from the cursor .
        Picasso.with(myContext)
                .load(MOVIE_POSTER_BASE_URL + mMovieList
                        .get(position).imageURLRelativePath)
                .error(R.drawable.ic_play_black_48dp)
                .into(holder.mImageView);

        holder.mImageView.setVisibility(View.VISIBLE);
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mMovieList.size();
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
            Movie movieObjectToBeSentToTheMovieDetailsActivity = mMovieList.get(adapterPosition);
            mClickHandler.onClick(movieObjectToBeSentToTheMovieDetailsActivity);
        }
    }

    //Todo this should be swap cursor
    void setMoviesList(ArrayList<Movie> data) {
        mMovieList.clear();
        mMovieList.addAll(data);
        notifyDataSetChanged();
    }


}


