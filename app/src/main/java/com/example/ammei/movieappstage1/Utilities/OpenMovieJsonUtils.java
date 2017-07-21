package com.example.ammei.movieappstage1.Utilities;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

/**
 * Created by ammei on 7/11/2017.
 */

public class OpenMovieJsonUtils {

    private static final String LOG_TAG = OpenMovieJsonUtils.class.getSimpleName();

    public static String[] getSimpleMovieDataStringsFromJson
            (Context context, String movieJsonString) throws JSONException {


        final String MOVIE_MESSAGE_CODE = "cod";

        /* ArrayList to hold each movies data */
        String[] parsedMovieData = null;

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

            JSONArray movieArray = baseJsonResponse.getJSONArray("results");

            parsedMovieData = new String[movieArray.length()];

            for (int i = 0; i < movieArray.length(); i++) {
            /* Get the JSONObject representing the current movie */
                JSONObject currentMovie = movieArray.getJSONObject(i);

            /* These are the values that will be parsed */
                String movieTitle = currentMovie.getString("title");
                String movieReleaseDate = currentMovie.getString("release_date");
                String moviePoster = currentMovie.getString("poster_path");
                String movieSummary = currentMovie.getString("overview");
                Double movieRating = currentMovie.getDouble("vote_average");

                parsedMovieData[i] = movieTitle + " - " + movieReleaseDate + " - "
                        + " - " + moviePoster + " - " + movieSummary + " - " + movieRating;

            }

        } catch (JSONException jsone) {
            Log.e("OpenMovieJsonUtils", "Problem parsing the Movie JSON results", jsone);
        }

        return parsedMovieData;
    }
}
