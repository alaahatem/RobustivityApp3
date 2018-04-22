package com.robustastudio.robustivityapp.ViewSingleProject;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.Adapters.EngagementListAdapter;
import com.robustastudio.robustivityapp.Models.Projects;
import com.robustastudio.robustivityapp.R;
import com.robustastudio.robustivityapp.Statistics.Activity_show_statistics;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Activity_Project extends AppCompatActivity implements Project_View{
    public String projectName;
    public Projects p;
    public Date start;
    public TextView name ;
    public TextView type;
    public List<String> engagement;
    public TextView StartDate;
    public TextView endDate;
    public TextView Tagline;
    public TextView accountName ;
    public TextView cost;
    private List<String> list;

    private Button statistics ;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    public String newdate;
    public Project_presenter mpresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        mpresenter = new Project_presenter(Activity_Project.this);

        mRecyclerView = findViewById(R.id.engagement);
        name = findViewById(R.id.pname);
        type = findViewById(R.id.type);
        StartDate = findViewById(R.id.startDate);
        endDate = findViewById(R.id.endDate);
        Tagline = findViewById(R.id.tagline);
        accountName = findViewById(R.id.account);
        cost = findViewById(R.id.cost);
        statistics =findViewById(R.id.statistics);


        Bundle extras = getIntent().getExtras();


        if (extras != null) {
        projectName = extras.getString("projectName");
       }


        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity").allowMainThreadQueries().build();

        mpresenter.get_Single_Project(db,projectName);

        //list =findViewById(R.id.engagement);




        //mpresenter.get_project();


        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Activity_show_statistics.class);
                //Projects p = mpresenter.get_project();
                i.putExtra("projectName",projectName);
                startActivity(i);
           }
        });


}

    public void view_details(Projects p){

        list =p.getEngagement();
        name.setText(p.getName());
        type.setText(p.getType());
        SimpleDateFormat format = new SimpleDateFormat(
                "EEE, d/MM/yy");

        StartDate.setText(""+format.format(p.getStartDate()));
        endDate.setText(""+format.format(p.getEndDate()));
        Tagline.setText(p.getTagline());
        accountName.setText(p.getAccountName());
        cost.setText(""+p.getProject_cost());


        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new EngagementListAdapter(list);
        mRecyclerView.setAdapter(mAdapter);

    }


}
