package com.mzone.mymoviefavorite.helper;

import android.database.Cursor;

import com.mzone.mymoviefavorite.database.DatabaseContract;
import com.mzone.mymoviefavorite.entity.MovieFavorite;
import com.mzone.mymoviefavorite.entity.TvFavorite;

import java.util.ArrayList;

public class MappingHelper {

    public static ArrayList<MovieFavorite> movieCursorToArrayList(Cursor moviesCursor) {
        ArrayList<MovieFavorite> movieList = new ArrayList<>();
        while (moviesCursor.moveToNext()) {
            int id = moviesCursor.getInt(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns._ID));
            String title = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.TITLE));
            String original_title = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.ORIGINAL_TITLE));
            String poster = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.POSTER));
            String backdrop = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.BACKDROP));
            String release_date = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.RELEASE_DATE));
            String overview = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.OVERVIEW));
            String category = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.CATEGORY));
            movieList.add(new MovieFavorite(id,title,original_title, release_date, overview, category, backdrop, poster));
        }
        return movieList;
    }

    public static MovieFavorite movieCursorToObject(Cursor moviesCursor) {
        moviesCursor.moveToFirst();
        int id = moviesCursor.getInt(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns._ID));
        String title = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.TITLE));
        String original_title = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.ORIGINAL_TITLE));
        String poster = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.POSTER));
        String backdrop = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.BACKDROP));
        String release_date = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.RELEASE_DATE));
        String overview = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.OVERVIEW));
        String category = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.CATEGORY));
        return new MovieFavorite(id,title,original_title, release_date, overview, category, backdrop, poster);
    }

    public static ArrayList<TvFavorite> tvCursorToArrayList(Cursor tvCursor) {
        ArrayList<TvFavorite> tvList = new ArrayList<>();

        while (tvCursor.moveToNext()) {
            int id = tvCursor.getInt(tvCursor.getColumnIndexOrThrow(DatabaseContract.TVColumns._ID));
            String title = tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TVColumns.TITLE));
            String original_title = tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TVColumns.ORIGINAL_TITLE));
            String poster = tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TVColumns.POSTER));
            String backdrop = tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TVColumns.BACKDROP));
            String first_release = tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TVColumns.FIRST_AIR_DATE));
            String overview = tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TVColumns.OVERVIEW));
            String category = tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TVColumns.CATEGORY));
            tvList.add(new TvFavorite(id,title,original_title, first_release, overview, category, backdrop, poster));
        }
        return tvList;
    }

    public static TvFavorite tvCursorToObject(Cursor tvCursor) {
        tvCursor.moveToFirst();
        int id = tvCursor.getInt(tvCursor.getColumnIndexOrThrow(DatabaseContract.TVColumns._ID));
        String title = tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TVColumns.TITLE));
        String original_title = tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TVColumns.ORIGINAL_TITLE));
        String poster = tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TVColumns.POSTER));
        String backdrop = tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TVColumns.BACKDROP));
        String first_release = tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TVColumns.FIRST_AIR_DATE));
        String overview = tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TVColumns.OVERVIEW));
        String category = tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TVColumns.CATEGORY));
        return new TvFavorite(id,title,original_title, first_release, overview, category, backdrop, poster);
    }
}
