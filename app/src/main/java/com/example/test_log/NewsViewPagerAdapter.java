package com.example.test_log;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class NewsViewPagerAdapter extends FragmentStatePagerAdapter {

    public NewsViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }



    @NonNull
    @Override
    public Fragment getItem(int position) {

        Fragment fragment = new WebViewFragment();
        Bundle args = new Bundle();
        //our ob is an integer
        args.putInt(WebViewFragment.ARG_OBJECT, position +1);

        return fragment;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public CharSequence getPageTitle(int position){
        return "OBJECT " + (position +1);
    }
}
