package com.mzone.mymoviesapi.view.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mzone.mymoviesapi.R;
import com.mzone.mymoviesapi.adapter.FavMovieAdapter;
import com.mzone.mymoviesapi.data.local.database.DatabaseContract;
import com.mzone.mymoviesapi.data.local.entity.MovieFavorite;
import com.mzone.mymoviesapi.data.local.helper.MappingHelper;

import java.util.ArrayList;
import java.util.Objects;

public class FragmentMoviesFavorite extends Fragment {

    public FragmentMoviesFavorite() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.rvMovies);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        FavMovieAdapter adapter = new FavMovieAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        Cursor dataCursor = Objects.requireNonNull(getActivity()).getContentResolver().query(DatabaseContract.MovieColumns.CONTENT_URI_MOVIE, null, null, null, null);
        ArrayList<MovieFavorite> data;

        if (dataCursor != null) {
            data = MappingHelper.movieCursorToArrayList(dataCursor);
            adapter.setFavMovies(data);
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.no_data), Toast.LENGTH_SHORT).show();
        }
    }
}