package com.mzone.mymoviesapi.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mzone.mymoviesapi.BuildConfig;
import com.mzone.mymoviesapi.R;
import com.mzone.mymoviesapi.data.local.entity.TvFavorite;
import com.mzone.mymoviesapi.view.activity.DetailTVFavoriteActivity;

import java.util.ArrayList;

public class favTvAdapter extends RecyclerView.Adapter<favTvAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<TvFavorite> TV = new ArrayList<>();

    public favTvAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(TV.get(position).getTitle_Favorite());
        holder.genres.setText(TV.get(position).getCategories());
        holder.overview.setText(TV.get(position).getOverview_Favorite());
        Glide.with(context)
                .load(BuildConfig.URL_COVER + TV.get(position).getPosterPath())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(holder.imgPhoto);

        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailTVFavoriteActivity.class);
            intent.putExtra(DetailTVFavoriteActivity.EXTRA_DATA_TV, TV.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return TV.size();
    }

    public void setFavTV(ArrayList<TvFavorite> TV) {
        this.TV.clear();
        this.TV = TV;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, genres, overview;
        ImageView imgPhoto;
        CardView cardView;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.movie_image);
            title = itemView.findViewById(R.id.item_title);
            genres = itemView.findViewById(R.id.item_genres);
            overview = itemView.findViewById(R.id.item_overview);
            cardView = itemView.findViewById(R.id.cardParent);
        }
    }
}
