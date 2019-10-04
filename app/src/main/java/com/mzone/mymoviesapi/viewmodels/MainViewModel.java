package com.mzone.mymoviesapi.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.mzone.mymoviesapi.data.remote.Repository;
import com.mzone.mymoviesapi.data.remote.models.GenresResponse;
import com.mzone.mymoviesapi.data.remote.models.MovieResponse;
import com.mzone.mymoviesapi.data.remote.models.TVResponse;

import java.util.Objects;

import static com.mzone.mymoviesapi.BuildConfig.API_KEY;

public class MainViewModel extends AndroidViewModel {

    private LiveData<MovieResponse> movieResponseLiveData;
    private LiveData<TVResponse> tvResponseLiveData;
    private LiveData<GenresResponse> genresResponseLiveData;
    private Repository repository = new Repository();

    private final MutableLiveData<Keyword> keyword;
    private final LiveData<MovieResponse> movieSearchResponseLiveData;
    private final LiveData<TVResponse> tvSearchResponseLiveData;

    public MainViewModel(@NonNull Application application) {
        super(application);
        this.keyword = new MutableLiveData<>();
        this.movieResponseLiveData = repository.getMovies(API_KEY);
        this.tvResponseLiveData = repository.getTV(API_KEY);
        this.genresResponseLiveData = repository.getGenres(API_KEY);
        this.movieSearchResponseLiveData = Transformations.switchMap(keyword, input -> repository.getMoviesSearch(API_KEY, input.word));
        this.tvSearchResponseLiveData = Transformations.switchMap(keyword, input -> repository.getTVSearch(API_KEY, input.word));
    }

    public LiveData<MovieResponse> getMovieResponseLiveData() {
        return movieResponseLiveData;
    }

    public LiveData<TVResponse> getTVResponseLiveData() {
        return tvResponseLiveData;
    }

    public LiveData<MovieResponse> getMovieSearchResponseLiveData() {
        return movieSearchResponseLiveData;
    }

    public LiveData<TVResponse> getTVSearchResponseLiveData() {
        return tvSearchResponseLiveData;
    }

    public void setKeyword(String word) {
        Keyword update = new Keyword(word);
        if (Objects.equals(keyword.getValue(), update)) {
            return;
        }
        keyword.setValue(update);
    }

    static class Keyword {
        final String word;
        Keyword(String word) {
            this.word = word == null ? null : word.trim();
        }
    }

    public LiveData<GenresResponse> getGenresResponseLiveData() {
        return genresResponseLiveData;
    }
}
