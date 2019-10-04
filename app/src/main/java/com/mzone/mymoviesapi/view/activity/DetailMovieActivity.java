package com.mzone.mymoviesapi.view.activity;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.mzone.mymoviesapi.BuildConfig;
import com.mzone.mymoviesapi.R;
import com.mzone.mymoviesapi.data.local.database.DatabaseContract;
import com.mzone.mymoviesapi.data.remote.models.MovieResult;


public class DetailMovieActivity extends AppCompatActivity {
    public static final String EXTRA_DATA_MOVIE = "extra_data_movie";
    public static final String CATEGORY = "category";
    MovieResult dataMovie;
    String category;

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

        dataMovie = getIntent().getParcelableExtra(EXTRA_DATA_MOVIE);
        category = getIntent().getStringExtra(CATEGORY);

        if (dataMovie != null) {
            final String title = String.format("%s (%s)", dataMovie.getTitle(), dataMovie.getReleaseDate().substring(0, 4));
            Glide.with(this)
                    .load(BuildConfig.URL_BACKGROUND + dataMovie.getBackdropPath())
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .into(img);
            overview.setText(dataMovie.getOverview());
            original.setText(dataMovie.getOriginalTitle());
            date.setText(dataMovie.getReleaseDate());
            collapsingToolbar.setTitle(title);
        } else {
            Toast.makeText(this, getResources().getString(R.string.no_data), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.add_favorite, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_favorite) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle(getResources().getString(R.string.add_To_Favorite));
            alert.setPositiveButton(getResources().getString(R.string.yes), (dialog, whichButton) -> saveFavorite());
            alert.setNegativeButton(getResources().getString(R.string.no), (dialog, whichButton) -> {
            });
            alert.show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveFavorite() {
        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseContract.MovieColumns._ID, dataMovie.getId().toString());
        contentValues.put(DatabaseContract.MovieColumns.TITLE, dataMovie.getTitle());
        contentValues.put(DatabaseContract.MovieColumns.ORIGINAL_TITLE, dataMovie.getOriginalTitle());
        contentValues.put(DatabaseContract.MovieColumns.RELEASE_DATE, dataMovie.getReleaseDate());
        contentValues.put(DatabaseContract.MovieColumns.OVERVIEW, dataMovie.getOverview());
        contentValues.put(DatabaseContract.MovieColumns.POSTER, dataMovie.getPosterPath());
        contentValues.put(DatabaseContract.MovieColumns.BACKDROP, dataMovie.getBackdropPath());
        contentValues.put(DatabaseContract.MovieColumns.CATEGORY, category);

        getContentResolver().insert(DatabaseContract.MovieColumns.CONTENT_URI_MOVIE, contentValues);

        Toast.makeText(this, getResources().getString(R.string.success_add), Toast.LENGTH_SHORT).show();
    }
}
