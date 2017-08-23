package com.example.android.popmovies.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.popmovies.data.Movie;


public class FavoritesOpenHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "favorites.db";
    private static final int VERSION = 1;

    public FavoritesOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createMovies = "CREATE TABLE " + FavoritesContract.MOVE_TABLE + " (" +
                FavoritesContract.MovieEntry._ID + " INTEGER PRIMARY KEY, " +
                FavoritesContract.MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                FavoritesContract.MovieEntry.COLUMN_IMAGE + " BLOB NOT NULL, " +
                FavoritesContract.MovieEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                FavoritesContract.MovieEntry.COLUMN_USER_RATING + " TEXT NOT NULL, " +
                FavoritesContract.MovieEntry.COLUMN_SYNOPSIS + " TEXT NOT NULL);";
        String createReviews = "CREATE TABLE " + FavoritesContract.REVIEW_TABLE + " (" +
                FavoritesContract.ReviewEntry._ID + " INTEGER PRIMARY KEY, " +
                FavoritesContract.ReviewEntry.COLUMN_ID_MOVIE + " INTEGER, " +
                FavoritesContract.ReviewEntry.COLUMN_AUTHOR + " TEXT NOT NULL, " +
                FavoritesContract.ReviewEntry.COLUMN_CONTENT + " TEXT NOT NULL, " +
                "FOREIGN KEY (" + FavoritesContract.ReviewEntry.COLUMN_ID_MOVIE + ") REFERENCES " +
                FavoritesContract.MOVE_TABLE + " (" + FavoritesContract.MovieEntry._ID + "));";
        String createVideos = "CREATE TABLE " + FavoritesContract.VIDEO_TABLE + " (" +
                FavoritesContract.VideoEntry._ID + " INTEGER PRIMARY KEY, " +
                FavoritesContract.VideoEntry.COLUMN_ID_MOVIE + " INTEGER, " +
                FavoritesContract.VideoEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                FavoritesContract.VideoEntry.COLUMN_KEY + " TEXT NOT NULL, " +
                "FOREIGN KEY (" + FavoritesContract.VideoEntry.COLUMN_ID_MOVIE + ") REFERENCES " +
                FavoritesContract.MOVE_TABLE + " (" + FavoritesContract.MovieEntry._ID  + "));";

        db.execSQL(createMovies);
        db.execSQL(createReviews);
        db.execSQL(createVideos);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + FavoritesContract.MOVE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS" + FavoritesContract.REVIEW_TABLE);
        db.execSQL("DROP TABLE IF EXISTS" + FavoritesContract.VIDEO_TABLE);
    }
}
