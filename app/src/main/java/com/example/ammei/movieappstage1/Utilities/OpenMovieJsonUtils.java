package com.example.ammei.movieappstage1.Utilities;

import android.content.Context;
import android.util.Log;

import com.example.ammei.movieappstage1.GridItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * Created by ammei on 7/11/2017.
 */

public class OpenMovieJsonUtils {

    private static final String LOG_TAG = OpenMovieJsonUtils.class.getSimpleName();

    public static ArrayList<GridItem> getSimpleMovieDataStringsFromJson
            (Context context, String movieJsonString) throws JSONException {


        final String MOVIE_MESSAGE_CODE = "cod";

        /* ArrayList to hold each movies data */
        ArrayList<GridItem> gridItems = new ArrayList<>();

        try {

            JSONObject baseJsonResponse = new JSONObject(movieJsonString);

        /* If there is an error */
            if (baseJsonResponse.has(MOVIE_MESSAGE_CODE)) {
                int errorCode = baseJsonResponse.getInt(MOVIE_MESSAGE_CODE);

                switch (errorCode) {
                    case HttpURLConnection.HTTP_OK:
                        break;
                    case HttpURLConnection.HTTP_NOT_FOUND:
                        return null;
                    default:
                        return null;
                }
            }

            JSONObject results = baseJsonResponse.getJSONObject("page");

            JSONArray movieArray = results.getJSONArray("results");

            for (int i = 0; i < movieArray.length(); i++) {
            /* Get the JSONObject representing the current movie */
                JSONObject currentMovie = movieArray.getJSONObject(i);

            /* These are the values that will be parsed */
                String movieTitle = currentMovie.getString("title");
                String movieReleaseDate = currentMovie.getString("release_date");
                String moviePoster = currentMovie.getString("poster_path");
                String movieSummary = currentMovie.getString("overview");
                String movieRating = currentMovie.getString("vote_average");

                GridItem movieResults = new GridItem(movieTitle, movieReleaseDate, moviePoster, movieSummary, movieRating);
                gridItems.add(movieResults);
            }

        } catch (JSONException jsone) {
            Log.e("OpenMovieJsonUtils", "Problem parsing the Movie JSON results", jsone);
        }

        return gridItems;
    }
}