package com.robustastudio.robustivityapp;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.robustastudio.robustivityapp.Models.Projects;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by MALAK SHAKER on 3/28/2018.
 */


public class viewProjects extends AppCompatActivity {
    Button addproj ;
    private List<String> projects;
    // String accountname ;
    Projects proj ;



    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_projects);
        addproj =findViewById(R.id.btnAddProject);
        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity").allowMainThreadQueries().build();
        projects  = db.userDao().getAllProjects();
        // Bundle extras = getIntent().getExtras();
        //if (extras != null) {
         //   accountname = extras.getString("accountName");
        List<String> m = new ArrayList<>();
        m.add("malak");
        m.add("mostafa");
        m.add("mohamed");
        m.add("menna");

        proj = new Projects("Malak", "Fun",m, new Date(3918,2,10), new Date(18,2,10), "whatever", "bla bla", "bla", 20.0);

        viewProjects();
        addproj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.userDao().insertAllProjects(proj);
                Toast.makeText(getApplicationContext(),"Done ya baby"+proj.getName()+proj.getEngagement().get(1)+proj.getStartDate(),Toast.LENGTH_LONG).show();
            }
        });

       // }

    }

        public void viewProjects(){


            if(projects.isEmpty()){

                    Toast.makeText(getApplicationContext(),"No Items",Toast.LENGTH_LONG).show();

            }else{
                //Toast.makeText(getApplicationContext(),"fy"+projects.get(1),Toast.LENGTH_LONG).show();
                mRecyclerView = findViewById(R.id.projectsList);

               // mAdapter = new ViewProjectsAdapter(projects,getApplicationContext());
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setHasFixedSize(true);
                mAdapter = new ProjectsAdapter(projects,getApplicationContext());
                mRecyclerView.setAdapter(mAdapter);
            }

        }




}
