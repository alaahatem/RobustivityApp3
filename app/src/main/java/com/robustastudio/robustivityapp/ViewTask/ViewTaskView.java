package com.robustastudio.robustivityapp.ViewTask;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.EditTask.EditTaskView;
import com.robustastudio.robustivityapp.Models.Tasks;
import com.robustastudio.robustivityapp.R;
import com.robustastudio.robustivityapp.ViewTasks.ViewTasksView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sa2r_ on 4/18/2018.
 */

public class ViewTaskView extends AppCompatActivity implements ViewTaskViewInt {
    TextView viewName,viewDesciption, viewAssignee, viewEstimatedHours, viewStartDate, viewDueDate, viewFinishedHours, viewProjectName,viewMember;
    Button delete,edit;
    ViewTaskPresenter presenter;
    String members;
    String temp2;
    String temp;
    Tasks task;
    DatabaseReference fireBase;
    public ViewTaskView() {
    }

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        presenter = new ViewTaskPresenter(this);
        setContentView(R.layout.view_task);
        temp="";
        temp2="";
        viewName=findViewById(R.id.viewName);
        viewDesciption=findViewById(R.id.viewDescription);
        viewAssignee=findViewById(R.id.viewAssignee);
        viewEstimatedHours=findViewById(R.id.viewEstimatedHours);
        viewStartDate=findViewById(R.id.viewStartDate);
        viewDueDate=findViewById(R.id.viewDueDate);
        viewMember=findViewById(R.id.viewMembers);
        viewFinishedHours=findViewById(R.id.viewFinishedHours);
        viewProjectName=findViewById(R.id.viewProjectName);
        edit=findViewById(R.id.edit);
        fireBase= FirebaseDatabase.getInstance().getReference();
        delete=findViewById(R.id.delete);
        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity").fallbackToDestructiveMigration()
                .allowMainThreadQueries().build();

        Bundle extras = getIntent().getExtras();
        temp2="";
        if (extras != null) {
            System.out.println("testing");
            temp2 = extras.getString("projectName");
            temp=extras.getString("taskName");
        }

        //Intent intent=getIntent();
        //temp2=intent.getStringExtra("projectName");

        System.out.println(temp2);
        presenter.viewTask(db,temp,temp2);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.delete(task.getId(),db);
                fireBase.child("Tasks").child(task.getId()).removeValue();
                Intent i=new Intent(ViewTaskView.this,ViewTasksView.class);
                i.putExtra("projectName",viewProjectName.getText().toString());
                startActivity(i);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(ViewTaskView.this,EditTaskView.class);
                intent1.putExtra("id",task.getId());
                startActivity(intent1);
            }
        });

    }

    @Override
    public void viewTask(Tasks task) {

        viewName.setText(task.getName());
        viewDesciption.setText(task.getDescription());
        viewAssignee.setText(task.getAssignee());
        viewEstimatedHours.setText(String.valueOf(task.getEstimated_hours()));
        task.getStartDate().setMonth(task.getStartDate().getMonth()-1);
        task.getStartDate().setYear(task.getStartDate().getYear()-1900);
        task.getDue_date().setMonth(task.getDue_date().getMonth()-1);
        task.getDue_date().setYear(task.getDue_date().getYear()-1900);
        viewStartDate.setText(String.valueOf(new SimpleDateFormat("dd-MM-yyyy").format(task.getStartDate())));
        viewDueDate.setText(String.valueOf(new SimpleDateFormat("dd-MM-yyyy").format(task.getDue_date())));
        viewFinishedHours.setText(String.valueOf(task.getFinished_hours()));
        viewProjectName.setText(task.getProjectname());
        viewMember.setText(task.getMembers());
        this.task=task;
    }
}
