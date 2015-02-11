package com.zakarie.musicify.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.zakarie.musicify.R;
import com.zakarie.musicify.adapter.SuggestionAdapter;
import com.zakarie.musicify.api.response.GetSuggestionsResponse;
import com.zakarie.musicify.api.response.GetUserResponse;
import com.zakarie.musicify.api.response.MusicifyResponse;
import com.zakarie.musicify.api.MusicifyService;
import com.zakarie.musicify.api.request.PutUserSuggestionRequest;
import com.zakarie.musicify.api.object.Suggestion;
import com.zakarie.musicify.util.Session;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class RecommendedArtistsActivity extends Activity {

    public final static String EXTRA_ARTIST_NAME = "com.zakarie.musicify.ARTIST_NAME";
    public final static String EXTRA_ARTIST_SPOTIFY_ID = "com.zakarie.musicify.ARTIST_SPOTIFY_ID";
    public final static String EXTRA_ARTIST_IMAGE_URL = "com.zakarie.musicify.ARTIST_IMAGE_URL";

    @InjectView(R.id.raa_progress_bar) ProgressBar progressBar;
    @InjectView(R.id.suggestions) ListView suggestions;

    private SuggestionAdapter adapter;

    private MusicifyService musicify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended_artists);

        ButterKnife.inject(this);
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(MusicifyService.MUSICIFY_API)
                .build();
        musicify = restAdapter.create(MusicifyService.class);

        getUser();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recommended_artists, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_sign_out) {
            Session.signOut(this);

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getUser() {
        musicify.getUser(Session.getCurrentUser(this), new Callback<GetUserResponse>() {
            @Override
            public void success(GetUserResponse getUserResponse, Response response) {
                if (getUserResponse.success) {
                    if (getUserResponse.item.genre.equals("")) {
                        Intent intent = new Intent(getApplicationContext(), GenreActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        getSuggestions();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), getUserResponse.message, Toast.LENGTH_LONG).show();

                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void getSuggestions() {
        musicify.getSuggestions(Session.getCurrentUser(this), new Callback<GetSuggestionsResponse>() {
            @Override
            public void success(GetSuggestionsResponse getSuggestionsResponse, Response response) {
                if (getSuggestionsResponse.success) {
                    adapter = new SuggestionAdapter(getApplicationContext(), R.layout.listview_suggestion, getSuggestionsResponse.items);
                    suggestions.setOnItemClickListener(suggestionsOnItemClickListener);
                    suggestions.setAdapter(adapter);

                    progressBar.setVisibility(View.GONE);
                } else {
                    Toast.makeText(getApplicationContext(), getSuggestionsResponse.message, Toast.LENGTH_LONG).show();

                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private AdapterView.OnItemClickListener suggestionsOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final Suggestion suggestion = adapter.getItem(position);

            Intent intent = new Intent(getApplicationContext(), ArtistActivity.class);
            intent.putExtra(EXTRA_ARTIST_NAME, suggestion.artist.name);
            intent.putExtra(EXTRA_ARTIST_SPOTIFY_ID, suggestion.artist.spotify_id);
            intent.putExtra(EXTRA_ARTIST_IMAGE_URL, suggestion.artist.image_url_medium);
            startActivity(intent);
        }
    };

}
