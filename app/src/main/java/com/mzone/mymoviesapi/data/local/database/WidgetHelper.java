package com.mzone.mymoviesapi.data.local.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mzone.mymoviesapi.data.local.entity.MovieFavorite;
import com.mzone.mymoviesapi.data.local.entity.TvFavorite;

import java.util.ArrayList;
import java.util.List;

import static android.provider.BaseColumns._ID;

public class WidgetHelper {

    private static final String MOVIE_TABLE = DatabaseContract.MovieColumns.TABLE_MOVIE;
    private static final String TV_TABLE = DatabaseContract.TVColumns.TABLE_TV;

    private static DatabaseHelper databaseHelper;
    private static WidgetHelper INSTANCE;

    private static SQLiteDatabase database;

    private WidgetHelper(Context context){
        databaseHelper = new DatabaseHelper(context);
    }

    public static WidgetHelper getInstance(Context context){
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new WidgetHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public List<MovieFavorite> getAllMovies(){
        List<MovieFavorite> arrayList = new ArrayList<>();
        Cursor cursor = database.query(MOVIE_TABLE, null, null, null, null, null, null, null);
        MovieFavorite movie;
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                movie = new MovieFavorite();
                movie.setId_Favorite(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movie.setTitle_Favorite(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.TITLE)));
                movie.setOriginal_title_Favorite(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.ORIGINAL_TITLE)));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.POSTER)));
                movie.setBackdropPath(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.BACKDROP)));
                movie.setRelease_date_Favorite(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.RELEASE_DATE)));
                movie.setOverview_Favorite(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.OVERVIEW)));
                movie.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.CATEGORY)));
                arrayList.add(movie);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public List<TvFavorite> getAllTVShows(){
        List<TvFavorite> arrayList = new ArrayList<>();
        Cursor cursor = database.query(TV_TABLE, null, null, null, null, null, null, null);
        cursor.moveToFirst();
        TvFavorite tvShow;
        if (cursor.getCount() > 0){
            do {
                tvShow = new TvFavorite();
                tvShow.setId_Favorite(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                tvShow.setTitle_Favorite(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TVColumns.TITLE)));
                tvShow.setOriginal_title_Favorite(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TVColumns.ORIGINAL_TITLE)));
                tvShow.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TVColumns.POSTER)));
                tvShow.setBackdropPath(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TVColumns.BACKDROP)));
                tvShow.setFirst_air_date_Favorite(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TVColumns.FIRST_AIR_DATE)));
                tvShow.setOverview_Favorite(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TVColumns.OVERVIEW)));
                tvShow.setCategories(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TVColumns.CATEGORY)));
                arrayList.add(tvShow);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close(){
        databaseHelper.close();

        if (database.isOpen())
            database.close();
    }
}
