package com.example.favoritecatalogue.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.favoritecatalogue.R;
import com.example.favoritecatalogue.activity.MovieDetailActivity;
import com.example.favoritecatalogue.parcelable.MovieItems;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private final ArrayList<MovieItems> movieItems = new ArrayList<>();
    private final Activity activity;

    public MovieAdapter(Activity activity) {
        this.activity = activity;
    }

    private ArrayList<MovieItems> getMovieItems(){
        return movieItems;
    }

    public void setListMovie(ArrayList<MovieItems> movieItems){
        this.movieItems.clear();
        this.movieItems.addAll(movieItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_movie, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder movieViewHolder, final int i) {
        movieViewHolder.textViewJudul.setText(getMovieItems().get(i).getName());
        movieViewHolder.textViewScore.setText(getMovieItems().get(i).getScore());
        String imageUrl = "https://image.tmdb.org/t/p/w780/";
        Glide.with(activity.getApplicationContext())
                .load(imageUrl + getMovieItems().get(i).getPhoto())
                .transform(new RoundedCorners(45))
                .into(movieViewHolder.imgPhoto);

        final int id = getMovieItems().get(i).getId();
        final String judul = getMovieItems().get(i).getName();
        final String score = getMovieItems().get(i).getScore();
        final String date = getMovieItems().get(i).getDate();
        final String overview = getMovieItems().get(i).getOverview();
        final String photo = getMovieItems().get(i).getPhoto();
        movieViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(movieViewHolder.itemView.getContext(), MovieDetailActivity.class);
                MovieItems movie = new MovieItems();
                movie.setId(id);
                movie.setName(judul);
                movie.setScore(score);
                movie.setDate(date);
                movie.setOverview(overview);
                movie.setPhoto(photo);

                intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie);
                movieViewHolder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("movie", "movie : " + movieItems.size());
        return movieItems.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView textViewJudul, textViewScore;
        ImageView imgPhoto;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewJudul = itemView.findViewById(R.id.tv_judul);
            textViewScore = itemView.findViewById(R.id.tv_score);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
        }
    }
}
