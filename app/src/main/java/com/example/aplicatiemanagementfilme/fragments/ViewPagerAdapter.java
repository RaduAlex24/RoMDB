package com.example.aplicatiemanagementfilme.fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private static final int NUM_INTEMS = 3;

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new MovieBrowserFragment();
            case 1: return new WatchListFragment();
            default: return new ProfileFragment();
        }
    }

    @Override
    public int getCount() {
        return NUM_INTEMS;
    }
}

