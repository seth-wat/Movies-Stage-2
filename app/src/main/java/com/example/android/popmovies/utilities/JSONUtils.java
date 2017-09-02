package com.example.android.popmovies.utilities;


import com.example.android.popmovies.data.Movie;
import com.example.android.popmovies.data.Review;
import com.example.android.popmovies.data.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Parse the JSON response.
 */

public final class JSONUtils {
    public static ArrayList<Movie> parseMovies(String rawData) {
        if (rawData.isEmpty()) {
            return null;
        }
        ArrayList<Movie> listOfMovies = new ArrayList<Movie>();
        try {
            JSONObject root = new JSONObject(rawData);
            JSONArray results = root.getJSONArray("results");
            JSONObject singleMovie = null;
            if (results != null) {
                for (int i = 0; i < results.length(); i++) {
                    singleMovie = results.getJSONObject(i);
                    Movie m = new Movie(singleMovie
                            .getString("title"), singleMovie
                            .getString("poster_path"), singleMovie
                            .getString("overview"), singleMovie
                            .getDouble("vote_average"), singleMovie
                            .getString("release_date"), singleMovie
                            .getInt("id"));
                    m.setBackDropPath("https://image.tmdb.org/t/p/w780" + singleMovie.getString("backdrop_path"));
                    listOfMovies.add(m);
                }
                return listOfMovies;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listOfMovies;
    }

    public static Movie parseMovieDetails(String rawData, Movie noDetails) {
        try {
            ArrayList<Review> reviewList = new ArrayList<Review>();
            ArrayList<Video> videoList = new ArrayList<Video>();
            JSONObject root = new JSONObject(rawData);
            JSONArray videos = root.getJSONObject("videos").getJSONArray("results");
            JSONArray reviews = root.getJSONObject("reviews").getJSONArray("results");
            if (videos != null) {
                for (int i = 0; i < videos.length(); i++) {
                    JSONObject details = videos.getJSONObject(i);
                    if (details.get("site").equals("YouTube") && details.getString("type").equals("Trailer")) {
                        Video v = new Video(details.getString("key"), details.getString("name"));
                        videoList.add(v);
                    }
                }
            }
            if (reviews != null) {
                for (int i = 0; i < reviews.length(); i++) {
                    JSONObject details = reviews.getJSONObject(i);
                    Review r = new Review(details.getString("author"), details.getString("content"));
                    reviewList.add(r);
                }

            }
            noDetails.setReviews(reviewList);
            noDetails.setVideos(videoList);
            return noDetails;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}

