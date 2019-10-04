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
import com.mzone.mymoviefavorite.DetailMovieActivity;
import com.mzone.mymoviefavorite.R;
import com.mzone.mymoviefavorite.entity.MovieFavorite;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder>  {

    private Context context;
    private ArrayList<MovieFavorite> movies = new ArrayList<>();

    public MovieAdapter(Context context) {
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
        holder.title.setText(movies.get(position).getTitle_Favorite());
        holder.genres.setText(movies.get(position).getCategory());
        holder.overview.setText(movies.get(position).getOverview_Favorite());
        Glide.with(context)
                .load(BuildConfig.URL_COVER + movies.get(position).getPosterPath())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(holder.imgPhoto);

        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailMovieActivity.class);
            intent.putExtra(DetailMovieActivity.EXTRA_DATA_MOVIE, movies.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setFavMovies(ArrayList<MovieFavorite> movies) {
        this.movies.clear();
        this.movies = movies;
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
