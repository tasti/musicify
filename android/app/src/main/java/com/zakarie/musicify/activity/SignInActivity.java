package com.zakarie.musicify.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.zakarie.musicify.api.GetUserResponse;
import com.zakarie.musicify.util.Helper;
import com.zakarie.musicify.api.MusicifyResponse;
import com.zakarie.musicify.api.MusicifyService;
import com.zakarie.musicify.R;
import com.zakarie.musicify.util.Session;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SignInActivity extends Activity {

    @InjectView(R.id.sign_in_username) EditText signInUsername;

    private MusicifyService musicify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        ButterKnife.inject(this);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(MusicifyService.MUSICIFY_API)
                .build();

        musicify = restAdapter.create(MusicifyService.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sign_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void signIn(final View view) {
        view.setEnabled(false);

        final String username = signInUsername.getText().toString();

        if (!Helper.isValidUsername(username)) {
            Toast.makeText(getApplicationContext(), "Please enter a valid username", Toast.LENGTH_SHORT).show();
            return;
        }

        musicify.getUser(username, new Callback<GetUserResponse>() {
            @Override
            public void success(GetUserResponse getUserResponse, Response response) {
                if (getUserResponse.success) {
                    Session.signIn(getUserResponse.item.username, getApplicationContext());

                    Intent intent = new Intent(getApplicationContext(), RecommendedArtistsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), getUserResponse.message, Toast.LENGTH_LONG).show();

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
