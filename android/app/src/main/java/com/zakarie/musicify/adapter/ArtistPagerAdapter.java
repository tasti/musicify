package com.zakarie.musicify.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.zakarie.musicify.fragment.InfoFragment;
import com.zakarie.musicify.fragment.RelatedFragment;
import com.zakarie.musicify.fragment.TracksFragment;

public class ArtistPagerAdapter extends FragmentStatePagerAdapter {

    public ArtistPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new InfoFragment();
            case 1:
                return new TracksFragment();
            case 2:
                return new RelatedFragment();
            default:
                return new InfoFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Info";
            case 1:
                return "Tracks";
            case 2:
                return "Related";
            default:
                return "Info";
        }
    }

}