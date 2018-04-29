package com.robustastudio.robustivityapp;

import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;

public class CollectionDemoActivity extends AppCompatActivity {
    // When requested, this adapter returns a Reminders_fragment,
    // representing an object in the collection.
    Tabs_Adapter Tabs_Adapter;
    ViewPager mViewPager;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);


     /*   ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);*/


        Reminders_fragment fragmentOne = new Reminders_fragment();
        Shortcuts_fragment fragmentTwo= new Shortcuts_fragment();

        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        Tabs_Adapter =
                new Tabs_Adapter(
                        getSupportFragmentManager());
        Tabs_Adapter.addFragment(fragmentOne);
        Tabs_Adapter.addFragment(fragmentTwo);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(Tabs_Adapter);





    }
}


