package com.robustastudio.robustivityapp.CreateTask;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.HomeActivity;
import com.robustastudio.robustivityapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sa2r_ on 4/14/2018.
 */

public class CreateTaskView extends AppCompatActivity {
    EditText name,description, member, projectName,assignee, estimatedHours, startDated,startDatem,startDatey,dueDated,dueDatem,dueDatey;
    Button addMember, done;
    List<String>members=new ArrayList<>();
    RecyclerView recycle;
    DatabaseReference mDatabase;
    CreateTaskPresenter presenter=new CreateTaskPresenter();
    public void onCreate(Bundle savedInstancestate) {
        super.onCreate(savedInstancestate);
        setContentView(R.layout.create_task_mvp);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        name=findViewById(R.id.taskName);
        description=findViewById(R.id.taskDescription);
        member=findViewById(R.id.taskMember);
        projectName=findViewById(R.id.taskProjectName);
        assignee=findViewById(R.id.taskAssignee);
        estimatedHours=findViewById(R.id.taskEstimatedHours);
        startDated=findViewById(R.id.taskStartDay);
        startDatem=findViewById(R.id.taskStartMonth);
        startDatey=findViewById(R.id.taskStartYear);
        dueDated=findViewById(R.id.taskEndDay);
        dueDatem=findViewById(R.id.taskEndMonth);
        dueDatey=findViewById(R.id.taskEndYear);
        addMember=findViewById(R.id.addMember);
        done=findViewById(R.id.addTask);
        recycle=findViewById(R.id.memberList);
        recycle.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                members.add(member.getText().toString());
                recycle.setAdapter(new TaskAdapter(members));
                member.setText("");
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.checkTextField(member,members);
                AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity")
                        .fallbackToDestructiveMigration().allowMainThreadQueries().build();
                presenter.addTask(db,name.getText().toString(),description.getText().toString(),assignee.getText().toString(),members,new Date(Integer.parseInt(startDatey.getText().toString()),Integer.parseInt(startDatem.getText().toString()),Integer.parseInt(startDated.getText().toString())),new Date(Integer.parseInt(dueDatey.getText().toString()),Integer.parseInt(dueDatem.getText().toString()),Integer.parseInt(dueDated.getText().toString())),Float.valueOf(estimatedHours.getText().toString()),0.0f,projectName.getText().toString(),0);
                String time= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new java.util.Date());
                presenter.addActivity(mDatabase,db,"Task Creation",assignee.getText().toString()+" "+"Created a new Task called"+" "+name.getText().toString()+" in Project"+" "+projectName.getText().toString(),assignee.getText().toString(),time);
                Toast.makeText(CreateTaskView.this, "kollo fel konafa", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CreateTaskView.this, HomeActivity.class);
                startActivity(intent);
            }

        });

    }
}
