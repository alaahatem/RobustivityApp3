package com.robustastudio.robustivityapp.ActivityFeed;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.robustastudio.robustivityapp.Adapters.ActivityAdapter;
import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.Models.Activities;
import com.robustastudio.robustivityapp.R;

import java.util.List;

public class ActivityFeed extends AppCompatActivity  {
    RecyclerView recyclerView;
    public ActivityAdapter adapter;
    List<Activities> activities;
    public AppDatabase db = null;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        recyclerView =findViewById(R.id.recycler_view_activity);
        db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity").allowMainThreadQueries().build();

        activities =db.activitiesDao().getAllActivities();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ActivityAdapter(activities,getApplicationContext(),db);
        recyclerView.setAdapter(adapter);
    }


}
