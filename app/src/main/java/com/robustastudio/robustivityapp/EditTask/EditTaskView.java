package com.robustastudio.robustivityapp.EditTask;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.Models.Tasks;
import com.robustastudio.robustivityapp.R;
import com.robustastudio.robustivityapp.ViewTask.ViewTaskView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sa2r_ on 4/19/2018.
 */

public class EditTaskView extends AppCompatActivity implements EditTaskViewInt{
    TextView name, description, startDated, startDatem, startDatey, dueDated, dueDatem, dueDatey, assignee, estimatedHours, projectName, taskMember;
    RecyclerView recycle;
    int id;
    Tasks temp;
    List<String>members=new ArrayList<>();
    EditTaskAdapter editTaskAdapter;
    EditTaskPresenter presenter=new EditTaskPresenter(this);
    Button edit, addMembers;
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
        assignee=findViewById(R.id.editTaskAssignee);
        estimatedHours=findViewById(R.id.editTaskEstimatedHours);
        projectName=findViewById(R.id.editProjectName);
        taskMember=findViewById(R.id.editTaskMembers);
        recycle=findViewById(R.id.editMemberList);
        recycle.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity").fallbackToDestructiveMigration()
                .allowMainThreadQueries().build();
        Intent intent=getIntent();
        id=intent.getIntExtra("id",0);
        presenter.taskInfo(db,id);
        addMembers=findViewById(R.id.addMembers);
        edit=findViewById(R.id.editTask);
        addMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTaskAdapter.getMembers().add(taskMember.getText().toString());
                editTaskAdapter.notifyItemInserted(editTaskAdapter.getItemCount()-1);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(taskMember.getText())){
                    editTaskAdapter.getMembers().add(taskMember.getText().toString());
                    editTaskAdapter.notifyItemInserted(editTaskAdapter.getItemCount()-1);
                }
                Intent intent1=new Intent(EditTaskView.this,ViewTaskView.class);
                if(!temp.getName().equals(name.getText().toString())){
                    temp.setName(name.getText().toString());
                    intent1.putExtra("taskName",name.getText().toString());
                }
                temp.setProject_name(projectName.getText().toString());
                temp.setStartDate(new Date(Integer.parseInt(startDatey.getText().toString()),Integer.parseInt(startDatem.getText().toString()),Integer.parseInt(startDated.getText().toString())));
                temp.setDue_date(new Date(Integer.parseInt(dueDatey.getText().toString()),Integer.parseInt(dueDatem.getText().toString()),Integer.parseInt(dueDated.getText().toString())));
                temp.setAssigne(assignee.getText().toString());
                temp.setEstimated_hours(Float.valueOf(estimatedHours.getText().toString()));
                temp.setDescription(description.getText().toString());
                temp.setMembers(editTaskAdapter.getMembers().isEmpty()?null:editTaskAdapter.getMembers());
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
        assignee.setText(task.getAssigne());
        estimatedHours.setText(String.valueOf(task.getEstimated_hours()));
        projectName.setText(task.getProject_name());
        editTaskAdapter=new EditTaskAdapter(task.getMembers());
        recycle.setAdapter(editTaskAdapter);
        temp=task;
    }
}
