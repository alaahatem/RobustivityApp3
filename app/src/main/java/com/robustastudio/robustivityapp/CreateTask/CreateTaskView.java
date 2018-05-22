package com.robustastudio.robustivityapp.CreateTask;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.robustastudio.robustivityapp.Adapters.ActivityAdapter;
import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.Models.Tasks;
import com.robustastudio.robustivityapp.NewsFeed;
import com.robustastudio.robustivityapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sa2r_ on 4/14/2018.
 */

public class CreateTaskView extends AppCompatActivity {
    EditText name,description, projectName, estimatedHours, startDated,startDatem,startDatey,dueDated,dueDatem,dueDatey;
    Button done;
    List<String>members=new ArrayList<>();
    CreateTaskPresenter presenter=new CreateTaskPresenter(this);
    public  DatabaseReference fireBase;
    List<Tasks> tasks;
    Spinner spinner;
    FirebaseAuth mAuth;
    public void onCreate(Bundle savedInstancestate) {
        super.onCreate(savedInstancestate);
        setContentView(R.layout.create_task_mvp);
        name=findViewById(R.id.taskName);
        description=findViewById(R.id.taskDescription);
        spinner=findViewById(R.id.taskMember);
        projectName=findViewById(R.id.taskProjectName);
        estimatedHours=findViewById(R.id.taskEstimatedHours);
        startDated=findViewById(R.id.taskStartDay);
        startDatem=findViewById(R.id.taskStartMonth);
        startDatey=findViewById(R.id.taskStartYear);
        dueDated=findViewById(R.id.taskEndDay);
        dueDatem=findViewById(R.id.taskEndMonth);
        dueDatey=findViewById(R.id.taskEndYear);
        done=findViewById(R.id.addTask);
        fireBase= FirebaseDatabase.getInstance().getReference();
        mAuth=FirebaseAuth.getInstance();
        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity")
                .fallbackToDestructiveMigration().allowMainThreadQueries().build();
        List<String>temp=presenter.fillMembers(db);
        spinner=findViewById(R.id.taskMember);
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,temp);
        spinner.setAdapter(adapter);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                presenter.addTask(db,fireBase,mAuth,name.getText().toString(),description.getText().toString(),spinner.getSelectedItem().toString(),new Date(Integer.parseInt(startDatey.getText().toString()),Integer.parseInt(startDatem.getText().toString()),Integer.parseInt(startDated.getText().toString())),new Date(Integer.parseInt(dueDatey.getText().toString()),Integer.parseInt(dueDatem.getText().toString()),Integer.parseInt(dueDated.getText().toString())),Float.valueOf(estimatedHours.getText().toString()),projectName.getText().toString());
                long time =  System.currentTimeMillis();
                presenter.addActivity(fireBase,db,"Task Creation",mAuth.getCurrentUser().getDisplayName()+" "+"Created a new Task called"+" "+name.getText().toString()+" in Project"+" "+projectName.getText().toString(),mAuth.getCurrentUser().getEmail(),time);
                Toast.makeText(CreateTaskView.this, "kollo fel konafa", Toast.LENGTH_SHORT).show();

            }
        });




    }
}
