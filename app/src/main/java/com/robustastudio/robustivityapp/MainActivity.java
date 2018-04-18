package com.robustastudio.robustivityapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.robustastudio.robustivityapp.CreateProject.CreateProjectView;
import com.robustastudio.robustivityapp.CreateTask.CreateTaskView;
import com.robustastudio.robustivityapp.CreateTodo.CreateTodoView;
import com.robustastudio.robustivityapp.ViewTasks.ViewTasksView;

public class MainActivity extends AppCompatActivity {
    public void onCreate(Bundle savedInstantceState) {
        super.onCreate(savedInstantceState);


        setContentView(R.layout.home_screen);

        Button b1,b2,b3,b4;
        b1=findViewById(R.id.createProject);
        b2=findViewById(R.id.createTask);
        b3=findViewById(R.id.createTodo);
        b4=findViewById(R.id.viewTasks);
        final Intent intent1=new Intent(this, CreateTaskView.class);
        final Intent intent2=new Intent(this, CreateTodoView.class);
        final Intent intent3=new Intent(this,ViewTasksView.class);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,CreateProjectView.class);
                MainActivity.this.startActivity(intent);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent1);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent2);
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent3);
            }
        });
    }

}
