package com.robustastudio.robustivityapp;

import android.database.DefaultDatabaseErrorHandler;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MALAK SHAKER on 4/28/2018.
 */

// Since this is an object collection, use a FragmentStatePagerAdapter,
// and NOT a FragmentPagerAdapter.
public class Tabs_Adapter extends FragmentStatePagerAdapter {

    private final List<Fragment> mFragments = new ArrayList<Fragment>();
    public Tabs_Adapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {

        return mFragments.get(i);

    }

    public void addFragment(Fragment fragment) {
        mFragments.add(fragment);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0:return "Reminders";
            case 1:return "Shortcuts";
        }
        return null;
    }
}
