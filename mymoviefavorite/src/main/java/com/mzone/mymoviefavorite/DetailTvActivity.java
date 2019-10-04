package com.mzone.mymoviefavorite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.mzone.mymoviefavorite.entity.TvFavorite;

public class DetailTvActivity extends AppCompatActivity {
    public static final String EXTRA_DATA_TV = "extra_data_tv";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv);

        Toolbar toolbar = findViewById(R.id.toolbar_tv);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_tv);
        ImageView img = findViewById(R.id.detail_image_tv);
        TextView overview = findViewById(R.id.detail_overview_tv);
        TextView first_date = findViewById(R.id.detail_first_date_tv);
        TextView original = findViewById(R.id.detail_original_tv);

        TvFavorite dataTV = getIntent().getParcelableExtra(EXTRA_DATA_TV);
        if(dataTV!=null){
            final String title = String.format("%s (%s)", dataTV.getTitle_Favorite(), dataTV.getFirst_air_date_Favorite().substring(0,4));
            Glide.with(this)
                    .load(BuildConfig.URL_BACKGROUND + dataTV.getBackdropPath())
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .into(img);
            overview.setText(dataTV.getOverview_Favorite());
            original.setText(dataTV.getOriginal_title_Favorite());
            first_date.setText(dataTV.getFirst_air_date_Favorite());
            collapsingToolbar.setTitle(title);
        }else {
            Toast.makeText(this, getResources().getString(R.string.no_data), Toast.LENGTH_SHORT).show();
        }
    }
}
