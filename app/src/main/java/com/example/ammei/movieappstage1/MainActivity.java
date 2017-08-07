package com.example.ammei.movieappstage1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ammei.movieappstage1.Utilities.NetworkUtils;
import com.example.ammei.movieappstage1.Utilities.OpenMovieJsonUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final static String LOG_TAG = MainActivity.class.getSimpleName();

    private static final String MOVIE_DP_TOP_RATED_URL = "http://api.themoviedb.org/3/movie" +
            "/top_rated?api_key=86a31bd15ea90ea230565c86f34b6a13";

    private static final String MOVIE_POPULAR_DB_URL =
            "http://api.themoviedb.org/3/movie/popular?api_key=86a31bd15ea90ea230565c86f34b6a13";  //Put in your api key here.

    private static final String BASE_URL = MOVIE_DP_TOP_RATED_URL + MOVIE_POPULAR_DB_URL;
    /*
     * Global static variables for the user to sort the data to their preferences.
     */
    private static String SORT_ORDER = "sort_order";

    /*
     * GridView object to populate movies to
     */
    private GridView mGridView;
    /*
     * TextView object to display error message to the user when there is no data.
     */
    private TextView mErrorMessageTextView;
    /*
     * ProgressBar to display to the user while their query is loading.
     */
    private ProgressBar mLoadingIndicator;

    private List<Movie> mMovieList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_gridview);

        /*Setting the mErrorMessageTextView to its corresponding View ID. */
        mErrorMessageTextView = (TextView) findViewById(R.id.display_movie_error_message);

        /*Setting the mLoadingIndicator to is corresponding View ID */
        mLoadingIndicator = (ProgressBar) findViewById(R.id.loading_indicator);

        /*Hooking up the GridView to it's corresponding ID. */
        mGridView = (GridView) findViewById(R.id.gridView);

        /*Initializing the GridAdapter. */
        mGridView.setAdapter(new GridAdapter(mGridView.getContext(), (ArrayList<Movie>) mMovieList));

        /*Creating an OnClickListener to listen for when an item is clicked by the user*/
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                /*Once movie item has been clicked, the detailIntent
                  is executed and the DetailActivity is called */
                Intent detailIntent = new Intent(MainActivity.this, DetailActivity.class);
                detailIntent.putExtra("MOVIE_TRANSFER", String.valueOf(mMovieList.get(position)));
                startActivity(detailIntent);

                Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });

        /*Executing the MovieTask*/
        new MovieTask().execute(BASE_URL);
    }

    /*
     *Is responsible for displaying the movie data to the user and populating the GridView while
     *hiding the error message.
     */
    public void showMovieDataView() {
        /* Display movie data to the user */
        mGridView.setVisibility(View.VISIBLE);
        /* Hide error message from the user when there is content */
        mErrorMessageTextView.setVisibility(View.INVISIBLE);
    }

    /*
     *Displays the error message to the user while hiding the GridView when there is no data to
     *populate the views.
     */
    public void showErrorMessage() {
        /* Hide the GridView when there is no data  to display */
        mGridView.setVisibility(View.INVISIBLE);
        /* Display error message to user */
        mErrorMessageTextView.setVisibility(View.VISIBLE);
    }

    private void showMovieData() {
        //  showMovieDataView();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options in the app bar overflow menu
        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // User clicked on a menu option in the app bar overflow.
        switch (item.getItemId()) {
            // Respond to a click on the "Highest Rated" menu option.
            case R.id.action_highest_rating:
                return true;
            // Respond to a click on the "Most Popular" menu option.
            case R.id.action_most_popular:

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class MovieTask extends AsyncTask<String, Void, ArrayList> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Movie> doInBackground(String... params) {

            ArrayList<Movie> movieArrayList = null;

            if (params.length == 0) {
                return null;
            }
            String movies = params[0];
            URL movieRequestUrl = NetworkUtils.buildUrl(movies);

            try {
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);

                movieArrayList = OpenMovieJsonUtils.getSimpleMovieDataStringsFromJson
                        (MainActivity.this, jsonMovieResponse);

                return movieArrayList;

            } catch (Exception e) {
                e.printStackTrace();

            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList movieData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movieData != null) {
                showMovieData();

            } else {
                //  showErrorMessage();
            }

        }
    }
}
