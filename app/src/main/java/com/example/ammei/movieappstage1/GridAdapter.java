package com.example.ammei.movieappstage1;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

/**
 * Created by ammei on 7/7/2017.
 */

public class GridAdapter extends ArrayAdapter<GridItem> {
    private Context mContext;
    private int layoutResourceId;
    private ArrayList<GridItem> mGridData = new ArrayList<GridItem>();


    public GridAdapter(Context mContext, int layoutResourceId, ArrayList<GridItem> mGridData) {
        super(mContext, layoutResourceId, mGridData);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.mGridData = mGridData;
    }

    public void setGridData(ArrayList<GridItem> mGridData) {
        this.mGridData = mGridData;
        notifyDataSetChanged();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, viewGroup, false);
            holder = new ViewHolder();
            holder.image = (ImageView) row.findViewById(R.id.display_movie_poster_imageView);
            row.setTag(holder);
        }else{
            holder = (ViewHolder) row.getTag();
        }

        GridItem item = mGridData.get(position);


        Picasso.with(mContext).load("http://i.imgur.com/DvpvklR.png").into((Target) holder.image);
        return row;
    }

    static class ViewHolder{
        ImageView image;
    }
}

