package com.example.ammei.movieappstage1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by ammei on 7/7/2017.
 */

public class GridAdapter extends BaseAdapter {

    public Integer[] mThumbIds = {
            R.drawable.reel,
            R.drawable.book,
            R.drawable.dread_icon,
            R.drawable.notebook
    };
    LayoutInflater inflater;
    private Context mContext;

    public GridAdapter(Context context) {
        mContext = context;
        inflater = (LayoutInflater.from(mContext));

    }

    @Override
    public int getCount() {

        if (mThumbIds != null) {
            return mThumbIds.length;
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        Picasso.with(mContext).load(mThumbIds[position]).into(imageView);
        return imageView;
    }
}


