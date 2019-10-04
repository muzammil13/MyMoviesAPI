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
import com.mzone.mymoviesapi.data.remote.models.TVResult;
import com.mzone.mymoviesapi.view.activity.DetailTVActivity;

import java.util.ArrayList;
import java.util.List;

public class RecyclerTVAdapter extends RecyclerView.Adapter<RecyclerTVAdapter.MyTVViewHolder> {

    private Context context;
    private List<TVResult> myTV = new ArrayList<>();
    private List<GenreResult> allGenres = new ArrayList<>();

    public RecyclerTVAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyTVViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_item, viewGroup, false);
        return new MyTVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyTVViewHolder holder, int position) {
        String categories = getGenres(myTV.get(position).getGenreIds());
        holder.title.setText(myTV.get(position).getName());
        holder.genres.setText(categories);
        holder.overview.setText(myTV.get(position).getOverview());
        Glide.with(context)
                .load(BuildConfig.URL_COVER + myTV.get(position).getPosterPath())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(holder.imgPhoto);

        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailTVActivity.class);
            intent.putExtra(DetailTVActivity.EXTRA_DATA_TV, myTV.get(position));
            intent.putExtra(DetailTVActivity.CATEGORY_TV, categories);
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
        return myTV.size();
    }

    public void setTV(List<TVResult> myTV) {
        this.myTV = myTV;
        notifyDataSetChanged();
    }

    public void setGenres(List<GenreResult> allGenres) {
        this.allGenres = allGenres;
        notifyDataSetChanged();
    }

    class MyTVViewHolder extends RecyclerView.ViewHolder {
        TextView title, genres, overview;
        ImageView imgPhoto;
        CardView cardView;

        MyTVViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.movie_image);
            title = itemView.findViewById(R.id.item_title);
            genres = itemView.findViewById(R.id.item_genres);
            overview = itemView.findViewById(R.id.item_overview);
            cardView = itemView.findViewById(R.id.cardParent);
        }
    }
}
