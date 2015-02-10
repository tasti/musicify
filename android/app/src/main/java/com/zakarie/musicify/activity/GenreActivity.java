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
import com.zakarie.musicify.adapter.GenreAdapter;
import com.zakarie.musicify.api.GetGenresResponse;
import com.zakarie.musicify.api.MusicifyResponse;
import com.zakarie.musicify.api.MusicifyService;
import com.zakarie.musicify.api.PutUserGenreRequest;
import com.zakarie.musicify.util.Session;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class GenreActivity extends Activity {

    @InjectView(R.id.ga_progress_bar) ProgressBar progressBar;
    @InjectView(R.id.genres) ListView genres;

    private GenreAdapter adapter;

    private MusicifyService musicify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);

        ButterKnife.inject(this);
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(MusicifyService.MUSICIFY_API)
                .build();
        musicify = restAdapter.create(MusicifyService.class);

        getGenres();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_genre, menu);
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

    private void getGenres() {
        musicify.getGenres(new Callback<GetGenresResponse>() {
            @Override
            public void success(GetGenresResponse getGenresResponse, Response response) {
                if (getGenresResponse.success) {
                    adapter = new GenreAdapter(getApplicationContext(), R.layout.listview_genre, getGenresResponse.items);
                    genres.setOnItemClickListener(genresOnItemClickListener);
                    genres.setAdapter(adapter);

                    progressBar.setVisibility(View.GONE);
                } else {
                    Toast.makeText(getApplicationContext(), getGenresResponse.message, Toast.LENGTH_LONG).show();

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

    private AdapterView.OnItemClickListener genresOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            genres.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);

            final String genre = adapter.getItem(position).name;

            musicify.putUserGenre(Session.getCurrentUser(getApplicationContext()), new PutUserGenreRequest(genre), new Callback<MusicifyResponse>() {
                @Override
                public void success(MusicifyResponse musicifyResponse, Response response) {
                    if (musicifyResponse.success) {
                        Toast.makeText(getApplicationContext(), musicifyResponse.message, Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(getApplicationContext(), RecommendedArtistsActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), musicifyResponse.message, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    };

}
