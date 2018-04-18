package com.robustastudio.robustivityapp.CreateProject;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.robustastudio.robustivityapp.AppDatabase;
import com.robustastudio.robustivityapp.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sa2r_ on 4/14/2018.
 */

public class CreateProjectView extends AppCompatActivity{
    EditText name, type, engagement, startdated,startdatem,startdatey,duedated,duedatem,duedatey,tagLine, accountName, projectCost;
    Button addEngagement,done;
    List<String>engagementList=new ArrayList<>();
    RecyclerView recycle;
    CreateProjectPresenter presenter=new CreateProjectPresenter();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_project_mvp);
        name=findViewById(R.id.projectName);
        type=findViewById(R.id.projectType);
        engagement=findViewById(R.id.projectEngagement);
        startdated=findViewById(R.id.startDay);
        startdatem=findViewById(R.id.startMonth);
        startdatey=findViewById(R.id.startYear);
        duedated=findViewById(R.id.endDay);
        duedatem=findViewById(R.id.endMonth);
        duedatey=findViewById(R.id.endYear);
        tagLine=findViewById(R.id.projectTagLine);
        accountName=findViewById(R.id.projectAccountName);
        projectCost=findViewById(R.id.projectCost);
        recycle=findViewById(R.id.engagementList);
        recycle.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recycle.setAdapter(new ProjectAdapter(engagementList));
        addEngagement=findViewById(R.id.addEngagement);
        done=findViewById(R.id.addProject);
        addEngagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                engagementList.add(engagement.getText().toString());
                recycle.setAdapter(new ProjectAdapter(engagementList));
                engagement.setText("");
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.checkTextField(engagement,engagementList);
                AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity")
                        .fallbackToDestructiveMigration().allowMainThreadQueries().build();
                presenter.addProject(db,name.getText().toString(), type.getText().toString(),new Date(Integer.parseInt(startdatey.getText().toString()),Integer.parseInt(startdatem.getText().toString()),
                                Integer.parseInt(startdated.getText().toString())),
                        new Date(Integer.parseInt(duedatey.getText().toString()),Integer.parseInt(duedatem.getText().toString()),
                                Integer.parseInt(duedated.getText().toString())),
                                engagementList,tagLine.getText().toString(),accountName.getText().toString(),Float.valueOf(projectCost.getText().toString()));
                Toast.makeText(CreateProjectView.this, "kollo zal fol !!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
