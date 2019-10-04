package com.mzone.mymoviesapi.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.mzone.mymoviesapi.BuildConfig;
import com.mzone.mymoviesapi.R;
import com.mzone.mymoviesapi.data.local.database.WidgetHelper;
import com.mzone.mymoviesapi.data.local.entity.MovieFavorite;
import com.mzone.mymoviesapi.data.local.entity.TvFavorite;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class StackRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private List<String> imgWidget = new ArrayList<>();
    private WidgetHelper helper;
    private final Context mContext;

    StackRemoteViewFactory(Context context) {
        mContext = context;
        helper = WidgetHelper.getInstance(mContext);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        final long identityToken = Binder.clearCallingIdentity();
        helper.open();
        List<MovieFavorite> movies = helper.getAllMovies();
        List<TvFavorite> tvShows = helper.getAllTVShows();
        for (MovieFavorite movie : movies) {
            imgWidget.add(movie.getPosterPath());
        }
        for (TvFavorite tvShow : tvShows) {
            imgWidget.add(tvShow.getPosterPath());
        }
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return imgWidget.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        Bitmap bmp = null;
        try {
            bmp = Glide.with(mContext)
                    .asBitmap()
                    .load(BuildConfig.URL_COVER + imgWidget.get(i))
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .submit()
                    .get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        views.setImageViewBitmap(R.id.imageView, bmp);

        Bundle extras = new Bundle();
        extras.putInt(StackWidget.EXTRA_ITEM, i);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        views.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
