package com.mzone.mymoviesapi.view.activity;

import android.content.Intent;
import android.net.Uri;
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
import com.mzone.mymoviesapi.data.local.entity.TvFavorite;

public class DetailTVFavoriteActivity extends AppCompatActivity {
    public static final String EXTRA_DATA_TV = "extra_data_tv";
    private int ID;

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

        TvFavorite dataTV = getIntent().getParcelableExtra(EXTRA_DATA_TV);

        if (dataTV != null) {
            ID = dataTV.getId_Favorite();
            final String title = String.format("%s (%s)", dataTV.getTitle_Favorite(), dataTV.getFirst_air_date_Favorite().substring(0, 4));
            Glide.with(this)
                    .load(BuildConfig.URL_BACKGROUND + dataTV.getBackdropPath())
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .into(img);
            overview.setText(dataTV.getOverview_Favorite());
            original.setText(dataTV.getOriginal_title_Favorite());
            first_date.setText(dataTV.getFirst_air_date_Favorite());
            collapsingToolbar.setTitle(title);
        } else {
            Toast.makeText(this, getResources().getString(R.string.no_data), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.remove_favorite, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.remove_favorite) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle(getResources().getString(R.string.remove_From_Favorite));
            alert.setPositiveButton(getResources().getString(R.string.yes), (dialog, whichButton) -> removeFavorite(ID));
            alert.setNegativeButton(getResources().getString(R.string.no), (dialog, whichButton) -> {
            });
            alert.show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void removeFavorite(int ID) {
        Uri uri = DatabaseContract.TVColumns.CONTENT_URI_TV;
        uri = uri.buildUpon().appendPath(String.valueOf(ID)).build();
        getContentResolver().delete(uri, null, null);
        Toast.makeText(this, getResources().getString(R.string.success_remove), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
