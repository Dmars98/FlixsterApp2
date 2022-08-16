package com.example.flixsterapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.flixsterapp.DetailActivity;
import com.example.flixsterapp.R;
import com.example.flixsterapp.databinding.ItemMovieBinding;
import com.example.flixsterapp.databinding.PopularMovieBinding;
import com.example.flixsterapp.models.Movie;

import org.parceler.Parcels;

import java.util.List;

import javax.annotation.Nonnull;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    static Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if(viewType == 0){
            ItemMovieBinding view = DataBindingUtil.inflate(inflater,R.layout.item_movie, parent,false);
            viewHolder = new ViewHolder(view);
        }
        else{
            PopularMovieBinding view = DataBindingUtil.inflate(inflater,R.layout.popular_movie, parent,false);
            viewHolder = new ViewHolder1(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Movie movie = movies.get(position);
        if(holder.getItemViewType() == 0) {
            ViewHolder holder1 = (ViewHolder) holder;
            holder1.bind(movie);
        }else{
            ViewHolder1 holder2 = (ViewHolder1) holder;
            holder2.bind(movie);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(movies.get(position).getRating() < 5)
            return 0;
        return 1;
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout container;

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        ItemMovieBinding binding;
        public ViewHolder(@Nonnull ItemMovieBinding itemView){
            super(itemView.getRoot());
            tvTitle = itemView.tvTitle;
            tvOverview = itemView.tvoverview;
            ivPoster = itemView.ivPoster;
            container = itemView.container;
            binding = itemView;
        }

        public void bind(Movie movie) {
//            tvTitle.setText(movie.getTitle());
//            tvOverview.setText(movie.getOverview());
            binding.setMovie(movie);


//            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
//                IMAGE_URL = movie.getBackdropPath();
//            }else {
//                IMAGE_URL = movie.getPosterPath();
//            }


            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(i);
                }
            });
        }
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder{
        RelativeLayout container;
        PopularMovieBinding binding;
        ImageView ivPoster;

        public ViewHolder1(@NonNull PopularMovieBinding itemView) {
            super(itemView.getRoot());

            ivPoster = itemView.imageView;
            container = itemView.container;

            binding = itemView;
        }
        public void bind(Movie movie) {
            binding.setMovie(movie);

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movie));
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, ivPoster, "profile");
                            context.startActivity(i, options.toBundle());
                }
            });
        }

    }

    public static class BindingAdapterUtils {
        @BindingAdapter({"morepop"})
        public static void loadImage(ImageView view, String url) {
            Glide.with(context).load(url).placeholder(R.drawable.image_not_found).transform(new RoundedCorners(50)).into(view);
        }
    }

    public static class BindingAdapterUtils2 {
        @BindingAdapter({"lessp"})
        public static void loadImage(ImageView view, String url) {
            Glide.with(context).load(url).placeholder(R.drawable.image_not_found).transform(new RoundedCorners(50)).into(view);
        }
    }
}
