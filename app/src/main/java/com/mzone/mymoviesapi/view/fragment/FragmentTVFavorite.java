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
import com.mzone.mymoviesapi.adapter.favTvAdapter;
import com.mzone.mymoviesapi.data.local.database.DatabaseContract;
import com.mzone.mymoviesapi.data.local.entity.TvFavorite;
import com.mzone.mymoviesapi.data.local.helper.MappingHelper;

import java.util.ArrayList;
import java.util.Objects;

public class FragmentTVFavorite extends Fragment {

    public FragmentTVFavorite() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tv, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.rvTV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        final favTvAdapter adapter = new favTvAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        Cursor dataCursor = Objects.requireNonNull(getActivity()).getContentResolver().query(DatabaseContract.TVColumns.CONTENT_URI_TV, null, null, null, null);
        ArrayList<TvFavorite> data;

        if (dataCursor != null) {
            data = MappingHelper.tvCursorToArrayList(dataCursor);
            adapter.setFavTV(data);
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.no_data), Toast.LENGTH_SHORT).show();
        }
    }
}
