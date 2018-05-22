package com.robustastudio.robustivityapp.CreateProject;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by sa2r_ on 4/14/2018.
 */

public class CreateProjectView extends AppCompatActivity {
    EditText name,plannedCost, contractedCost, engagement, startdated,startdatem,startdatey,duedated,duedatem,duedatey,tagLine, accountName, projectCost;
    Button addEngagement,done;
    List<String>engagementList=new ArrayList<>();
    RecyclerView recycle;
    CreateProjectPresenter presenter=new CreateProjectPresenter(this);

    List<Projects> p;
    DatabaseReference firebase;
    DatabaseReference ref;
    public int id;
    String account;
    Spinner type;
    Spinner account_name;
    Spinner engagement_spinner;
    List<String> project_types;
    List<String> accounts;
    List<String> engagement_types;

    DatePickerDialog start_date;
    DatePickerDialog end_date;
    Calendar myCalendar = Calendar.getInstance();
    SimpleDateFormat dateFormatter;
    ImageView datepicker_icon;
    ImageView datepicker_icon2;
    int start_y,end_y,start_m,end_m,start_d,end_d;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_project_mvp);

        project_types=new ArrayList<>();
        accounts=new ArrayList<>();
        engagement_types=new ArrayList<>();
        project_types.add("Mobile App");
        project_types.add("Website");
        project_types.add("E-Commerce");
        project_types.add("Design");

        engagement_types.add("Scope");
        engagement_types.add("Swat");
        engagement_types.add("Support");

        name=findViewById(R.id.projectName);
       // type=findViewById(R.id.projectType);
        type=findViewById(R.id.project_type_spinner);
       // engagement=findViewById(R.id.projectEngagement);
        engagement_spinner=findViewById(R.id.engagement_spinner);
        startdated=findViewById(R.id.startDay);
        //startdatem=findViewById(R.id.startMonth);
        //startdatey=findViewById(R.id.startYear);

        datepicker_icon = findViewById(R.id.datepickericon_start);
        datepicker_icon2 = findViewById(R.id.datepickericon_end);
        duedated=findViewById(R.id.endDay);
        //duedatem=findViewById(R.id.endMonth);
        //duedatey=findViewById(R.id.endYear);
        tagLine=findViewById(R.id.projectTagLine);
        //accountName=findViewById(R.id.projectAccountName);
        account_name=findViewById(R.id.account_spinner);
        projectCost=findViewById(R.id.projectCost);
        recycle=findViewById(R.id.engagementList);
        plannedCost=findViewById(R.id.plannedCost);
        contractedCost=findViewById(R.id.contractedCost);
        recycle.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recycle.setAdapter(new ProjectAdapter(engagementList));
        //addEngagement=findViewById(R.id.addEngagement);
        done=findViewById(R.id.addProject);
        firebase = FirebaseDatabase.getInstance().getReference();
        ref= firebase.child("Projects");
        p= new ArrayList<>();
        id=0;
        account="";



        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, project_types);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity")
                .fallbackToDestructiveMigration().allowMainThreadQueries().build();
        accounts= db.projectDao().get_all_accounts();

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, accounts);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        account_name.setAdapter(adapter1);


        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, engagement_types);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        engagement_spinner.setAdapter(adapter2);


        start_date = new DatePickerDialog(this, AlertDialog.THEME_HOLO_DARK, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                start_y=year;
                start_m=monthOfYear;
                start_d=dayOfMonth;
                startdated.setText(dateFormatter.format(newDate.getTime()));
            }

        }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));

        end_date = new DatePickerDialog(this, AlertDialog.THEME_HOLO_DARK, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                end_y=year;
                end_m=monthOfYear;
                end_d=dayOfMonth;
                duedated.setText(dateFormatter.format(newDate.getTime()));
            }

        }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));

        datepicker_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_date.show();
            }
        });

        datepicker_icon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                end_date.show();
            }
        });

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

       /* addEngagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                engagementList.add(engagement.getText().toString());
                recycle.setAdapter(new ProjectAdapter(engagementList));
                engagement.setText("");
            }
        });*/
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //presenter.checkTextField(engagement,engagementList);
                id = p.size();
                AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity")
                        .fallbackToDestructiveMigration().allowMainThreadQueries().build();

                long time =System.currentTimeMillis();
                presenter.addActivity(firebase,db,"Project Creation","Project has been created referring to Client : " +accountName.getText().toString(),accountName.getText().toString(),time);
                presenter.addProject(db,ref,id,name.getText().toString(), project_types.get(type.getSelectedItemPosition()),new Date(start_y,start_m, start_d),
                        new Date(end_y,end_m, end_d),
                                engagement_types.get(engagement_spinner.getSelectedItemPosition()),tagLine.getText().toString(),accounts.get(account_name.getSelectedItemPosition()),Float.valueOf(projectCost.getText().toString()),Float.valueOf(contractedCost.getText().toString()),Float.valueOf(plannedCost.getText().toString()));

                Intent intent = new Intent(CreateProjectView.this, HomeActivity.class);
              startActivity(intent);
            }
        });
    }
}
