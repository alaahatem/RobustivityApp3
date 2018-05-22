package com.robustastudio.robustivityapp.CreateTodo;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.HomeActivity;
import com.robustastudio.robustivityapp.Models.UserProfile;
import com.robustastudio.robustivityapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by sa2r_ on 4/18/2018.
 */

public class CreateTodoView extends AppCompatActivity {
    EditText title,member, startTimeH,startTimeM,todoDated,todoDatem, todoDatey,todoDuration,todoDateAll;
    Button addTodo;
    ImageView addMember;
    RecyclerView recycle;
    List<String>members=new ArrayList<>();
    CreateTodoPresenter presenter=new CreateTodoPresenter(this);
    DatabaseReference firebase;
    DatabaseReference reference;
    FirebaseAuth mAuth;

    Spinner spinner;
    Spinner duration_spinner;
    ArrayAdapter<CharSequence> arrayAdapter;
    List<UserProfile> userProfiles;
    List<String> userprofiles_emails;
    List<String> userprofiles_names;
    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog date;
    TimePickerDialog time;
    SimpleDateFormat dateFormatter;
        int year_y,month_m,day_d;

    ImageView datepicker_icon;
    ImageView timepicker_icon;
    List<String> duration_names;
    List<Integer> duration_values;





    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_todo_mvp);
        title = findViewById(R.id.todo_name);
        member = findViewById(R.id.todoMember);
        startTimeH = findViewById(R.id.startTimeH);
        startTimeM = findViewById(R.id.startTimeM);
      /*  todoDated = findViewById(R.id.todoDated);
        todoDatem = findViewById(R.id.todoDatem);
        todoDatey = findViewById(R.id.todoDatey);*/
       // todoDuration = findViewById(R.id.todoDuration);
        addMember = findViewById(R.id.addMember);
        addTodo = findViewById(R.id.addTodo);
        recycle = findViewById(R.id.todoMembersList);
        recycle.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        firebase = FirebaseDatabase.getInstance().getReference();
        reference = firebase.child("Todos");
        mAuth = FirebaseAuth.getInstance();
        userProfiles = new ArrayList<>();
        userprofiles_emails = new ArrayList<>();
        userprofiles_names = new ArrayList<>();
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        todoDateAll = findViewById(R.id.todoDateall);
       // todoDateAll.setOnClickListener(this);
        year_y=0;
        month_m=0;
        day_d=0;
        datepicker_icon = findViewById(R.id.datepickericon);
        timepicker_icon = findViewById(R.id.timepickericon);
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "robustivity")
                .fallbackToDestructiveMigration().allowMainThreadQueries().build();

        duration_names = new ArrayList<>();
        duration_values= new ArrayList<>();
        userProfiles = db.userDao().getAllprofiles();
        duration_names.add("30 Minutes");
        duration_names.add("1 Hour");
        duration_names.add("90 Minutes");
        duration_names.add("2 Hours");
        duration_values.add(30);
        duration_values.add(60);
        duration_values.add(90);
        duration_values.add(120);


        for (int i = 0; i < userProfiles.size(); i++) {
            userprofiles_names.add(userProfiles.get(i).getName());
           // Toast.makeText(getApplicationContext(), "3andena " + userProfiles.get(i).getName(), Toast.LENGTH_LONG).show();
        }
         userprofiles_names.add("");
        userProfiles.add(new UserProfile());
        spinner = findViewById(R.id.members_spinner);
        duration_spinner= findViewById(R.id.duration_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, userprofiles_names);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(userprofiles_names.size()-1);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, duration_names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        duration_spinner.setAdapter(adapter1);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                member.setText(userProfiles.get(i).getName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        date = new DatePickerDialog(this, AlertDialog.THEME_HOLO_DARK, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                year_y=year;
                month_m=monthOfYear;
                day_d=dayOfMonth;
                todoDateAll.setText(dateFormatter.format(newDate.getTime()));
            }

        }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));

       // Calendar mcurrentTime = Calendar.getInstance();
       // int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        //int minute = mcurrentTime.get(Calendar.MINUTE);
        time = new TimePickerDialog(this,AlertDialog.THEME_HOLO_DARK, new TimePickerDialog.OnTimeSetListener() {

            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                startTimeH.setText(""+selectedHour);
                startTimeM.setText(""+selectedMinute);
            }

        }, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), true);


        datepicker_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date.show();
            }
        });


        timepicker_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time.show();
            }
        });




        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // members.add(member.getText().toString());
                if(userprofiles_names.get(spinner.getSelectedItemPosition())!="") {
                    members.add(userProfiles.get(spinner.getSelectedItemPosition()).getName());
                    userprofiles_emails.add(userProfiles.get(spinner.getSelectedItemPosition()).getEmail());
                    //  Toast.makeText(getApplicationContext(),userProfiles.get(spinner.getSelectedItemPosition()).getEmail(),Toast.LENGTH_LONG).show();
                    TodoAdapter t = new TodoAdapter(members);
                    recycle.setAdapter(t);
                    member.setText("");
                }
            }
        });

        addTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!title.getText().toString().isEmpty() && !startTimeH.getText().toString().isEmpty() && !todoDateAll.getText().toString().isEmpty()) {
                    if (userprofiles_emails.isEmpty() || userprofiles_emails == null) {
                        if (userprofiles_names.get(spinner.getSelectedItemPosition()) != "") {
                            userprofiles_emails.add(userProfiles.get(spinner.getSelectedItemPosition()).getEmail());
                        }

                    }

                    // presenter.checkTextField(member,members);
                    AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "robustivity")
                            .fallbackToDestructiveMigration().allowMainThreadQueries().build();
                    presenter.addTodo(db, firebase, title.getText().toString(), mAuth.getCurrentUser().getEmail(), members, userprofiles_emails, startTimeH.getText().toString() + ":" + startTimeM.getText().toString(), new Date(year_y, month_m, day_d), duration_values.get(duration_spinner.getSelectedItemPosition()));
                    // Toast.makeText(CreateTodoView.this, "zay elfol", Toast.LENGTH_SHORT).show();

                    // Double.valueOf(todoDuration.getText().toString())
                    Intent i = new Intent(CreateTodoView.this, HomeActivity.class);
                    startActivity(i);

                }else {
                    Toast.makeText(getApplicationContext(),"Complete all the fields",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
