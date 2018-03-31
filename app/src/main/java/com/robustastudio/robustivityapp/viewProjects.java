package com.robustastudio.robustivityapp;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.robustastudio.robustivityapp.Models.Projects;

import java.util.List;

/**
 * Created by MALAK SHAKER on 3/28/2018.
 */

public class viewProjects extends AppCompatActivity{

    private List<String> projects;
    private String accountname ;


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_projects);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            accountname = extras.getString("accountName");
        }
        viewProjects();

    }

        public void viewProjects(){

            final AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity").allowMainThreadQueries().build();
            projects  = db.userDao().getAllProjects(accountname);

            if(projects.isEmpty()){
                Toast.makeText(getApplicationContext(),"There are no projects yet",Toast.LENGTH_LONG).show();
            }else{
                mRecyclerView = findViewById(R.id.projectsList);
                mRecyclerView.setHasFixedSize(true);

                mAdapter = new ProjectsAdapter(projects);
                mRecyclerView.setAdapter(mAdapter);
            }

        }




}
