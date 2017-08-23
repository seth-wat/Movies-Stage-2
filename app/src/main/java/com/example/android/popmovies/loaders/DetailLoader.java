package com.example.android.popmovies.loaders;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.android.popmovies.data.Movie;
import com.example.android.popmovies.utilities.JSONUtils;
import com.example.android.popmovies.utilities.NetworkUtils;

import java.net.URL;

/**
 * Used in DetailActivity to fetch reviews and videos.
 */

public class DetailLoader extends AsyncTaskLoader<Movie> {

    private Movie baseMovie;

    public DetailLoader(Context context, Movie baseMovie) {
        super(context);
        this.baseMovie = baseMovie;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Movie loadInBackground() {
        URL url = NetworkUtils.makeDetailQuery(baseMovie.getId());
        String response = NetworkUtils.getResponseFromURL(url);
        return JSONUtils.parseMovieDetails(response, baseMovie);
    }
}
