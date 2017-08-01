package com.example.ammei.movieappstage1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ammei on 7/7/2017.
 */

public class GridAdapter extends BaseAdapter {

    int listOfMovies = 0;
    private List<Movie> mMovieList = new ArrayList<>();
    private Context mContext;

    public GridAdapter(Context context, ArrayList<Movie> movies) {
        this.mContext = context;
        this.mMovieList = movies;

    }

    @Override
    public int getCount() {
        if (mMovieList != null) {
            listOfMovies = mMovieList.size();
        }
        return listOfMovies;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        //Creating a new view to inflate with data.
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.movie_list_item, viewGroup, false);
        }

        ImageView imageView;
        //Checking to see if Image is null
        if (convertView == null) {
            //If image is not null, set the image with these layout parameters.
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;

        }

        Movie movie = mMovieList.get(position);
        String imageUrl = movie.getMoviePoster();
        //Using the Picasso  library to populate imageView with corresponding movie.
        Picasso.with(mContext).load(imageUrl + mMovieList.get(position)
                .getMoviePoster()).into(imageView);

        return imageView;
    }
}


