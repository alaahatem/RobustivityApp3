package com.robustastudio.robustivityapp;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.robustastudio.robustivityapp.Models.Projects;

import java.util.Date;
import java.util.List;

public class Project extends AppCompatActivity {
    public String projectName;
    public Projects p;

    public Date start;

    public TextView name ;
    public TextView type;
    public List<String> engagement;
    public TextView StartDate;
    public TextView endDate;
    public TextView Tagline;
    public TextView SectorName;
    public TextView accountName ;
    public TextView cost;
    private List<String> list;

    private Button statistics ;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    public String newdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            projectName = extras.getString("accountName");
        }






        projectName = extras.getString("projectName");




        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity").allowMainThreadQueries().build();
        p  = db.userDao().getProjectDetails(projectName);

        name = findViewById(R.id.name);
        type = findViewById(R.id.type);
        StartDate = findViewById(R.id.startDate);
        endDate = findViewById(R.id.endDate);
        Tagline = findViewById(R.id.tagline);
        SectorName = findViewById(R.id.sector);
        accountName = findViewById(R.id.account);
        cost = findViewById(R.id.cost);
        statistics =findViewById(R.id.statistics);

       //newdate = DateFormat.getDateTimeInstance().format(start);
      // String end = DateFormat.getDateTimeInstance().format(p.getEndDate());

        list =p.getEngagement();
        name.setText(p.getName());
        type.setText(p.getType());
       StartDate.setText(""+p.getStartDate());
      endDate.setText(""+p.getEndDate());
       Tagline.setText(p.getTagline());
       SectorName.setText(p.getSectorName());
      accountName.setText(p.getAccountName());
       cost.setText(""+p.getProject_cost());

        mRecyclerView = findViewById(R.id.engagement);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new EngagementListAdapter(list);
        mRecyclerView.setAdapter(mAdapter);

        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), statistics.class);
                i.putExtra("projectName",p.getName());
                startActivity(i);
           }
        });


    }

}
