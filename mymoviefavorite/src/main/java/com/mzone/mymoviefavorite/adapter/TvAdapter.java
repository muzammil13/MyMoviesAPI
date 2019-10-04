package com.mzone.mymoviefavorite.adapter;

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
import com.mzone.mymoviefavorite.BuildConfig;
import com.mzone.mymoviefavorite.DetailTvActivity;
import com.mzone.mymoviefavorite.R;
import com.mzone.mymoviefavorite.entity.TvFavorite;

import java.util.ArrayList;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<TvFavorite> TV = new ArrayList<>();

    public TvAdapter(Context context) {
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
            Intent intent = new Intent(context, DetailTvActivity.class);
            intent.putExtra(DetailTvActivity.EXTRA_DATA_TV, TV.get(position));
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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, genres, overview;
        ImageView imgPhoto;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.movie_image);
            title = itemView.findViewById(R.id.item_title);
            genres = itemView.findViewById(R.id.item_genres);
            overview = itemView.findViewById(R.id.item_overview);
            cardView = itemView.findViewById(R.id.cardParent);
        }
    }
}
