package com.marcopololeyva.cinemanice.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class PagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public PagerAdapter(FragmentManager manager) {
        super(manager);
    }

    public void setItem(int position, Fragment element) {
        mFragmentList.set(position, element);
    }


    @Override
    public Fragment getItem(int position) {
        Log.e("OkHttp", "==============================>>  POSITION IN FRAGMENT:     "+ position );
        return mFragmentList.get(position);
    }

/*
    @Override
    public Fragment getItem(int position) {
        Log.e("OkHttp", "==============================>>  POSITION IN FRAGMENT:     "+ position );
        switch (position){
            case 0:
                PopularFragment tab1=new PopularFragment();
                return tab1;
            case 1:
                TopRatedFragment tab2=new TopRatedFragment();
                return tab2;
            case 2:
                UpcomingFragment tab3=new UpcomingFragment();
                return tab3;
        }
        return null;
    }
*/




    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

}