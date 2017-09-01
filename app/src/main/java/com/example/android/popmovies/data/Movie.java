package com.example.android.popmovies.data;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import java.util.ArrayList;

/**
 * Holds data for each movie in the response.
 */
@Parcel
public class Movie {
    private String title;


    private String thumbnailPath;
    private String backDropPath;
    private String plotSynopsis;
    private double userRating;
    private String releaseDate;
    private byte[] detailByteImage;
    private byte[] posterByteImage;


    private boolean favorite = false;
    private int id;


    private ArrayList<Review> reviews;
    private ArrayList<Video> videos;

    @ParcelConstructor
    public Movie(String title, String thumbnailPath, String plotSynopsis, double userRating, String releaseDate, int id) {
        this.title = title;
        if (thumbnailPath != null) {
            this.thumbnailPath = "https://image.tmdb.org/t/p/w342" + thumbnailPath;
        }
        this.plotSynopsis = plotSynopsis;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
        this.id = id;

    }

    public String getTitle() {
        return title;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public double getUserRating() {
        return userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public boolean getFavorite() {
        return favorite;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public ArrayList<Video> getVideos() {
        return videos;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    public void setVideos(ArrayList<Video> videos) {
        this.videos = videos;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public byte[] getPosterByteImage() {
        return posterByteImage;
    }

    public void setPosterByteImage(byte[] posterByteImage) {
        this.posterByteImage = posterByteImage;
    }


    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public byte[] getDetailByteImage() {
        return detailByteImage;
    }

    public void setDetailByteImage(byte[] detailByteImage) {
        this.detailByteImage = detailByteImage;
    }

    public String getBackDropPath() {
        return backDropPath;
    }

    public void setBackDropPath(String backDropPath) {
        this.backDropPath = backDropPath;
    }

}
