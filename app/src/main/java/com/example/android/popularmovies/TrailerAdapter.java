package com.example.android.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mostafayehya on 29/08/17.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private final TrailerAdapterOnClickHandler mClickHandler;
    private ArrayList<String> mTrailersList;

    private interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface TrailerAdapterOnClickHandler {
        void onClick(String urlString);
    }


    public TrailerAdapter(TrailerAdapterOnClickHandler onClickHandler, Context context) {

        mClickHandler = onClickHandler;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public TrailerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.trailer_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Context myContext = holder.mImageView.getContext();
        holder.mImageView.setImageResource(R.drawable.ic_play_black_36dp);
        holder.mTextView.setText(R.string.Word_Trailer);
        holder.mTextView.append(Integer.toString(position));
        holder.mImageView.setVisibility(View.VISIBLE);

    }

    @Override
    public int getItemCount() {
        return mTrailersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView mImageView;
        public TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.trailer_icon);
            mTextView = (TextView) itemView.findViewById(R.id.trailer_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //should extract the url from the movie object and send it to the detailedActivity
            //to fire an intent to launch youtube .
            int adapterPosition = getAdapterPosition();
            String urlStringToBeSentToDetailsActivity = mTrailersList.get(adapterPosition);
            mClickHandler.onClick(urlStringToBeSentToDetailsActivity);
        }
    }

    //called by : post execute of fetchTrailersTask
    //updates the current list when viewing a different movie
    void setTrailersList(ArrayList<String> data) {
        mTrailersList.clear();
        mTrailersList.addAll(data);
        notifyDataSetChanged();
    }
}
