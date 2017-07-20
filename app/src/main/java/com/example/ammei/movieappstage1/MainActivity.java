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

public class MainActivity extends AppCompatActivity {

    private final static String LOG_TAG = MainActivity.class.getSimpleName();

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

    /*
     * Adapter linking the movie data with the correct views that will display the data.
     */
    private GridAdapter mMovieAdapter;

    /*
     * ArrayList to populate data
     */
    private ArrayList<GridItem> mGridData;

    /*
     * Global variables for the user to sort the data to their preferences.
     */
    private static String SORT_ORDER = "sort_order";
    private static final int SORT_ORDER_POPULAR = 0;
    private static final int SORT_ORDER_TOP_RATED = 1;

    private static final String MOVIE_DB_URL =
            "http://api.themoviedb.org/3/movie/popular?api_key="; //Put in your api key here.

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

        //Initialize with empty data.
        mGridData = new ArrayList<>();
        mMovieAdapter = new GridAdapter(this, R.layout.movie_list_item, mGridData);
        /*Initializing the GridAdapter. */
        mGridView.setAdapter(mMovieAdapter);

        /*Creating an OnClickListener to listen for when an item is clicked by the user*/
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                /*Once movie item has been clicked, the detailIntent
                  is executed and the DetailActivity is called */
                Intent detailIntent = new Intent(MainActivity.this, DetailActivity.class);
                startActivity(detailIntent);

                Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });

        /*Executing the MovieTask*/
        new MovieTask().execute(MOVIE_DB_URL);
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
        showMovieDataView();

    }

    public class MovieTask extends AsyncTask<String, Void, ArrayList<GridItem>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<GridItem> doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }
            String movies = params[0];
            URL movieRequestUrl = NetworkUtils.buildUrl(movies);

            try {
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);

                ArrayList<GridItem> simpleJsonMovieData = OpenMovieJsonUtils.getSimpleMovieDataStringsFromJson
                        (MainActivity.this, jsonMovieResponse);

                return simpleJsonMovieData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList movieData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movieData != null) {
                showMovieData();

            } else {
                showErrorMessage();
            }
            super.onPostExecute(movieData);
        }
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
}
