package com.example.ammei.movieappstage1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private final static String LOG_TAG = DetailActivity.class.getSimpleName();

    private String mMovieData;
    private ImageView moviePoster;
    private TextView movieTitle;
    private TextView movieRating;
    private TextView movieReleaseDate;
    private TextView movieSummary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        moviePoster = (ImageView) findViewById(R.id.display_movie_poster_imageView);
        movieTitle = (TextView) findViewById(R.id.title_textview);
        movieRating = (TextView) findViewById(R.id.rating_textview);
        movieReleaseDate = (TextView) findViewById(R.id.release_date_textview);
        movieSummary = (TextView) findViewById(R.id.summary_textview);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null){
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)){
                mMovieData = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
                movieTitle.setText(mMovieData);
                movieRating.setText(mMovieData);
                movieReleaseDate.setText(mMovieData);
                movieSummary.setText(mMovieData);

            }
        }
    }
}
