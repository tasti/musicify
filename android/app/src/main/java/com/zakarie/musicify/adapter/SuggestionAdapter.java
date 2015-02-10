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
import com.zakarie.musicify.api.Suggestion;

import java.util.List;

public class SuggestionAdapter extends ArrayAdapter<Suggestion> {

    private Context context;
    private int layout;

    private List<Suggestion> suggestions;

    public SuggestionAdapter(Context context, int layout, List<Suggestion> suggestions) {
        super(context, layout, suggestions);

        this.context = context;
        this.layout = layout;
        this.suggestions = suggestions;
    }

    @Override
    public View getView(int position, View suggestionView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        if (suggestionView == null) {
            suggestionView = LayoutInflater.from(context).inflate(layout, parent, false);
        }

        Suggestion suggestion = suggestions.get(position);

        TextView type = (TextView) suggestionView.findViewById(R.id.suggestion);
        type.setText(suggestion.artist.name);

        return suggestionView;
    }

}
