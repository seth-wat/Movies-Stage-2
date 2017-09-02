package com.example.android.popmovies.events;

import android.app.Activity;
import android.content.AsyncTaskLoader;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Loader;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.example.android.popmovies.R;
import com.example.android.popmovies.data.Movie;
import com.example.android.popmovies.data.Review;
import com.example.android.popmovies.data.Video;
import com.example.android.popmovies.database.FavoritesContract;
import com.example.android.popmovies.database.FavoritesOpenHelper;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import android.app.LoaderManager;

/**
 * Click handler for the favorites fab. Adds / removes from favorites db.
 */


public class FavoriteClickHandler implements View.OnClickListener {
    private Movie movie;
    private LoaderManager.LoaderCallbacks<Object> callbacks;
    public static final int ADD_LOADER_ID = 107;
    public static final int REMOVE_LOADER_iD = 109;

    public FavoriteClickHandler(Movie movie) {
        this.movie = movie;
    }

    @Override
    public void onClick(final View v) {
        if (movie.getFavorite()) {
            final Activity callingActivity = (Activity) v.getContext();
            callbacks = new LoaderManager.LoaderCallbacks<Object>() {

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
            final ContentValues movieContentValues = new ContentValues();
            movieContentValues.put(FavoritesContract.MovieEntry.COLUMN_TITLE, movie.getTitle());
            movieContentValues.put(FavoritesContract.MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
            movieContentValues.put(FavoritesContract.MovieEntry.COLUMN_USER_RATING, movie.getUserRating());
            movieContentValues.put(FavoritesContract.MovieEntry.COLUMN_SYNOPSIS, movie.getPlotSynopsis());

            Target target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    byte[] imageInByte = outputStream.toByteArray();
                    movie.setPosterByteImage(imageInByte);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                }
            };


            //Somehow between PosterAdapter and here a duplicate String is added on to the end of
            //the thumbnail path. How? I don't know, the method setThumbnailPath didn't even exist
            //until I created it for this hack. It was only settable through the constructor.
            // The string value was perfectly valid when it was bound in PosterActivity retrieved
            // from the same Movie reference that was passed into this Activity
            // This code is important for adding the image to the favorites db.
            StringBuilder whatAHack = new StringBuilder(movie.getThumbnailPath());
            whatAHack = whatAHack.delete(0, whatAHack.indexOf("2htt") + 1);
            movie.setThumbnailPath(whatAHack.toString());
            //end hack


            Picasso.with(v.getContext()).load(movie.getThumbnailPath()).into(target);

            movieContentValues.put(FavoritesContract.MovieEntry.COLUMN_IMAGE, movie.getPosterByteImage());

            target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    byte[] imageInByte = outputStream.toByteArray();
                    movie.setDetailByteImage(imageInByte);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                }
            };
            Picasso.with(v.getContext()).load(movie.getBackDropPath()).into(target);
            movieContentValues.put(FavoritesContract.MovieEntry.COLUMN_DETAIL_IMAGE, movie.getDetailByteImage());


            final Activity callingActivity = (Activity) v.getContext();

            callbacks = new LoaderManager.LoaderCallbacks<Object>() {


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
                    if (callingActivity.getLoaderManager().getLoader(REMOVE_LOADER_iD) != null) {
                        callingActivity.getLoaderManager().destroyLoader(REMOVE_LOADER_iD);
                    }


                }

                @Override
                public void onLoaderReset(Loader<Object> loader) {

                }
            };

            callingActivity.getLoaderManager().initLoader(ADD_LOADER_ID, null, callbacks);

        }


    }


}
