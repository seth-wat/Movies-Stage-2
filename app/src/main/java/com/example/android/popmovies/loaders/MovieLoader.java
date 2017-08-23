package com.example.android.popmovies.loaders;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popmovies.R;
import com.example.android.popmovies.data.Movie;
import com.example.android.popmovies.utilities.JSONUtils;
import com.example.android.popmovies.utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

/**
 * Loader to be used in PosterActivity
 */

public class MovieLoader extends AsyncTaskLoader<ArrayList<Movie>> {

    public static final int MOST_POPULAR_LOADER = 22;
    public static final int TOP_RATED_LOADER = 23;
    public static final int FAVORITE_LOADER = 24;
    private static final String LOG_TAG = "MovieLoader";

    private Toast errorToast;
    private TextView mErrorView;
    private Context mContext;
    private int loaderId;

    public MovieLoader(Context context, Toast errorToast, TextView mErrorView, int loaderId) {
        super(context);
        this.errorToast = errorToast;
        this.mErrorView = mErrorView;
        this.mContext = context;
        this.loaderId = loaderId;
    }

    @Override
    protected void onStartLoading() {
            forceLoad();
    }

    @Override
    public ArrayList<Movie> loadInBackground() {
        URL url = null;
        switch (loaderId) {

            case MOST_POPULAR_LOADER:
                errorToast.cancel();
                url = NetworkUtils.urlFromString(NetworkUtils.MOST_POPULAR_QUERY);
                if (url != null) {
                    String responseString = NetworkUtils.getResponseFromURL(url);
                    Log.v(LOG_TAG, responseString);
                    return JSONUtils.parseMovies(responseString);
                }
                break;

            case TOP_RATED_LOADER:
                errorToast.cancel();
                url = NetworkUtils.urlFromString(NetworkUtils.HIGHEST_RATED_QUERY);
                if (url != null) {
                    String responseString = NetworkUtils.getResponseFromURL(url);
                    Log.v(LOG_TAG, responseString);
                    return JSONUtils.parseMovies(responseString);
                }
                break;

            case FAVORITE_LOADER:
                break;
            default:
                return null;
        }
        return null;
    }
}
