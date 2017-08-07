package com.example.ammei.movieappstage1;

/**
 * Created by ammei on 7/25/2017.
 */

public class Movie {

    private String mMovieTitle;
    private String mMovieRelease;
    private String mMoviePoster;
    private String mMovieSummary;
    private String mMovieRating;

    public Movie(String movieTitle, String movieRelease, String moviePoster, String movieSummary,
                 String movieRating) {

        mMovieTitle = movieTitle;
        mMovieRelease = movieRelease;
        mMoviePoster = moviePoster;
        mMovieSummary = movieSummary;
        mMovieRating = movieRating;

    }

    public String getMovieTitle() {
        return mMovieTitle;
    }

    public String getMovieRelease() {
        return mMovieRelease;
    }

    public String getMoviePoster() {
        mMoviePoster = "http://image.tmdb.org/t/p/w185" + mMoviePoster;
        return mMoviePoster;
    }

    public String getMovieSummary() {
        return mMovieSummary;
    }

    public String getMovieRating() {
        return mMovieRating;
    }

}
