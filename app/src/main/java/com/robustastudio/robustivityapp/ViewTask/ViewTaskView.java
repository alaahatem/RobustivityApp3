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

import com.robustastudio.robustivityapp.Database.AppDatabase;
/*import com.robustastudio.robustivityapp.EditTask;*/
import com.robustastudio.robustivityapp.R;
import com.robustastudio.robustivityapp.ViewTasks.ViewTasksView;

import java.util.Date;
import java.util.List;

/**
 * Created by sa2r_ on 4/18/2018.
 */

public class ViewTaskView extends AppCompatActivity implements ViewTaskViewInt{
    RecyclerView recycle;
    TextView viewID,viewName,viewDesciption, viewAssignee, viewEstimatedHours, viewStartDate, viewDueDate, viewFinishedHours, viewProjectName;
    Button delete,edit;
    ViewTaskPresenter presenter;

    public ViewTaskView() {
    }

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        presenter = new ViewTaskPresenter(this);
        setContentView(R.layout.view_task);
        recycle=findViewById(R.id.viewMembers);
        recycle.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        viewID=findViewById(R.id.viewID);
        viewName=findViewById(R.id.viewName);
        viewDesciption=findViewById(R.id.viewDescription);
        viewAssignee=findViewById(R.id.viewAssignee);
        viewEstimatedHours=findViewById(R.id.viewEstimatedHours);
        viewStartDate=findViewById(R.id.viewStartDate);
        viewDueDate=findViewById(R.id.viewDueDate);
        viewFinishedHours=findViewById(R.id.viewFinishedHours);
        viewProjectName=findViewById(R.id.viewProjectName);
        edit=findViewById(R.id.edit);
        delete=findViewById(R.id.delete);
        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity").fallbackToDestructiveMigration()
                .allowMainThreadQueries().build();
        Intent intent=getIntent();
        String temp=intent.getStringExtra("taskName");
        presenter.viewTask(db,temp);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.delete(Integer.parseInt(viewID.getText().toString()),db);
                Intent i=new Intent(ViewTaskView.this,ViewTasksView.class);
                startActivity(i);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent1=new Intent(ViewTaskView.this,EditTask.class);
                //startActivity(intent1);
            }
        });

    }

    @Override
    public void viewTask(String id,String name, String description, String assignee, List<String> members, float estimated_hours, Date due_date, float finished_hours, Date startDate, String projectname) {

        viewName.setText(name);
        viewID.setText(id);
        viewDesciption.setText(description);
        viewAssignee.setText(assignee);
        viewEstimatedHours.setText(String.valueOf(estimated_hours));
        viewStartDate.setText(String.valueOf(startDate));
        viewDueDate.setText(String.valueOf(due_date));
        viewFinishedHours.setText(String.valueOf(finished_hours));
        viewProjectName.setText(projectname);
    }
}