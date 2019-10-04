package com.mzone.mymoviesapi.data.remote.service;

import com.mzone.mymoviesapi.data.remote.models.GenresResponse;
import com.mzone.mymoviesapi.data.remote.models.MovieResponse;
import com.mzone.mymoviesapi.data.remote.models.TVResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TheMovieDBInterface {

    @GET("discover/movie")
    Call<MovieResponse> Movie(
            @Query("api_key") String apiKey);

    @GET("discover/tv")
    Call<TVResponse> TV(
            @Query("api_key") String apiKey);

    @GET("search/movie")
    Call<MovieResponse> MovieSearch(
            @Query("api_key") String apiKey,
            @Query("query") String movieName);

    @GET("search/tv")
    Call<TVResponse> TVSearch(
            @Query("api_key") String apiKey,
            @Query("query") String tvName);

    @GET("genre/movie/list")
    Call<GenresResponse> Genres(
            @Query("api_key") String apiKey);

    @GET("discover/movie")
    Call<MovieResponse> getReleaseToday(
            @Query("api_key") String apiKey,
            @Query("primary_release_date.gte") String gte,
            @Query("primary_release_date.lte") String lte);
}
