package com.marcopololeyva.cinemanice.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;
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
        return mFragmentList.get(position);
    }


    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
    }




    @Override
    public int getCount() {
        return mFragmentList.size();
     //   return 3;
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