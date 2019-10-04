package com.mzone.mymoviesapi.data.remote;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mzone.mymoviesapi.data.remote.service.TheMovieDBClient;
import com.mzone.mymoviesapi.data.remote.service.TheMovieDBInterface;
import com.mzone.mymoviesapi.data.remote.models.GenresResponse;
import com.mzone.mymoviesapi.data.remote.models.MovieResponse;
import com.mzone.mymoviesapi.data.remote.models.TVResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    private TheMovieDBInterface apiRequest;

    public Repository() {
        apiRequest = TheMovieDBClient.getRetrofitInstance().create(TheMovieDBInterface.class);
    }

    public LiveData<MovieResponse> getMovies(String key) {
        final MutableLiveData<MovieResponse> dataMovie = new MutableLiveData<>();
        apiRequest.Movie(key)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                        if (response.body() != null) {
                            dataMovie.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                        dataMovie.setValue(null);
                    }
                });
        return dataMovie;
    }

    public LiveData<MovieResponse> getMoviesSearch(String key, String Name) {
        final MutableLiveData<MovieResponse> dataMovieSearch = new MutableLiveData<>();
        apiRequest.MovieSearch(key, Name)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                        if (response.body() != null) {
                            dataMovieSearch.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                        dataMovieSearch.setValue(null);
                    }
                });
        return dataMovieSearch;
    }

    public LiveData<TVResponse> getTV(String key) {
        final MutableLiveData<TVResponse> dataTV = new MutableLiveData<>();
        apiRequest.TV(key)
                .enqueue(new Callback<TVResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<TVResponse> call, @NonNull Response<TVResponse> response) {
                        if (response.body() != null) {
                            dataTV.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TVResponse> call, @NonNull Throwable t) {
                        dataTV.setValue(null);
                    }
                });
        return dataTV;
    }

    public LiveData<TVResponse> getTVSearch(String key, String Name) {
        final MutableLiveData<TVResponse> dataTV = new MutableLiveData<>();
        apiRequest.TVSearch(key, Name)
                .enqueue(new Callback<TVResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<TVResponse> call, @NonNull Response<TVResponse> response) {
                        if (response.body() != null) {
                            dataTV.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TVResponse> call, @NonNull Throwable t) {
                        dataTV.setValue(null);
                    }
                });
        return dataTV;
    }

    public LiveData<GenresResponse> getGenres(String key) {
        final MutableLiveData<GenresResponse> genres = new MutableLiveData<>();
        apiRequest.Genres(key)
                .enqueue(new Callback<GenresResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<GenresResponse> call, @NonNull Response<GenresResponse> response) {
                        if (response.body() != null) {
                            genres.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<GenresResponse> call, @NonNull Throwable t) {
                        genres.setValue(null);
                    }
                });
        return genres;
    }
}
