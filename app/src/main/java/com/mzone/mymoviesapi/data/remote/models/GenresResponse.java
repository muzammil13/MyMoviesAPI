package com.mzone.mymoviesapi.data.remote.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenresResponse {
    @SerializedName("genres")
    private List<GenreResult> genres = null;

    public List<GenreResult> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreResult> genres) {
        this.genres = genres;
    }
}
