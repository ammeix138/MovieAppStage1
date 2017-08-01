package com.example.ammei.movieappstage1.Utilities;

import android.content.Context;
import android.util.Log;

import com.example.ammei.movieappstage1.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ammei on 7/11/2017.
 */

public class OpenMovieJsonUtils {

    private static final String LOG_TAG = OpenMovieJsonUtils.class.getSimpleName();

    /**
     * Returns an ArrayList of movie objects that has been built up from parsing the relevant data.
     *
     * @param context
     * @param movieJsonString
     * @return
     * @throws JSONException
     */
    public static ArrayList<Movie> getSimpleMovieDataStringsFromJson
    (Context context, String movieJsonString) throws JSONException {

        ArrayList<Movie> movieListData = null;
        if (movieJsonString != null) {
            try {
                JSONObject movieDataJson = new JSONObject(movieJsonString);

                JSONArray movieArray = movieDataJson.getJSONArray("results");
                movieListData = new ArrayList<>();
                for (int i = 0; i < movieArray.length(); i++) {
                    JSONObject movieData = movieArray.getJSONObject(i);

                    //Extract the value for the key called "title".
                    String movieTitle;
                    //Checks to ensure the movie title is not null
                    if (movieData.has("title")) {
                        movieTitle = movieData.getString("title");
                    } else
                        movieTitle = "Movie Title Not Available";

                    //Extract the value for the key called "release_date".
                    String movieRelease;
                    //Checks to ensure the movie release date is not null.
                    if (movieData.has("release_date")) {
                        movieRelease = movieData.getString("release_date");
                    } else {
                        movieRelease = "Movie Release Date Unavailable";
                    }
                    //Extract the value for the key called "poster_path".
                    String moviePoster;
                    //Check to ensure the movie has a poster image to display
                    if (movieData.has("poster_path")) {
                        moviePoster = movieData.getString("poster_path");
                    } else {
                        moviePoster = "No Image Available";
                    }

                    //Extract the key for the value called "vote_average".
                    String movieRating;
                    //Check to ensure movie has a rating.
                    if (movieData.has("vote_average")) {
                        movieRating = movieData.getString("vote_average");
                    } else {
                        movieRating = "Rating Unavailable";
                    }

                    //Extract the key for the value called "overview".
                    String movieSummary;
                    //Check to ensure movie possesses an overview.
                    if (movieData.has("overview")) {
                        movieSummary = movieData.getString("overview");
                    } else {
                        movieSummary = "No Description Is Available";
                    }

                    //Create a new List object
                    Movie movieList = new Movie
                            (movieTitle, movieRelease, moviePoster, movieRating, movieSummary);
                    movieListData.add(movieList);
                }
            } catch (JSONException e) {
                //Print to log with a message from the exception
                Log.e(LOG_TAG, "Problem parsing the movie JSON results");
            }
        }

        //Returns a list of movies and the corresponding data.
        return movieListData;
    }
}

