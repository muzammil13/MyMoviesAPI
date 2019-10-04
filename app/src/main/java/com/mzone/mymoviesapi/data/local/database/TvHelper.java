package com.mzone.mymoviesapi.data.local.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class TvHelper {

    private static final String DATABASE_TABLE = DatabaseContract.TVColumns.TABLE_TV;
    private static DatabaseHelper databaseHelper;
    private static TvHelper INSTANCE;

    private static SQLiteDatabase database;

    private TvHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static TvHelper getInstance(Context context){
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new TvHelper(context);
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

    public Cursor queryAllTv() {
        return database.query(DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                DatabaseContract.TVColumns._ID + " DESC");
    }

    public Cursor queryTvById(String id) {
        return database.query(DATABASE_TABLE, null
                , DatabaseContract.TVColumns._ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public long insertTv(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int updateTv(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, DatabaseContract.TVColumns._ID + " = ?", new String[]{id});
    }

    public int deleteTvById(String id) {
        return database.delete(DATABASE_TABLE, DatabaseContract.TVColumns._ID + " = ?", new String[]{id});
    }
}
