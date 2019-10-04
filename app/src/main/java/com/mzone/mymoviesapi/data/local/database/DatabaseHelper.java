package com.mzone.mymoviesapi.data.local.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "dbMyMovie";

    private static final int DATABASE_VERSION = 2;

    private static final String SQL_CREATE_TABLE_MOVIE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.MovieColumns.TABLE_MOVIE,
            DatabaseContract.MovieColumns._ID,
            DatabaseContract.MovieColumns.TITLE,
            DatabaseContract.MovieColumns.ORIGINAL_TITLE,
            DatabaseContract.MovieColumns.POSTER,
            DatabaseContract.MovieColumns.BACKDROP,
            DatabaseContract.MovieColumns.RELEASE_DATE,
            DatabaseContract.MovieColumns.OVERVIEW,
            DatabaseContract.MovieColumns.CATEGORY
    );

    private static final String SQL_CREATE_TABLE_TV = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.TVColumns.TABLE_TV,
            DatabaseContract.TVColumns._ID,
            DatabaseContract.TVColumns.TITLE,
            DatabaseContract.TVColumns.ORIGINAL_TITLE,
            DatabaseContract.TVColumns.POSTER,
            DatabaseContract.TVColumns.BACKDROP,
            DatabaseContract.TVColumns.FIRST_AIR_DATE,
            DatabaseContract.TVColumns.OVERVIEW,
            DatabaseContract.TVColumns.CATEGORY
    );

    DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_MOVIE);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_TV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.MovieColumns.TABLE_MOVIE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TVColumns.TABLE_TV);
        onCreate(sqLiteDatabase);
    }
}
