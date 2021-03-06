package com.example.android.popmovies.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class FavoritesContentProvider extends ContentProvider {

    public static final int URI_MOVIE_MATCH_ID = 42;
    public static final int URI_REVIEW_MATCH_ID = 43;
    public static final int URI_VIDEO_MATCH_ID = 44;
    public static final int URI_DELETE_FAVORITE_ID = 46;


    public static final UriMatcher sMatcher = buildUriMatcher();

    private FavoritesOpenHelper mOpenHelper;


    public static UriMatcher buildUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(FavoritesContract.AUTHORITY, FavoritesContract.MOVE_TABLE, URI_MOVIE_MATCH_ID);
        matcher.addURI(FavoritesContract.AUTHORITY, FavoritesContract.REVIEW_TABLE, URI_REVIEW_MATCH_ID);
        matcher.addURI(FavoritesContract.AUTHORITY, FavoritesContract.VIDEO_TABLE, URI_VIDEO_MATCH_ID);
        matcher.addURI(FavoritesContract.AUTHORITY, FavoritesContract.DELETE_PATH, URI_DELETE_FAVORITE_ID);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new FavoritesOpenHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor returnCursor = null;
        switch (sMatcher.match(uri)) {
            case URI_MOVIE_MATCH_ID:
                returnCursor = db.query(FavoritesContract.MOVE_TABLE, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case URI_REVIEW_MATCH_ID:
                returnCursor = db.query(FavoritesContract.REVIEW_TABLE, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case URI_VIDEO_MATCH_ID:
                returnCursor = db.query(FavoritesContract.VIDEO_TABLE, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Uri not matched: " + uri);
        }
        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long row = -1;
        Uri uriToReturn;
        switch (sMatcher.match(uri)) {
            case URI_MOVIE_MATCH_ID:
                row = db.insert(FavoritesContract.MOVE_TABLE, null, values);
                if (row != -1) {
                    uriToReturn = ContentUris.withAppendedId(FavoritesContract.MovieEntry.ACCESS_URI, row);
                } else {
                    throw new android.database.SQLException("Row was not inserted into the database " + uri);
                }
                break;
            case URI_REVIEW_MATCH_ID:
                row = db.insert(FavoritesContract.REVIEW_TABLE, null, values);
                if (row != -1) {
                    uriToReturn = ContentUris.withAppendedId(FavoritesContract.ReviewEntry.ACCESS_URI, row);
                } else {
                    throw new android.database.SQLException("Row was not inserted into the database " + uri);
                }
                break;
            case URI_VIDEO_MATCH_ID:
                row = db.insert(FavoritesContract.VIDEO_TABLE, null, values);
                if (row != -1) {
                    uriToReturn = ContentUris.withAppendedId(FavoritesContract.VideoEntry.ACCESS_URI, row);
                } else {
                    throw new android.database.SQLException("Row was not inserted into the database " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Uri not matched: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return uriToReturn;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        if (sMatcher.match(uri) == URI_DELETE_FAVORITE_ID) {
            //delete query
            int reviewRowsDeleted = db.delete(FavoritesContract.REVIEW_TABLE, FavoritesContract.ReviewEntry.COLUMN_ID_MOVIE + "=?", selectionArgs);
            int dbRowsDeleted = db.delete(FavoritesContract.VIDEO_TABLE, FavoritesContract.VideoEntry.COLUMN_ID_MOVIE + "=?", selectionArgs);
            int movieRowsDeleted = db.delete(FavoritesContract.MOVE_TABLE, selection, selectionArgs);
            getContext().getContentResolver().notifyChange(uri, null);
            return reviewRowsDeleted + dbRowsDeleted + movieRowsDeleted;
        } else {
            throw new UnsupportedOperationException("Uri not matched:  " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
