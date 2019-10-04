package com.mzone.mymoviesapi.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
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
import com.mzone.mymoviesapi.data.remote.models.GenreResult;
import com.mzone.mymoviesapi.data.remote.models.MovieResult;
import com.mzone.mymoviesapi.view.activity.DetailMovieActivity;

import java.util.ArrayList;
import java.util.List;

public class RecyclerMovieAdapter extends RecyclerView.Adapter<RecyclerMovieAdapter.MyViewHolder> {

    private Context context;
    private List<MovieResult> myMovie = new ArrayList<>();
    private List<GenreResult> allGenres = new ArrayList<>();

    public RecyclerMovieAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String categories = getGenres(myMovie.get(position).getGenreIds());
        holder.title.setText(myMovie.get(position).getTitle());
        holder.genres.setText(categories);
        holder.overview.setText(myMovie.get(position).getOverview());
        Glide.with(context)
                .load(BuildConfig.URL_COVER + myMovie.get(position).getPosterPath())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(holder.imgPhoto);

        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailMovieActivity.class);
            intent.putExtra(DetailMovieActivity.EXTRA_DATA_MOVIE, myMovie.get(position));
            intent.putExtra(DetailMovieActivity.CATEGORY, categories);
            context.startActivity(intent);
        });
    }

    private String getGenres(List<Integer> genreIds) {
        List<String> movieGenres = new ArrayList<>();
        for (Integer genreId : genreIds) {
            for (GenreResult genre : allGenres) {
                if (genre.getId().equals(genreId)) {
                    movieGenres.add(genre.getName());
                    break;
                }
            }
        }
        return TextUtils.join(", ", movieGenres);
    }

    @Override
    public int getItemCount() {
        return myMovie.size();
    }

    public void setMovies(List<MovieResult> myMovie) {
        this.myMovie = myMovie;
        notifyDataSetChanged();
    }

    public void setGenres(List<GenreResult> allGenres) {
        this.allGenres = allGenres;
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
