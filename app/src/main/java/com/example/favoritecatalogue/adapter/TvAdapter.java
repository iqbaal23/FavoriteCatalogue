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
import com.example.favoritecatalogue.activity.TvDetailActivity;
import com.example.favoritecatalogue.parcelable.TvItems;

import java.util.ArrayList;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.TvViewHolder> {
    private final ArrayList<TvItems> tvItems = new ArrayList<>();
    private final Activity activity;

    public TvAdapter(Activity activity) {
        this.activity = activity;
    }

    private ArrayList<TvItems> getTvItems(){
        return tvItems;
    }

    public void setListTv(ArrayList<TvItems> tvItems){
        this.tvItems.clear();
        this.tvItems.addAll(tvItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TvViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_movie,viewGroup,false);
        return new TvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TvViewHolder tvViewHolder, int i) {
        tvViewHolder.textViewJudul.setText(getTvItems().get(i).getName());
        tvViewHolder.textViewScore.setText(getTvItems().get(i).getScore());
        String imageUrl = "https://image.tmdb.org/t/p/w780/";
        Glide.with(activity.getApplicationContext())
                .load(imageUrl + getTvItems().get(i).getPhoto())
                .transform(new RoundedCorners(45))
                .into(tvViewHolder.imgPhoto);

        final int id = getTvItems().get(i).getId();
        final String judul = getTvItems().get(i).getName();
        final String score = getTvItems().get(i).getScore();
        final String date = getTvItems().get(i).getDate();
        final String overview = getTvItems().get(i).getOverview();
        final String photo = getTvItems().get(i).getPhoto();

        tvViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(tvViewHolder.itemView.getContext(), TvDetailActivity.class);
                TvItems tv = new TvItems();
                tv.setId(id);
                tv.setName(judul);
                tv.setScore(score);
                tv.setDate(date);
                tv.setOverview(overview);
                tv.setPhoto(photo);

                intent.putExtra(TvDetailActivity.EXTRA_MOVIE, tv);
                tvViewHolder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("tv", "tv : " + tvItems.size());
        return tvItems.size();
    }

    public class TvViewHolder extends RecyclerView.ViewHolder {
        TextView textViewJudul, textViewScore;
        ImageView imgPhoto;

        public TvViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewJudul = itemView.findViewById(R.id.tv_judul);
            textViewScore = itemView.findViewById(R.id.tv_score);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
        }
    }
}
