package com.zakarie.musicify.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zakarie.musicify.R;
import com.zakarie.musicify.api.object.Genre;

import java.util.List;

public class GenreAdapter extends ArrayAdapter<Genre> {

    private Context context;
    private int layout;

    private List<Genre> genres;

    public GenreAdapter(Context context, int layout, List<Genre> genres) {
        super(context, layout, genres);

        this.context = context;
        this.layout = layout;
        this.genres = genres;
    }

    @Override
    public View getView(int position, View genreView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        if (genreView == null) {
            genreView = LayoutInflater.from(context).inflate(layout, parent, false);
        }

        Genre genre = genres.get(position);

        TextView type = (TextView) genreView.findViewById(R.id.genre);
        type.setText(genre.name);

        return genreView;
    }

}
