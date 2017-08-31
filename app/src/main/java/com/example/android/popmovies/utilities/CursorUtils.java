package com.example.android.popmovies.utilities;

import android.database.Cursor;

import com.example.android.popmovies.data.Movie;
import com.example.android.popmovies.data.Review;
import com.example.android.popmovies.data.Video;
import com.example.android.popmovies.database.FavoritesContract;

import java.util.ArrayList;

/**
 *
 */

public class CursorUtils {
    //movie

    public static Movie getMovieFromCursorList(ArrayList<Cursor> list) {

        Cursor movieEntry = list.get(0);
        Cursor reviewEntry = list.get(1);
        Cursor videoEntry = list.get(2);

        //base movie
        Movie movie = null;
        if (movieEntry.getCount() > 0) {
            movieEntry.moveToFirst();
            String title = movieEntry.getString(movieEntry.getColumnIndex(FavoritesContract.MovieEntry.COLUMN_TITLE));
            byte[] imageByte = movieEntry.getBlob(movieEntry.getColumnIndex(FavoritesContract.MovieEntry.COLUMN_IMAGE));
            String plotSynopsis = movieEntry.getString(movieEntry.getColumnIndex(FavoritesContract.MovieEntry.COLUMN_RELEASE_DATE));
            double userRating = movieEntry.getDouble(movieEntry.getColumnIndex(FavoritesContract.MovieEntry.COLUMN_USER_RATING));
            String releaseDate = movieEntry.getString(movieEntry.getColumnIndex(FavoritesContract.MovieEntry.COLUMN_RELEASE_DATE));
            int id = 0;

            movie = new Movie(title, null, plotSynopsis, userRating, releaseDate, id);
            movie.setByteImage(imageByte);
        }

        ArrayList<Review> reviews = new ArrayList<Review>();
        //reviews
        if (reviewEntry.getCount() > 0) {
            reviewEntry.moveToFirst();
            String author = reviewEntry.getString(reviewEntry.getColumnIndex(FavoritesContract.ReviewEntry.COLUMN_AUTHOR));
            String content = reviewEntry.getString(reviewEntry.getColumnIndex(FavoritesContract.ReviewEntry.COLUMN_CONTENT));

            reviews.add(new Review(author, content));

            while (reviewEntry.moveToNext()) {
                author = reviewEntry.getString(reviewEntry.getColumnIndex(FavoritesContract.ReviewEntry.COLUMN_AUTHOR));
                content = reviewEntry.getString(reviewEntry.getColumnIndex(FavoritesContract.ReviewEntry.COLUMN_CONTENT));

                reviews.add(new Review(author, content));

            }
        }

        //videos
        ArrayList<Video> videos = new ArrayList<Video>();
        if (videoEntry.getCount() > 0) {
            videoEntry.moveToFirst();
            String key = videoEntry.getString(videoEntry.getColumnIndex(FavoritesContract.VideoEntry.COLUMN_KEY));
            String name = videoEntry.getString(videoEntry.getColumnIndex(FavoritesContract.VideoEntry.COLUMN_NAME));


            videos.add(new Video(key, name));
            while (videoEntry.moveToNext()) {
                key = videoEntry.getString(videoEntry.getColumnIndex(FavoritesContract.VideoEntry.COLUMN_KEY));
                name = videoEntry.getString(videoEntry.getColumnIndex(FavoritesContract.VideoEntry.COLUMN_NAME));

                videos.add(new Video(key, name));
            }
        }

        if (movie != null) {
            movie.setReviews(reviews);
            movie.setVideos(videos);
        }

        return movie;
    }

    public static ArrayList<Movie> getAllMoviesFromCursor(Cursor movieEntry) {
        ArrayList<Movie> movies = new ArrayList<Movie>();
        if (movieEntry != null && movieEntry.getCount() > 0) {
            movieEntry.moveToFirst();

            String title = movieEntry.getString(movieEntry.getColumnIndex(FavoritesContract.MovieEntry.COLUMN_TITLE));
            byte[] imageByte = movieEntry.getBlob(movieEntry.getColumnIndex(FavoritesContract.MovieEntry.COLUMN_IMAGE));
            String plotSynopsis = movieEntry.getString(movieEntry.getColumnIndex(FavoritesContract.MovieEntry.COLUMN_RELEASE_DATE));
            double userRating = movieEntry.getDouble(movieEntry.getColumnIndex(FavoritesContract.MovieEntry.COLUMN_USER_RATING));
            String releaseDate = movieEntry.getString(movieEntry.getColumnIndex(FavoritesContract.MovieEntry.COLUMN_RELEASE_DATE));
            int id = 0;

            Movie movie = new Movie(title, null, plotSynopsis, userRating, releaseDate, id);
            movie.setByteImage(imageByte);
            movies.add(movie);

            while (movieEntry.moveToNext()) {
                title = movieEntry.getString(movieEntry.getColumnIndex(FavoritesContract.MovieEntry.COLUMN_TITLE));
                imageByte = movieEntry.getBlob(movieEntry.getColumnIndex(FavoritesContract.MovieEntry.COLUMN_IMAGE));
                plotSynopsis = movieEntry.getString(movieEntry.getColumnIndex(FavoritesContract.MovieEntry.COLUMN_RELEASE_DATE));
                userRating = movieEntry.getDouble(movieEntry.getColumnIndex(FavoritesContract.MovieEntry.COLUMN_USER_RATING));
                releaseDate = movieEntry.getString(movieEntry.getColumnIndex(FavoritesContract.MovieEntry.COLUMN_RELEASE_DATE));
                id = 0;

                movie = new Movie(title, null, plotSynopsis, userRating, releaseDate, id);
                movie.setByteImage(imageByte);
                movies.add(movie);
            }
            movieEntry.close();
        }
        return movies;
    }

}
