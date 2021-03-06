package com.example.android.popmovies.loaders;


import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;

import com.example.android.popmovies.data.Movie;
import com.example.android.popmovies.database.FavoritesOpenHelper;
import com.example.android.popmovies.utilities.CursorUtils;
import com.example.android.popmovies.utilities.JSONUtils;
import com.example.android.popmovies.utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

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

        //Make a query to see if the movie is in the favorites database, if so pull data from db instead of network call.

        int movieKey = FavoritesOpenHelper.isFavorite(getContext().getContentResolver(), baseMovie.getTitle());
        if (movieKey != -1) {

            ArrayList<Cursor> cursorList = FavoritesOpenHelper.fetchMovieFromKey(getContext().getContentResolver(), movieKey);
            Movie fullMovie = CursorUtils.getMovieFromCursorList(cursorList);
            if (fullMovie == null) {
                return null;
            }
            fullMovie.setFavorite(true);
            return fullMovie;

        } else {
            URL url = NetworkUtils.makeDetailQuery(baseMovie.getId());
            String response = NetworkUtils.getResponseFromURL(url);
            return JSONUtils.parseMovieDetails(response, baseMovie);
        }
    }
}
