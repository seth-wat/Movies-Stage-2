package com.example.android.popmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popmovies.data.Movie;
import com.example.android.popmovies.loaders.MovieLoader;
import com.example.android.popmovies.utilities.JSONUtils;
import com.example.android.popmovies.utilities.NetworkUtils;

import org.parceler.Parcels;

import java.net.URL;
import java.util.ArrayList;

public class PosterActivity extends AppCompatActivity implements PosterAdapter.ItemClickListener, LoaderManager.LoaderCallbacks<ArrayList<Movie>> {
    public static final String LOG_TAG = PosterActivity.class.getSimpleName();
    RecyclerView posters;
    ProgressBar mProgressBar;
    String fetchUrl = NetworkUtils.MOST_POPULAR_QUERY;
    TextView mHeaderView;
    TextView mErrorView;
    Toast errorToast;
    ArrayList<Movie> movieData;
    LoaderManager loaderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster);

        mProgressBar = (ProgressBar) findViewById(R.id.loading_progress_bar);
        mHeaderView = (TextView) findViewById(R.id.header_text_view);
        mErrorView = (TextView) findViewById(R.id.error_text_view);
        errorToast = Toast.makeText(this, R.string.error_msg, Toast.LENGTH_LONG);
        posters = (RecyclerView) findViewById(R.id.posters_recycler_view);

        posters.setLayoutManager(new GridLayoutManager(this, 2));
        loaderManager = getSupportLoaderManager();

        if (NetworkUtils.hasInternet(this)) {
            loaderManager.initLoader(MovieLoader.MOST_POPULAR_LOADER, null, this);
            mHeaderView.setText(R.string.most_popular_header);
        } else {
            mErrorView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        if (movieData != null) {
            Intent intent = new Intent(this, DetailActivity.class);
            Parcelable movieParcel = Parcels.wrap(movieData.get(position));
            intent.putExtra("movieParcel", movieParcel);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.order, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.top_rated_item:
                fetchUrl = NetworkUtils.HIGHEST_RATED_QUERY;
                if (NetworkUtils.hasInternet(this)) {
                    mHeaderView.setText(R.string.top_rated_header);
                    loaderManager.initLoader(MovieLoader.TOP_RATED_LOADER, null, this);
                } else {
                    errorToast.show();
                    mProgressBar.setVisibility(View.INVISIBLE);
                }
                return true;
            case R.id.most_popular_item:
                fetchUrl = NetworkUtils.MOST_POPULAR_QUERY;
                if (NetworkUtils.hasInternet(this)) {
                    mHeaderView.setText(R.string.most_popular_header);
                    loaderManager.initLoader(MovieLoader.MOST_POPULAR_LOADER, null, this);
                } else {
                    errorToast.show();
                    mProgressBar.setVisibility(View.INVISIBLE);
                }
                return true;
            case R.id.favorites_item:
                mHeaderView.setText(R.string.favorited_header);
                loaderManager.initLoader(MovieLoader.FAVORITE_LOADER, null, this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {
        return new MovieLoader(this, errorToast, mErrorView, id);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> movies) {
        mProgressBar.setVisibility(View.INVISIBLE);
        if (movies == null || movies.isEmpty()) {
            if (loader.getId() == MovieLoader.FAVORITE_LOADER) {
                posters.setVisibility(View.INVISIBLE);
                mErrorView.setText(R.string.no_favorite_message);
            }
            mErrorView.setVisibility(View.VISIBLE);
            return;
        } else {
            posters.setVisibility(View.VISIBLE);
            mErrorView.setVisibility(View.INVISIBLE);
        }
        PosterAdapter mAdapter = new PosterAdapter(PosterActivity.this, movies);
        mAdapter.setOnClickListener(PosterActivity.this);
        posters.setAdapter(mAdapter);
        movieData = movies;
    }


    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        errorToast.cancel();
    }
}