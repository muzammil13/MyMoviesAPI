package com.mzone.mymoviefavorite.database;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String AUTHORITY = "com.mzone.mymoviesapi";
    private static final String SCHEME = "content";

    public static final class MovieColumns implements BaseColumns {
        public static String TABLE_MOVIE = "movie";
        public static String TITLE = "title";
        public static String ORIGINAL_TITLE = "original_title";
        public static String POSTER = "poster";
        public static String BACKDROP = "backdrop";
        public static String RELEASE_DATE = "release_date";
        public static String OVERVIEW = "overview";
        public static String CATEGORY = "category";

        public static final Uri CONTENT_URI_MOVIE = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_MOVIE)
                .build();
    }

    public static final class TVColumns implements BaseColumns{
        public static String TABLE_TV = "tvshow";
        public static String TITLE = "title";
        public static String ORIGINAL_TITLE = "original_title";
        public static String POSTER = "poster";
        public static String BACKDROP = "backdrop";
        public static String FIRST_AIR_DATE = "first_air_date";
        public static String OVERVIEW = "overview";
        public static String CATEGORY = "category";

        public static final Uri CONTENT_URI_TV = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_TV)
                .build();
    }
}
