package com.example.favoritecatalogue.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.favoritecatalogue.R;
import com.example.favoritecatalogue.parcelable.TvItems;

public class TvDetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";
    TextView tvName, tvScore, tvDate, tvOverview;
    ImageView imgPhoto;
    TvItems tvItems;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_detail);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        tvItems = getIntent().getParcelableExtra(EXTRA_MOVIE);
        actionBar.setTitle(tvItems.getName());

        tvName = findViewById(R.id.tv_judul);
        tvScore = findViewById(R.id.tv_score);
        tvDate = findViewById(R.id.tv_date);
        tvOverview = findViewById(R.id.tv_overview);
        imgPhoto = findViewById(R.id.iv_poster);
        tvName.setText(tvItems.getName());
        tvScore.setText(String.format("%s: %s", getString(R.string.score), tvItems.getScore()));
        tvDate.setText(tvItems.getDate());
        tvOverview.setText(tvItems.getOverview());

        String imageUrl = "https://image.tmdb.org/t/p/w780/";
        Glide.with(this)
                .load(imageUrl + tvItems.getPhoto())
                .transform(new RoundedCorners(45))
                .into(imgPhoto);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        onBackPressed();
        return true;
    }
}
