package com.zakarie.musicify.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zakarie.musicify.R;
import com.zakarie.musicify.api.Artist;
import com.zakarie.musicify.api.Genre;

import java.util.List;

public class ArtistAdapter extends ArrayAdapter<Artist> {

    private Context context;
    private int layout;

    private List<Artist> artists;

    public ArtistAdapter(Context context, int layout, List<Artist> artists) {
        super(context, layout, artists);

        this.context = context;
        this.layout = layout;
        this.artists = artists;
    }

    @Override
    public View getView(int position, View artistView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        if (artistView == null) {
            artistView = LayoutInflater.from(context).inflate(layout, parent, false);
        }

        Artist artist = artists.get(position);

        TextView type = (TextView) artistView.findViewById(R.id.genre);
        type.setText(artist.name);

        return artistView;
    }

}
