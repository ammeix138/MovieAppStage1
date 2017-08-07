package com.example.ammei.movieappstage1.Utilities;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by ammei on 7/11/2017.
 */

public final class NetworkUtils {

    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    private static final String MOVIE_DB_URL =
            "http://api.themoviedb.org/3/movie/popular?api_key="; //Put your api key here

    /**
     * Returns a new URL object from the given string URL.
     *
     * @param movieQuery
     * @return
     */
    public static URL buildUrl(String movieQuery) {
        Uri builtUri = Uri.parse(MOVIE_DB_URL).buildUpon()
                .scheme("http")
                .authority("api.themoviedb.org")
                .appendPath("movie")
                .appendPath("popular")
                .appendPath("topRated")
                .appendQueryParameter("api_key", "") //Api Key here
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(LOG_TAG, "Problem building the URL");

        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        String jsonResponse = "";

        //If response is null than return early
        if (url == null) {
            return jsonResponse;
        }

        //Actual HTTP Request being made.
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000/*Time in milliseconds*/);
            urlConnection.setConnectTimeout(15000/*Time in milliseconds*/);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            //If the request was successful (response code 200)
            //then reade the input stream and parse the response.
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code" + urlConnection.getResponseCode());
            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving your movie results");
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            if (inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }

        return output.toString();
    }
}
