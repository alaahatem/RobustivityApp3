package com.robustastudio.robustivityapp.EditTask;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.Models.Tasks;
import com.robustastudio.robustivityapp.R;
import com.robustastudio.robustivityapp.ViewTask.ViewTaskView;

import java.util.Date;
import java.util.List;

/**
 * Created by sa2r_ on 4/19/2018.
 */

public class EditTaskView extends AppCompatActivity implements EditTaskViewInt {
    TextView name, description, startDated, startDatem, startDatey, dueDated, dueDatem, dueDatey, estimatedHours, projectName;
    String id;
    Tasks temp;
    String members;
    Spinner taskMember;
    EditTaskPresenter presenter=new EditTaskPresenter(this);
    Button edit;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_task_mvp);
        name=findViewById(R.id.editTaskName);
        description=findViewById(R.id.editTaskDescription);
        startDated=findViewById(R.id.editStartDated);
        startDatem=findViewById(R.id.editStartDatem);
        startDatey=findViewById(R.id.editStartDatey);
        dueDated=findViewById(R.id.editDueDated);
        dueDatem=findViewById(R.id.editDueDatem);
        dueDatey=findViewById(R.id.editDueDatey);
        estimatedHours=findViewById(R.id.editTaskEstimatedHours);
        projectName=findViewById(R.id.editProjectName);
        taskMember=findViewById(R.id.editTaskMembers);
        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity").fallbackToDestructiveMigration()
                .allowMainThreadQueries().build();
        Intent intent=getIntent();
        id=intent.getStringExtra("id");
        presenter.taskInfo(db,id);
        edit=findViewById(R.id.editTask);
        List<String>temp1=presenter.fillMembers(db);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,temp1);
        taskMember.setAdapter(adapter);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(EditTaskView.this,ViewTaskView.class);
                temp.setName(name.getText().toString());
                intent1.putExtra("taskName",name.getText().toString());
                intent1.putExtra("projectName",projectName.getText().toString());
                temp.setProjectname(projectName.getText().toString());
                temp.setStartDate(new Date(Integer.parseInt(startDatey.getText().toString()),Integer.parseInt(startDatem.getText().toString()),Integer.parseInt(startDated.getText().toString())));
                temp.setDue_date(new Date(Integer.parseInt(dueDatey.getText().toString()),Integer.parseInt(dueDatem.getText().toString()),Integer.parseInt(dueDated.getText().toString())));
                temp.setEstimated_hours(Float.valueOf(estimatedHours.getText().toString()));
                temp.setDescription(description.getText().toString());
                temp.setMembers(taskMember.getSelectedItem().toString());
                presenter.editTask(db,temp);
                startActivity(intent1);
            }
        });
    }

    @Override
    public void viewInfo(Tasks task) {
        name.setText(task.getName());
        description.setText(task.getDescription());
        startDated.setText(String.valueOf(task.getStartDate().getDate()));
        startDatem.setText(String.valueOf(task.getStartDate().getMonth()));
        startDatey.setText(String.valueOf(task.getStartDate().getYear()));
        dueDated.setText(String.valueOf(task.getDue_date().getDate()));
        dueDatem.setText(String.valueOf(task.getDue_date().getMonth()));
        dueDatey.setText(String.valueOf(task.getDue_date().getYear()));
        estimatedHours.setText(String.valueOf(task.getEstimated_hours()));
        projectName.setText(task.getProjectname());
        temp=task;
    }
}
