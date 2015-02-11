package com.zakarie.musicify.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.zakarie.musicify.R;
import com.zakarie.musicify.adapter.ArtistPagerAdapter;
import com.zakarie.musicify.api.MusicifyService;
import com.zakarie.musicify.api.request.PutUserSuggestionRequest;
import com.zakarie.musicify.api.response.MusicifyResponse;
import com.zakarie.musicify.fragment.InfoFragment;
import com.zakarie.musicify.fragment.RelatedFragment;
import com.zakarie.musicify.fragment.TracksFragment;
import com.zakarie.musicify.util.Session;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ArtistActivity extends FragmentActivity {

    @InjectView(R.id.artists_pager) ViewPager artistPager;

    ArtistPagerAdapter pagerAdapter;

    public String artistsName;
    public String artistSpotifyId;
    public String artistImageURL;

    private MusicifyService musicify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);

        Intent intent = getIntent();
        artistsName = intent.getStringExtra(RecommendedArtistsActivity.EXTRA_ARTIST_NAME);
        artistSpotifyId = intent.getStringExtra(RecommendedArtistsActivity.EXTRA_ARTIST_SPOTIFY_ID);
        artistImageURL = intent.getStringExtra(RecommendedArtistsActivity.EXTRA_ARTIST_IMAGE_URL);

        // Label the activity as the artists name
        setTitle(artistsName);

        ButterKnife.inject(this);
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(MusicifyService.MUSICIFY_API)
                .build();
        musicify = restAdapter.create(MusicifyService.class);

        setupFragments();
        putSuggestion();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_artist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    private void setupFragments() {
        final ActionBar actionBar = getActionBar();

        pagerAdapter = new ArtistPagerAdapter(getSupportFragmentManager());
        artistPager.setAdapter(pagerAdapter);
        artistPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });
        artistPager.setOffscreenPageLimit(pagerAdapter.getCount() - 1); // Stops page from destroying when off screen

        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create a tab listener that is called when the user changes tabs.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                // show the given tab
                artistPager.setCurrentItem(tab.getPosition());
            }

            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {}

            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {}
        };

        // Add 3 tabs, specifying the tab's text and TabListener
        for (int i = 0; i < 3; i++) {
            actionBar.addTab(actionBar.newTab().setText(pagerAdapter.getPageTitle(i)).setTabListener(tabListener));
        }
    }

    private void putSuggestion() {
        musicify.putSuggestion(Session.getCurrentUser(this),
                new PutUserSuggestionRequest(artistSpotifyId), new Callback<MusicifyResponse>() {
            @Override
            public void success(MusicifyResponse musicifyResponse, Response response) {
                Toast.makeText(getApplicationContext(), musicifyResponse.message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
