package com.mzone.mymoviesapi.data.local.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieFavorite implements Parcelable {

    private int id_Favorite;
    private String title_Favorite;
    private String original_title_Favorite;
    private String release_date_Favorite;
    private String overview_Favorite;
    private String category;
    private String backdropPath;
    private String posterPath;

    public MovieFavorite() {
    }

    public MovieFavorite(int id_Favorite, String title_Favorite, String original_title_Favorite, String release_date_Favorite, String overview_Favorite, String category, String backdropPath, String posterPath) {
        this.id_Favorite = id_Favorite;
        this.title_Favorite = title_Favorite;
        this.original_title_Favorite = original_title_Favorite;
        this.release_date_Favorite = release_date_Favorite;
        this.overview_Favorite = overview_Favorite;
        this.category = category;
        this.backdropPath = backdropPath;
        this.posterPath = posterPath;
    }
    public int getId_Favorite() {
        return id_Favorite;
    }

    public void setId_Favorite(int id_Favorite) {
        this.id_Favorite = id_Favorite;
    }

    public String getTitle_Favorite() {
        return title_Favorite;
    }

    public void setTitle_Favorite(String title_Favorite) {
        this.title_Favorite = title_Favorite;
    }

    public String getOriginal_title_Favorite() {
        return original_title_Favorite;
    }

    public void setOriginal_title_Favorite(String original_title_Favorite) {
        this.original_title_Favorite = original_title_Favorite;
    }

    public String getRelease_date_Favorite() {
        return release_date_Favorite;
    }

    public void setRelease_date_Favorite(String release_date_Favorite) {
        this.release_date_Favorite = release_date_Favorite;
    }

    public String getOverview_Favorite() {
        return overview_Favorite;
    }

    public void setOverview_Favorite(String overview_Favorite) {
        this.overview_Favorite = overview_Favorite;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id_Favorite);
        dest.writeString(this.title_Favorite);
        dest.writeString(this.original_title_Favorite);
        dest.writeString(this.release_date_Favorite);
        dest.writeString(this.overview_Favorite);
        dest.writeString(this.category);
        dest.writeString(this.backdropPath);
        dest.writeString(this.posterPath);
    }

    private MovieFavorite(Parcel in) {
        this.id_Favorite = in.readInt();
        this.title_Favorite = in.readString();
        this.original_title_Favorite = in.readString();
        this.release_date_Favorite = in.readString();
        this.overview_Favorite = in.readString();
        this.category = in.readString();
        this.backdropPath = in.readString();
        this.posterPath = in.readString();
    }

    public static final Parcelable.Creator<MovieFavorite> CREATOR = new Parcelable.Creator<MovieFavorite>() {
        @Override
        public MovieFavorite createFromParcel(Parcel source) {
            return new MovieFavorite(source);
        }

        @Override
        public MovieFavorite[] newArray(int size) {
            return new MovieFavorite[size];
        }
    };
}
