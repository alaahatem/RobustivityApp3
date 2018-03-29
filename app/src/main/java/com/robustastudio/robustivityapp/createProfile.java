package com.robustastudio.robustivityapp;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.robustastudio.robustivityapp.Models.UserProfile;

import java.util.ArrayList;
import java.util.List;

public class createProfile extends AppCompatActivity {
    List<UserProfile> filteredList;
    List<UserProfile> userprofiles;

    private static final String TAG = "createProfile";
    RecyclerView recyclerView;
    public UserAdapter adapter;
    FloatingActionButton fab;

//    ArrayList<UserProfile> user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView =findViewById(R.id.recycler_view);
        EditText search = (EditText)findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });


        AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity").allowMainThreadQueries().build();

       userprofiles= db.userDao().getAllprofiles();
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
    private void filter(String text) {
         filteredList = new ArrayList<>();


        for ( UserProfile item : userprofiles) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);



            }

        }

adapter.filterlist(filteredList);
    }


}
