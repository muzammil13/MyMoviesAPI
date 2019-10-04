package com.mzone.mymoviesapi.data.local.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mzone.mymoviesapi.data.local.database.DatabaseContract;
import com.mzone.mymoviesapi.data.local.database.MovieHelper;
import com.mzone.mymoviesapi.data.local.database.TvHelper;

import java.util.Objects;

public class DataProvider extends ContentProvider {

    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;
    private static final int TVSHOW = 3;
    private static final int TVSHOW_ID = 4;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private MovieHelper movieHelper;
    private TvHelper tvHelper;

    static {
        sUriMatcher.addURI(DatabaseContract.AUTHORITY, DatabaseContract.MovieColumns.TABLE_MOVIE, MOVIE);
        sUriMatcher.addURI(DatabaseContract.AUTHORITY, DatabaseContract.MovieColumns.TABLE_MOVIE + "/#", MOVIE_ID);
        sUriMatcher.addURI(DatabaseContract.AUTHORITY, DatabaseContract.TVColumns.TABLE_TV, TVSHOW);
        sUriMatcher.addURI(DatabaseContract.AUTHORITY, DatabaseContract.TVColumns.TABLE_TV + "/#", TVSHOW_ID);
    }

    @Override
    public boolean onCreate() {
        movieHelper = MovieHelper.getInstance(getContext());
        movieHelper.open();
        tvHelper = TvHelper.getInstance(getContext());
        tvHelper.open();
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                cursor = movieHelper.queryAllMovie();
                break;
            case MOVIE_ID:
                cursor = movieHelper.queryMovieById(uri.getLastPathSegment());
                break;
            case TVSHOW:
                cursor = tvHelper.queryAllTv();
                break;
            case TVSHOW_ID:
                cursor = tvHelper.queryTvById(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        return cursor;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        Uri _uri = null;
        long added;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                added = movieHelper.insertMovie(contentValues);
                Objects.requireNonNull(getContext()).getContentResolver().notifyChange(DatabaseContract.MovieColumns.CONTENT_URI_MOVIE, null);
                _uri = Uri.parse(DatabaseContract.MovieColumns.CONTENT_URI_MOVIE + "/" + added);
                break;
            case TVSHOW:
                added = tvHelper.insertTv(contentValues);
                Objects.requireNonNull(getContext()).getContentResolver().notifyChange(DatabaseContract.TVColumns.CONTENT_URI_TV, null);
                _uri = Uri.parse(DatabaseContract.TVColumns.CONTENT_URI_TV + "/" + added);
                break;
            default:
                added = 0;
                break;
        }
        return _uri;
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        int updated;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                updated = movieHelper.updateMovie(uri.getLastPathSegment(), contentValues);
                Objects.requireNonNull(getContext()).getContentResolver().notifyChange(DatabaseContract.MovieColumns.CONTENT_URI_MOVIE, null);
                break;
            case TVSHOW:
                updated = tvHelper.updateTv(uri.getLastPathSegment(), contentValues);
                Objects.requireNonNull(getContext()).getContentResolver().notifyChange(DatabaseContract.TVColumns.CONTENT_URI_TV, null);
                break;
            default:
                updated = 0;
                break;
        }
        return updated;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ID:
                deleted = movieHelper.deleteMovieById(uri.getLastPathSegment());
                Objects.requireNonNull(getContext()).getContentResolver().notifyChange(DatabaseContract.MovieColumns.CONTENT_URI_MOVIE, null);
                break;
            case TVSHOW_ID:
                deleted = tvHelper.deleteTvById(uri.getLastPathSegment());
                Objects.requireNonNull(getContext()).getContentResolver().notifyChange(DatabaseContract.TVColumns.CONTENT_URI_TV, null);
                break;
            default:
                deleted = 0;
                break;
        }
        return deleted;
    }
}
