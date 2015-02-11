package com.zakarie.musicify.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zakarie.musicify.R;
import com.zakarie.musicify.activity.ArtistActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class InfoFragment extends Fragment {

    @InjectView(R.id.artist_name) TextView artistName;
    @InjectView(R.id.artist_image) ImageView artistImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_info, container, false);

        //ButterKnife.inject(getActivity(), rootView);
        artistName = (TextView) rootView.findViewById(R.id.artist_name);
        artistName.setText(((ArtistActivity) getActivity()).artistsName);

        artistImage = (ImageView) rootView.findViewById(R.id.artist_image);
        Picasso.with(getActivity().getApplicationContext())
                .load(((ArtistActivity) getActivity())
                .artistImageURL).into(artistImage);

        return rootView;
    }

}
