package com.robustastudio.robustivityapp;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.robustastudio.robustivityapp.Models.UserProfile;

import java.util.List;

public class createProfile extends AppCompatActivity {
    private static final String TAG = "createProfile";
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    FloatingActionButton fab;
//    ArrayList<UserProfile> user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView =findViewById(R.id.recycler_view);
//        user= new ArrayList<UserProfile>();
//        for (int i = 0; i <10 ; i++) {
//            UserProfile userprofile = new UserProfile("Alaa"+""+i,"0100038293","kurdianos@live.com");
//            user.add(userprofile);
//
//        }
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity")
                .allowMainThreadQueries().build();

        List<UserProfile> userprofiles= db.userDao().getAllprofiles();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdapter(userprofiles);
        recyclerView.setAdapter(adapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(createProfile.this, createuserprof.class);
                createProfile.this.startActivity(myIntent);

            }
        });
    }

}
