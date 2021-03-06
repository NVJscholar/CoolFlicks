package com.example.nvjscholar.coolflicks;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.nvjscholar.coolflicks.models.Config;
import com.example.nvjscholar.coolflicks.models.MovieData;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>
{
    //list of movies
    ArrayList<MovieData> movies;
    //config needed for image url
    Config config;
    //context for rendering image
    Context context;

    //initialize movie list
    public MovieAdapter(ArrayList<MovieData> movies)
    {
        this.movies = movies;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    @NonNull
    @Override
    //creates and inflates a new view
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        //get the context and create the inflater
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //create the view using the item_movie layout
        View movieView = inflater.inflate(R.layout.item_movie, viewGroup, false);
        //return a new ViewHolder
        return new ViewHolder(movieView);
    }

    //binds an inflated view to a new item
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i)
    {

        //get the movie data at the specified position
        MovieData movie = movies.get(i);
        //populate the view with the movie data
        viewHolder.tvTitle.setText(movie.getTitle());
        viewHolder.tvOverview.setText(movie.getOverview());
        //determine the current orientation
        boolean isPortrait = context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        //build url for poster image
        String imageUrl = config.getImageUrl(config.getPosterSize(), movie.getPosterPath());
        //if in portrait mode, load the poster image
        if(isPortrait)
        {
            imageUrl = config.getImageUrl(config.getPosterSize(), movie.getPosterPath());
        }
        else
        {
            //load the backdrop
            imageUrl = config.getImageUrl(config.getBackdropSize(), movie.getPosterPath());
        }

        //get the correct placeholder and imageview for the current orientation
        int placeholderId = isPortrait ? R.drawable.flicks_movie_placeholder : R.drawable.flicks_backdrop_placeholder;
        ImageView imageView = isPortrait ? viewHolder.ivPosterImage : viewHolder.ivBackdropImage;
        //load image using glide
        Glide.with(context)
                .load(imageUrl)
                .apply(new RequestOptions()
                .bitmapTransform(new RoundedCornersTransformation( 30, 0 ))
                .placeholder(R.drawable.flicks_movie_placeholder)
                .error(placeholderId))
                .into(imageView);


    }

    //returns the total number of items in the list
    @Override
    public int getItemCount()
    {
        return movies.size();
    }

    //create the viewholder as a static inner class
    public static class ViewHolder extends RecyclerView.ViewHolder
    {

        //track view objects
        ImageView ivPosterImage;
        ImageView ivBackdropImage;
        TextView tvTitle;
        TextView tvOverview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //lookup view objects by id
            ivPosterImage = (ImageView) itemView.findViewById(R.id.ivPosterImage);
            ivBackdropImage = (ImageView) itemView.findViewById(R.id.ivBackdropImage);
            tvOverview = (TextView) itemView.findViewById(R.id.tvOverview);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        }
    }
}
