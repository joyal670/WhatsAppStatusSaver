package com.whatsappstatussaver.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.whatsappstatussaver.R;

import java.io.File;
import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {

    Context context;
    ArrayList<File> list;

    public GridAdapter(Context context, ArrayList<File> list)
    {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.grid_item, null);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageViewitem);

        Glide.with(context)
                .load(getItem(position).toString())
                .into(imageView);


        return convertView;

    }
}
