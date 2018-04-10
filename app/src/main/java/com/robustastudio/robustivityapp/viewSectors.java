package com.robustastudio.robustivityapp;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.List;

/**
 * Created by MALAK SHAKER on 3/29/2018.
 */

public class viewSectors extends AppCompatActivity {


    Button AddSector ;
    List<String> sectors;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_sectors);

        AddSector = (Button) findViewById(R.id.buttonSector);
       // AddSector = () findViewById(R.id.sectors_list);

        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity").allowMainThreadQueries().build();
        sectors  = db.userDao().getAllSectors();

        mRecyclerView = findViewById(R.id.sectors_list);

        // mAdapter = new ViewProjectsAdapter(projects,getApplicationContext());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new ProjectsAdapter(sectors,getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);

        AddSector.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                startActivity(new Intent(viewSectors.this,popAddSector.class));
            }
        });

    }

}
