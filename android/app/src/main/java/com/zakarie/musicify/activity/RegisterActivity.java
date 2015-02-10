package com.zakarie.musicify.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.zakarie.musicify.R;
import com.zakarie.musicify.api.MusicifyResponse;
import com.zakarie.musicify.api.MusicifyService;
import com.zakarie.musicify.api.PostUserRequest;
import com.zakarie.musicify.util.Helper;
import com.zakarie.musicify.util.Session;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RegisterActivity extends Activity {

    @InjectView(R.id.register_username) EditText registerUsername;

    private MusicifyService musicify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.inject(this);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(MusicifyService.MUSICIFY_API)
                .build();

        musicify = restAdapter.create(MusicifyService.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void register(final View view) {
        view.setEnabled(false);

        final String username = registerUsername.getText().toString();

        if (!Helper.isValidUsername(username)) {
            Toast.makeText(getApplicationContext(), "Please enter a valid username", Toast.LENGTH_SHORT).show();
            return;
        }

        musicify.postUser(new PostUserRequest(username), new Callback<MusicifyResponse>() {
            @Override
            public void success(MusicifyResponse musicifyResponse, Response response) {

                if (musicifyResponse.success) {
                    Toast.makeText(getApplicationContext(), musicifyResponse.message, Toast.LENGTH_LONG).show();

                    Session.signIn(username, getApplicationContext());

                    Intent intent = new Intent(getApplicationContext(), RecommendedArtistsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), musicifyResponse.message, Toast.LENGTH_LONG).show();

                    view.setEnabled(true);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                view.setEnabled(true);
            }
        });
    }

}
