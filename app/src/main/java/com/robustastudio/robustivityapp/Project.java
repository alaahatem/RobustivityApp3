package com.robustastudio.robustivityapp;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.robustastudio.robustivityapp.Models.Projects;

import java.text.DateFormat;
import java.util.List;

public class Project extends AppCompatActivity {
    public String projectName;
    public Projects p;

     EditText name ;
     EditText type;
     List<String> engagement;
     EditText StartDate;
      EditText endDate;
     EditText Tagline;
     EditText SectorName;
     EditText accountName ;
     EditText cost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            projectName = extras.getString("accountName");
        }

        viewProject();

    }

    public void viewProject(){
        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity").allowMainThreadQueries().build();
        p  = db.userDao().getProjectDetails(projectName);

        name = findViewById(R.id.name);
        type = findViewById(R.id.type);
        StartDate = findViewById(R.id.startDate);
        endDate = findViewById(R.id.endDate);
        Tagline = findViewById(R.id.tagline);
        SectorName = findViewById(R.id.sectorName);
        accountName = findViewById(R.id.account);
        cost = findViewById(R.id.cost);





    }
}
