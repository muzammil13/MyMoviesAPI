package com.mzone.mymoviesapi.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mzone.mymoviesapi.R;
import com.mzone.mymoviesapi.adapter.RecyclerMovieAdapter;
import com.mzone.mymoviesapi.data.remote.models.GenreResult;
import com.mzone.mymoviesapi.data.remote.models.MovieResult;
import com.mzone.mymoviesapi.viewmodels.MainViewModel;

import java.util.List;

public class FragmentMovies extends Fragment {

    private MainViewModel mainViewModel;
    private RecyclerMovieAdapter adapter;
    private ProgressBar pbMovies;
    private String keySearch = null;

    public FragmentMovies() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pbMovies = view.findViewById(R.id.pbMovies);
        pbMovies.setVisibility(View.VISIBLE);

        final RecyclerView recyclerView = view.findViewById(R.id.rvMovies);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        adapter = new RecyclerMovieAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        init();
    }

    private void init() {
        loadGenres();
        if (keySearch != null) {
            loadSearchResult();
        } else {
            loadDiscovery();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            getActivity();
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint(getResources().getString(R.string.search_movie));
        if (keySearch != null && !keySearch.isEmpty()) {
            menuItem.expandActionView();
            searchView.setQuery(keySearch, false);
            searchView.clearFocus();
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                keySearch = query;
                mainViewModel.setKeyword(keySearch);
                pbMovies.setVisibility(View.VISIBLE);
                loadSearchResult();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    keySearch = null;
                    loadDiscovery();
                }
                return false;
            }
        });
    }

    private void loadDiscovery() {
        mainViewModel.getMovieResponseLiveData().observe(this, movieResponse -> {
            if (movieResponse != null) {
                pbMovies.setVisibility(View.GONE);
                List<MovieResult> movies = movieResponse.getResults();
                adapter.setMovies(movies);
            } else {
                pbMovies.setVisibility(View.GONE);
                Toast.makeText(getActivity(), getResources().getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadSearchResult() {
        mainViewModel.getMovieSearchResponseLiveData().observe(this, movieResponse -> {
            if (movieResponse != null) {
                pbMovies.setVisibility(View.GONE);
                List<MovieResult> movieSearch = movieResponse.getResults();
                if (movieSearch.size() == 0) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.search_no_data), Toast.LENGTH_SHORT).show();
                }
                adapter.setMovies(movieSearch);
            } else {
                pbMovies.setVisibility(View.GONE);
                Toast.makeText(getActivity(), getResources().getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadGenres() {
        mainViewModel.getGenresResponseLiveData().observe(this, genresResponse -> {
            if (genresResponse != null) {
                pbMovies.setVisibility(View.GONE);
                List<GenreResult> allGenre = genresResponse.getGenres();
                adapter.setGenres(allGenre);
            } else {
                pbMovies.setVisibility(View.GONE);
                Toast.makeText(getActivity(), getResources().getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
