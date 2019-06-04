package com.arturarzumanyan.livejournalparser;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Post> {

    private final Activity context;
    //список постов
    ArrayList<Post> posts;

    public ListAdapter(Activity context, ArrayList<Post> posts) {
        super(context, R.layout.list_item, posts);

        this.context = context;
        this.posts = posts;
    }

    //установление контента в элементы ячеек
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_item, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView descriptionView = (TextView) rowView.findViewById(R.id.textView2);

        txtTitle.setText(posts.get(position).getPostName());
        Uri uri = Uri.parse(posts.get(position).getPostImgUrl());
        Picasso.with(context).load(uri).into(imageView);
        descriptionView.setText(posts.get(position).getPostDescription());

        return rowView;
    }
}
