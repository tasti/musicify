package com.zakarie.musicify.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zakarie.musicify.R;
import com.zakarie.musicify.api.object.Artist;
import com.zakarie.musicify.api.object.Track;

import java.util.List;

public class TrackAdapter extends ArrayAdapter<Track> {

    private Context context;
    private int layout;

    private List<Track> tracks;

    public TrackAdapter(Context context, int layout, List<Track> tracks) {
        super(context, layout, tracks);

        this.context = context;
        this.layout = layout;
        this.tracks = tracks;
    }

    @Override
    public View getView(int position, View artistView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        if (artistView == null) {
            artistView = LayoutInflater.from(context).inflate(layout, parent, false);
        }

        Track track = tracks.get(position);

        TextView type = (TextView) artistView.findViewById(R.id.genre);
        type.setText(track.name);

        return artistView;
    }

}
