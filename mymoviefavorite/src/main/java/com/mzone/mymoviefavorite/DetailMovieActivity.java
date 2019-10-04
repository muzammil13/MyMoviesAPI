package com.mzone.mymoviefavorite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.mzone.mymoviefavorite.entity.MovieFavorite;

public class DetailMovieActivity extends AppCompatActivity {

    public static final String EXTRA_DATA_MOVIE = "extra_data_movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        Toolbar toolbar = findViewById(R.id.toolbar_movie);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_movie);
        ImageView img = findViewById(R.id.detail_image_movie);
        TextView overview = findViewById(R.id.detail_overview_movie);
        TextView date = findViewById(R.id.detail_release_movie);
        TextView original = findViewById(R.id.detail_original_movie);

        MovieFavorite dataMovie = getIntent().getParcelableExtra(EXTRA_DATA_MOVIE);

        if(dataMovie!=null){
            final String title = String.format("%s (%s)", dataMovie.getTitle_Favorite(), dataMovie.getRelease_date_Favorite().substring(0, 4));
            Glide.with(this)
                    .load(BuildConfig.URL_BACKGROUND + dataMovie.getBackdropPath())
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .into(img);
            overview.setText(dataMovie.getOverview_Favorite());
            original.setText(dataMovie.getOriginal_title_Favorite());
            date.setText(dataMovie.getRelease_date_Favorite());
            collapsingToolbar.setTitle(title);
        }else {
            Toast.makeText(this, getResources().getString(R.string.no_data), Toast.LENGTH_SHORT).show();
        }

    }
}
