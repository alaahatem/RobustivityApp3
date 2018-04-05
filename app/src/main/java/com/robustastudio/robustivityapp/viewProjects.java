package com.robustastudio.robustivityapp;

<<<<<<< Updated upstream
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
=======
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Room;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
>>>>>>> Stashed changes
import android.widget.Toast;

import com.robustastudio.robustivityapp.Models.Projects;

<<<<<<< Updated upstream
=======
import java.util.ArrayList;
import java.util.Date;
>>>>>>> Stashed changes
import java.util.List;

/**
 * Created by MALAK SHAKER on 3/28/2018.
 */

<<<<<<< Updated upstream
public class viewProjects extends AppCompatActivity{

    private List<String> projects;
    private String accountname ;
=======
public class viewProjects extends AppCompatActivity {
    Button addproj ;
    private List<String> projects;
    // String accountname ;
    Projects proj ;
>>>>>>> Stashed changes


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
<<<<<<< Updated upstream
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_projects);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            accountname = extras.getString("accountName");
        }
        viewProjects();

=======

        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_projects);
        addproj =findViewById(R.id.addproject);
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

        proj = new Projects("try1", "Fun",m, new Date(18,02,10), new Date(18,02,10), "whatever", "bla bla", "bla", 20.0);

        viewProjects();
        addproj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.userDao().insertAllProjects(proj);
                Toast.makeText(getApplicationContext(),"Done ya baby"+proj.getName()+proj.getEngagement().get(1)+proj.getStartDate(),Toast.LENGTH_LONG).show();
            }
        });

       // }
>>>>>>> Stashed changes
    }

        public void viewProjects(){

<<<<<<< Updated upstream
            final AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity").allowMainThreadQueries().build();
            projects  = db.userDao().getAllProjects(accountname);

            if(projects.isEmpty()){
                Toast.makeText(getApplicationContext(),"There are no projects yet",Toast.LENGTH_LONG).show();
            }else{
                mRecyclerView = findViewById(R.id.projectsList);
                mRecyclerView.setHasFixedSize(true);

                mAdapter = new ProjectsAdapter(projects);
=======
            if(projects.isEmpty()){

                    Toast.makeText(getApplicationContext(),"No Items",Toast.LENGTH_LONG).show();

            }else{
                //Toast.makeText(getApplicationContext(),"fy"+projects.get(1),Toast.LENGTH_LONG).show();
                mRecyclerView = findViewById(R.id.projectsList);
                mRecyclerView.setHasFixedSize(true);
               // mAdapter = new ProjectsAdapter(projects,getApplicationContext());
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                mRecyclerView.setLayoutManager(mLayoutManager);
                mAdapter = new ProjectsAdapter(projects,getApplicationContext());
>>>>>>> Stashed changes
                mRecyclerView.setAdapter(mAdapter);
            }

        }




}
