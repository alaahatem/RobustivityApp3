package com.robustastudio.robustivityapp.CreateProject;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.HomeActivity;
import com.robustastudio.robustivityapp.Models.Projects;
import com.robustastudio.robustivityapp.R;
import com.robustastudio.robustivityapp.ViewProjects.Activity_View_Projects;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sa2r_ on 4/14/2018.
 */

public class CreateProjectView extends AppCompatActivity {
    EditText name,plannedCost, contractedCost, type, engagement, startdated,startdatem,startdatey,duedated,duedatem,duedatey,tagLine, accountName, projectCost;
    Button addEngagement,done;
    List<String>engagementList=new ArrayList<>();
    RecyclerView recycle;
    CreateProjectPresenter presenter=new CreateProjectPresenter(this);

    List<Projects> p;
    DatabaseReference firebase;
    DatabaseReference ref;
    public int id;
    String account;
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
        plannedCost=findViewById(R.id.plannedCost);
        contractedCost=findViewById(R.id.contractedCost);
        recycle.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recycle.setAdapter(new ProjectAdapter(engagementList));
        addEngagement=findViewById(R.id.addEngagement);
        done=findViewById(R.id.addProject);
        firebase = FirebaseDatabase.getInstance().getReference();
        ref= firebase.child("Projects");
        p= new ArrayList<>();
        id=0;
        account="";

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            account = extras.getString("name");
        }

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Projects t = postSnapshot.getValue(Projects.class);
                    p.add(t);

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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
                id = p.size();
                AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity")
                        .fallbackToDestructiveMigration().allowMainThreadQueries().build();
//                String time= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new java.util.Date());
               long time =System.currentTimeMillis();
                presenter.addActivity(firebase,db,"Project Creation","Project has been created referring to Client : " +accountName.getText().toString(),accountName.getText().toString(),time);
                presenter.addProject(db,ref,id,name.getText().toString(), type.getText().toString(),new Date(Integer.parseInt(startdatey.getText().toString()),Integer.parseInt(startdatem.getText().toString()),

                                Integer.parseInt(startdated.getText().toString())),
                        new Date(Integer.parseInt(duedatey.getText().toString()),Integer.parseInt(duedatem.getText().toString()),
                                Integer.parseInt(duedated.getText().toString())),
                                engagementList,tagLine.getText().toString(),accountName.getText().toString(),Float.valueOf(projectCost.getText().toString()),Float.valueOf(contractedCost.getText().toString()),Float.valueOf(plannedCost.getText().toString()));
//
//                Intent intent = new Intent(CreateProjectView.this, HomeActivity.class);
//
//                startActivity(intent);
            }
        });
    }
}
