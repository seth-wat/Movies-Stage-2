package com.example.android.popmovies.events;

import android.app.Activity;
import android.content.AsyncTaskLoader;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Loader;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.android.popmovies.R;
import com.example.android.popmovies.data.Movie;
import com.example.android.popmovies.data.Review;
import com.example.android.popmovies.data.Video;
import com.example.android.popmovies.database.FavoritesContract;
import com.example.android.popmovies.database.FavoritesOpenHelper;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import android.app.LoaderManager;
import android.widget.Toast;

/**
 * Click handler for the favorites fab. Adds / removes from favorites db.
 */



public class FavoriteClickHandler implements View.OnClickListener {
    private Movie movie;
    private ImageView mImage;
    private LoaderManager.LoaderCallbacks<Object> callbacks;
    public static final int ADD_LOADER_ID = 107;
    public static final int REMOVE_LOADER_iD = 109;

    public FavoriteClickHandler(Movie movie, ImageView imageView) {
        this.movie = movie;
        mImage = imageView;
    }

    @Override
    public void onClick(final View v) {
        if (movie.getFavorite()) {
            final Activity callingActivity = (Activity) v.getContext();
            callbacks =  new LoaderManager.LoaderCallbacks<Object>() {

                @Override
                public Loader<Object> onCreateLoader(final int id, Bundle args) {

                    return new AsyncTaskLoader<Object>(v.getContext()) {
                        @Override
                        protected void onStartLoading() {
                            forceLoad();
                        }
                        @Override
                        public Boolean loadInBackground() {
                            ContentResolver contentResolver = getContext().getContentResolver();
                            int databaseMovieId = FavoritesOpenHelper.isFavorite(contentResolver, movie.getTitle());
                            if (databaseMovieId != -1) {
                                int numRowsDeleted = contentResolver.delete(FavoritesContract.DELETE_URI, FavoritesContract.MovieEntry._ID + "=?", new String[]{Integer.toString(databaseMovieId)});
                                Log.e("CSDFSDFSD", "====================================" + numRowsDeleted);
                                return true;
                            }
                            return false;
                        }
                    };
                }

                @Override
                public void onLoadFinished(Loader<Object> loader, Object data) {
                    if (!(Boolean) data) {
                        return;
                    }
                    FloatingActionButton fab = (FloatingActionButton) v;
                    fab.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    movie.setFavorite(false);
                    Toast.makeText(v.getContext(), "Movie successfully removed from favorites database", Toast.LENGTH_SHORT).show();
                    if (callingActivity.getLoaderManager().getLoader(ADD_LOADER_ID) != null) {
                        callingActivity.getLoaderManager().destroyLoader(ADD_LOADER_ID);
                    }


                }

                @Override
                public void onLoaderReset(Loader<Object> loader) {

                }
            };

            callingActivity.getLoaderManager().initLoader(109, null, callbacks);


        } else {
            if (mImage == null) return;
            final ContentValues movieContentValues = new ContentValues();
            movieContentValues.put(FavoritesContract.MovieEntry.COLUMN_TITLE, movie.getTitle());
            movieContentValues.put(FavoritesContract.MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
            movieContentValues.put(FavoritesContract.MovieEntry.COLUMN_USER_RATING, movie.getUserRating());
            movieContentValues.put(FavoritesContract.MovieEntry.COLUMN_SYNOPSIS, movie.getPlotSynopsis());

            Bitmap bitmap = ((BitmapDrawable) mImage.getDrawable()).getBitmap();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            byte[] imageInByte = outputStream.toByteArray();

            movieContentValues.put(FavoritesContract.MovieEntry.COLUMN_IMAGE, imageInByte);

            final Activity host = (Activity) v.getContext();

            callbacks =  new LoaderManager.LoaderCallbacks<Object>() {


                @Override
                public Loader<Object> onCreateLoader(final int id, Bundle args) {

                    return new AsyncTaskLoader<Object>(v.getContext()) {
                        @Override
                        protected void onStartLoading() {
                            forceLoad();
                        }
                        @Override
                        public Boolean loadInBackground() {
                            ContentResolver contentResolver = getContext().getContentResolver();
                            contentResolver.insert(FavoritesContract.MovieEntry.ACCESS_URI, movieContentValues);
                            int databaseMovieId = FavoritesOpenHelper.isFavorite(contentResolver, movie.getTitle());
                            ArrayList<Review> reviews = movie.getReviews();
                            ContentValues reviewContentValues = new ContentValues();
                            for (Review review : reviews) {

                                reviewContentValues.put(FavoritesContract.ReviewEntry.COLUMN_ID_MOVIE, databaseMovieId);
                                reviewContentValues.put(FavoritesContract.ReviewEntry.COLUMN_AUTHOR, review.getAuthor());
                                reviewContentValues.put(FavoritesContract.ReviewEntry.COLUMN_CONTENT, review.getContent());

                                contentResolver.insert(FavoritesContract.ReviewEntry.ACCESS_URI, reviewContentValues);
                            }
                            ArrayList<Video> videos = movie.getVideos();
                            ContentValues videoContentValues = new ContentValues();
                            for (Video video : videos) {

                                videoContentValues.put(FavoritesContract.VideoEntry.COLUMN_ID_MOVIE, databaseMovieId);
                                videoContentValues.put(FavoritesContract.VideoEntry.COLUMN_NAME, video.getName());
                                videoContentValues.put(FavoritesContract.VideoEntry.COLUMN_KEY, video.getKey());

                                contentResolver.insert(FavoritesContract.VideoEntry.ACCESS_URI, videoContentValues);
                            }
                            return true;
                        }
                    };
                }


                @Override
                public void onLoadFinished(Loader<Object> loader, Object data) {
                    if (!(Boolean) data) {
                        return;
                    }
                    FloatingActionButton fab = (FloatingActionButton) v;
                    fab.setImageResource(R.drawable.ic_favorited_black_24dp);
                    movie.setFavorite(true);
                    Toast.makeText(v.getContext(), "Inserted into favorites database", Toast.LENGTH_SHORT).show();
                    if (host.getLoaderManager().getLoader(REMOVE_LOADER_iD) != null) {
                        host.getLoaderManager().destroyLoader(REMOVE_LOADER_iD);
                    }


                }

                @Override
                public void onLoaderReset(Loader<Object> loader) {

                }
            };

            host.getLoaderManager().initLoader(ADD_LOADER_ID, null, callbacks);

            //insert on the background thread, query for id, then insert the other two sections.

            }


        }



}
