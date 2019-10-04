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
import com.mzone.mymoviesapi.data.remote.models.TVResult;

public class DetailTVActivity extends AppCompatActivity {
    public static final String EXTRA_DATA_TV = "extra_data_tv";
    public static final String CATEGORY_TV = "category_tv";
    TVResult dataTV;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv);

        Toolbar toolbar = findViewById(R.id.toolbar_tv);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_tv);
        ImageView img = findViewById(R.id.detail_image_tv);
        TextView overview = findViewById(R.id.detail_overview_tv);
        TextView first_date = findViewById(R.id.detail_first_date_tv);
        TextView original = findViewById(R.id.detail_original_tv);

        dataTV = getIntent().getParcelableExtra(EXTRA_DATA_TV);
        category = getIntent().getStringExtra(CATEGORY_TV);

        if (dataTV != null) {
            final String title = String.format("%s (%s)", dataTV.getName(), dataTV.getFirstAirDate().substring(0, 4));
            Glide.with(this)
                    .load(BuildConfig.URL_BACKGROUND + dataTV.getBackdropPath())
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .into(img);
            overview.setText(dataTV.getOverview());
            original.setText(dataTV.getOriginalName());
            first_date.setText(dataTV.getFirstAirDate());
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

        contentValues.put(DatabaseContract.TVColumns._ID, dataTV.getId().toString());
        contentValues.put(DatabaseContract.TVColumns.TITLE, dataTV.getName());
        contentValues.put(DatabaseContract.TVColumns.ORIGINAL_TITLE, dataTV.getOriginalName());
        contentValues.put(DatabaseContract.TVColumns.FIRST_AIR_DATE, dataTV.getFirstAirDate());
        contentValues.put(DatabaseContract.TVColumns.OVERVIEW, dataTV.getOverview());
        contentValues.put(DatabaseContract.TVColumns.POSTER, dataTV.getPosterPath());
        contentValues.put(DatabaseContract.TVColumns.BACKDROP, dataTV.getBackdropPath());
        contentValues.put(DatabaseContract.TVColumns.CATEGORY, category);

        getContentResolver().insert(DatabaseContract.TVColumns.CONTENT_URI_TV, contentValues);

        Toast.makeText(this, getResources().getString(R.string.success_add), Toast.LENGTH_SHORT).show();
    }
}
