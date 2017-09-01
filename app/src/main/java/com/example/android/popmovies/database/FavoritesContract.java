package com.example.android.popmovies.database;

import android.net.Uri;
import android.provider.BaseColumns;


public class FavoritesContract {

    public static final String AUTHORITY = "com.example.android.popmovies";
    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);

    public static final String MOVE_TABLE = "movies";
    public static final String REVIEW_TABLE = "reviews";
    public static final String VIDEO_TABLE = "videos";
    public static final String DELETE_PATH = "delete";
    public static final Uri DELETE_URI = BASE_URI.buildUpon().appendPath(DELETE_PATH).build();


    public static final class MovieEntry implements BaseColumns {
        public static final Uri ACCESS_URI = BASE_URI.buildUpon().appendPath(MOVE_TABLE).build();

        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_DETAIL_IMAGE = "detail_image";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_USER_RATING = "user_rating";
        public static final String COLUMN_SYNOPSIS = "synopsis";
    }

    public static final class ReviewEntry implements BaseColumns {
        public static final Uri ACCESS_URI = BASE_URI.buildUpon().appendPath(REVIEW_TABLE).build();

        public static final String COLUMN_ID_MOVIE = "id_movie";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_CONTENT = "content";
    }

    public static final class VideoEntry implements BaseColumns {
        public static final Uri ACCESS_URI = BASE_URI.buildUpon().appendPath(VIDEO_TABLE).build();

        public static final String COLUMN_ID_MOVIE = "id_movie";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_KEY = "key";
    }

}
