package com.zakarie.musicify.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.zakarie.musicify.R;
import com.zakarie.musicify.activity.ArtistActivity;
import com.zakarie.musicify.activity.RecommendedArtistsActivity;
import com.zakarie.musicify.adapter.ArtistAdapter;
import com.zakarie.musicify.adapter.TrackAdapter;
import com.zakarie.musicify.api.MusicifyService;
import com.zakarie.musicify.api.object.Artist;
import com.zakarie.musicify.api.object.Suggestion;
import com.zakarie.musicify.api.request.PutUserSuggestionRequest;
import com.zakarie.musicify.api.response.GetRelatedResponse;
import com.zakarie.musicify.api.response.GetTracksResponse;
import com.zakarie.musicify.api.response.MusicifyResponse;
import com.zakarie.musicify.util.Session;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RelatedFragment extends Fragment {

    @InjectView(R.id.related_artists) ListView relatedArtists;

    private ArtistAdapter adapter;

    private MusicifyService musicify;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_related, container, false);

        //ButterKnife.inject(getActivity(), rootView);
        relatedArtists = (ListView) rootView.findViewById(R.id.related_artists);
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(MusicifyService.MUSICIFY_API)
                .build();
        musicify = restAdapter.create(MusicifyService.class);

        getRelated();

        return rootView;
    }

    private void getRelated() {
        musicify.getRelated(((ArtistActivity) getActivity()).artistSpotifyId, new Callback<GetRelatedResponse>() {
            @Override
            public void success(GetRelatedResponse getRelatedResponse, Response response) {
                if (getRelatedResponse.success) {
                    adapter = new ArtistAdapter(getActivity().getApplicationContext(), R.layout.listview_artist, getRelatedResponse.items);
                    relatedArtists.setOnItemClickListener(relatedArtistsOnItemClickListener);
                    relatedArtists.setAdapter(adapter);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), getRelatedResponse.message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getActivity().getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private AdapterView.OnItemClickListener relatedArtistsOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final Artist artist = adapter.getItem(position);

            Intent intent = new Intent(getActivity().getApplicationContext(), ArtistActivity.class);
            intent.putExtra(RecommendedArtistsActivity.EXTRA_ARTIST_NAME, artist.name);
            intent.putExtra(RecommendedArtistsActivity.EXTRA_ARTIST_SPOTIFY_ID, artist.spotify_id);
            intent.putExtra(RecommendedArtistsActivity.EXTRA_ARTIST_IMAGE_URL, artist.image_url_medium);
            startActivity(intent);
        }
    };

}
