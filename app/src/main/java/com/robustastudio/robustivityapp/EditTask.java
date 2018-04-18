package com.robustastudio.robustivityapp;

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

import com.robustastudio.robustivityapp.Models.Tasks;
import com.robustastudio.robustivityapp.ViewTask.ViewTaskView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sa2r_ on 4/6/2018.
 */

public class EditTask extends AppCompatActivity {
    TextView name, title, description, startDated, startDatem, startDatey, dueDated, dueDatem, dueDatey, assignee, estimatedHours, finishedHours, projectName, taskMember;
    RecyclerView recycle;
    int id;
    List<String> members=new ArrayList<>();
    Button edit, addMembers;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_task);
        name=findViewById(R.id.editTaskName);
        title=findViewById(R.id.editTaskTitle);
        description=findViewById(R.id.editTaskDescription);
        startDated=findViewById(R.id.editStartDated);
        startDatem=findViewById(R.id.editStartDatem);
        startDatey=findViewById(R.id.editStartDatey);
        dueDated=findViewById(R.id.editDueDated);
        dueDatem=findViewById(R.id.editDueDatem);
        dueDatey=findViewById(R.id.editDueDatey);
        assignee=findViewById(R.id.editTaskAssignee);
        estimatedHours=findViewById(R.id.editTaskEstimatedHours);
        finishedHours=findViewById(R.id.editFinishedHours);
        projectName=findViewById(R.id.editProjectName);
        taskMember=findViewById(R.id.editTaskMembers);
        recycle=findViewById(R.id.editMemberList);
        recycle.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity").fallbackToDestructiveMigration()
                .allowMainThreadQueries().build();
        Intent intent=getIntent();
        id=intent.getIntExtra("id",0);
        final Tasks task=db.taskDao().getUser(id);
        members=task.getMembers();
        name.setText(task.getName());
        description.setText(task.getDescription());
        startDated.setText(String.valueOf(task.getStartDate().getDate()));
        startDatem.setText(String.valueOf(task.getStartDate().getMonth()));
        startDatey.setText(String.valueOf(task.getStartDate().getYear()));
        dueDated.setText(String.valueOf(task.getDue_date().getDate()));
        dueDatem.setText(String.valueOf(task.getDue_date().getMonth()));
        dueDatey.setText(String.valueOf(task.getDue_date().getYear()));
        assignee.setText(task.getAssignee());
        estimatedHours.setText(String.valueOf(task.getEstimated_hours()));
        finishedHours.setText(String.valueOf(task.getFinished_hours()));
        projectName.setText(task.getProjectname());
        recycle.setAdapter(new EditTaskAdapter(task.getMembers()));
        addMembers=findViewById(R.id.addMembers);
        addMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                members.add(taskMember.getText().toString());
                recycle.setAdapter(new EditTaskAdapter(task.getMembers()));
                taskMember.setText("");
            }
        });
        edit=findViewById(R.id.editTask);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(taskMember.getText())){
                    members.add(taskMember.getText().toString());
                }
                Intent intent1=new Intent(EditTask.this,ViewTaskView.class);
                if(task.getName()!=name.getText().toString()){
                    task.setName(name.getText().toString());
                    intent1.putExtra("taskName",name.getText().toString());
                }
                System.out.println("This is the content of members after Delete");
                for (int i=0;i<members.size();i++)
                    System.out.println(members.get(i));
                task.setProjectname(projectName.getText().toString());
                task.setStartDate(new Date(Integer.parseInt(startDatey.getText().toString()),Integer.parseInt(startDatem.getText().toString()),Integer.parseInt(startDated.getText().toString())));
                task.setDue_date(new Date(Integer.parseInt(dueDatey.getText().toString()),Integer.parseInt(dueDatem.getText().toString()),Integer.parseInt(dueDated.getText().toString())));
                task.setAssignee(assignee.getText().toString());
                task.setEstimated_hours(Float.valueOf(estimatedHours.getText().toString()));
                task.setFinished_hours(Float.valueOf(finishedHours.getText().toString()));
                task.setDescription(description.getText().toString());
                task.setMembers(members);
                for(int i=0;i<task.getMembers().size();i++)
                    System.out.println(task.getMembers().get(i));
                db.taskDao().updateTask(task);
                startActivity(intent1);
            }
        });

    }
}
