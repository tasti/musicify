package com.zakarie.musicify.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.zakarie.musicify.R;
import com.zakarie.musicify.activity.ArtistActivity;
import com.zakarie.musicify.adapter.GenreAdapter;
import com.zakarie.musicify.adapter.TrackAdapter;
import com.zakarie.musicify.api.MusicifyService;
import com.zakarie.musicify.api.response.GetGenresResponse;
import com.zakarie.musicify.api.response.GetTracksResponse;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TracksFragment extends Fragment {

    @InjectView(R.id.tracks) ListView tracks;

    private TrackAdapter adapter;

    private MusicifyService musicify;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tracks, container, false);

        //ButterKnife.inject(getActivity(), rootView);
        tracks = (ListView) rootView.findViewById(R.id.tracks);
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(MusicifyService.MUSICIFY_API)
                .build();
        musicify = restAdapter.create(MusicifyService.class);

        getTracks();

        return rootView;
    }

    private void getTracks() {
        musicify.getTracks(((ArtistActivity) getActivity()).artistSpotifyId, new Callback<GetTracksResponse>() {
            @Override
            public void success(GetTracksResponse getTracksResponse, Response response) {
                if (getTracksResponse.success) {
                    adapter = new TrackAdapter(getActivity().getApplicationContext(), R.layout.listview_genre, getTracksResponse.items);
                    //tracks.setOnItemClickListener(tracksOnItemClickListener);
                    tracks.setAdapter(adapter);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), getTracksResponse.message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getActivity().getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
