package com.robustastudio.robustivityapp;

import android.support.v4.app.Fragment;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class NewsFeed extends Fragment {

    RecyclerView recyclerView;
    public ActivityAdapter adapter;
    List<Activities> activities;
    public AppDatabase db = null;
    private DatabaseReference mDatabaseRef;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(
                R.layout.activity_feed, container, false);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        recyclerView =rootView.findViewById(R.id.recycler_view_account);
        db = Room.databaseBuilder(getActivity().getApplicationContext(),AppDatabase.class,"robustivity").allowMainThreadQueries().build();

        activities =db.activitiesDao().getAllActivities();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ActivityAdapter(activities,getActivity().getApplicationContext(),db);
        recyclerView.setAdapter(adapter);


        return rootView;
    }

}
