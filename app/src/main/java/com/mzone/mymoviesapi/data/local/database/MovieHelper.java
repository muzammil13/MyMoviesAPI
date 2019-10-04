package com.mzone.mymoviesapi.data.local.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MovieHelper {

    private static final String DATABASE_TABLE = DatabaseContract.MovieColumns.TABLE_MOVIE;
    private static DatabaseHelper databaseHelper;
    private static MovieHelper INSTANCE;

    private static SQLiteDatabase database;

    private MovieHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static MovieHelper getInstance(Context context){
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new MovieHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close(){
        databaseHelper.close();

        if (database.isOpen())
            database.close();
    }

    public Cursor queryAllMovie() {
        return database.query(DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                DatabaseContract.MovieColumns._ID + " DESC");
    }

    public Cursor queryMovieById(String id) {
        return database.query(DATABASE_TABLE, null
                , DatabaseContract.MovieColumns._ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public long insertMovie(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int updateMovie(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, DatabaseContract.MovieColumns._ID + " = ?", new String[]{id});
    }

    public int deleteMovieById(String id) {
        return database.delete(DATABASE_TABLE, DatabaseContract.MovieColumns._ID + " = ?", new String[]{id});
    }
}
