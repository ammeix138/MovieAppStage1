package com.example.ammei.movieappstage1;

/**
 * Created by ammei on 7/19/2017.
 */

public class GridItem {

    public final static int NO_IMAGE_AVAILABLE = -1;

    private String mMovieImage;
    private String mMovieTitle;
    private String mMovieRelease;
    private String mMovieRating;
    private String mMovieSummary;

    public GridItem(String movieImage, String movieTitle, String movieRelease, String movieRating, String movieSummary) {
        mMovieImage = movieImage;
        mMovieTitle = movieTitle;
        mMovieRelease = movieRelease;
        mMovieRating = movieRating;
        mMovieSummary = movieSummary;
    }

    public String getImage() {
        return mMovieImage;
    }

    public String getTitle() {
        return mMovieTitle;
    }

    public void setTitle(String title){
        this.mMovieTitle = title;
    }

    public void setImage(String image){
        this.mMovieImage = image;
    }

    public String getRelease() {
        return mMovieRelease;
    }

    public String getRating() {
        return mMovieRating;
    }

    public String getSummary() {
        return mMovieSummary;
    }

}
