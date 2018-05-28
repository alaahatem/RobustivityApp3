package com.robustastudio.robustivityapp;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.robustastudio.robustivityapp.Adapters.ActivityAdapter;
import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.Models.Activities;

import java.util.List;

import static com.robustastudio.robustivityapp.HomeActivity.db;

/**
 * Created by MALAK SHAKER on 5/5/2018.
 */

public class NewsFeed extends Fragment  {

    RecyclerView recyclerView;
    public ActivityAdapter adapter;
    List<Activities> activities;
    public AppDatabase db = null;
    private DatabaseReference mDatabaseRef;
    private SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(
                R.layout.activity_feed, container, false);


        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        recyclerView =rootView.findViewById(R.id.recycler_view_activity);

        db = Room.databaseBuilder(getActivity().getApplicationContext(),AppDatabase.class,"robustivity").allowMainThreadQueries().build();

        activities =db.activitiesDao().getAllActivities();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        adapter = new ActivityAdapter(activities,getActivity().getApplicationContext(),db);
        recyclerView.setAdapter(adapter);


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}
