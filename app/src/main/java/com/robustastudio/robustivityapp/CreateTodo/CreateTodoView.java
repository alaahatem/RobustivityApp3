package com.robustastudio.robustivityapp.CreateTodo;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.HomeActivity;
import com.robustastudio.robustivityapp.Models.UserProfile;
import com.robustastudio.robustivityapp.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sa2r_ on 4/18/2018.
 */

public class CreateTodoView extends AppCompatActivity {
    EditText title,member, startTimeH,startTimeM,todoDated,todoDatem, todoDatey,todoDuration;
    Button addTodo;
    ImageView addMember;
    RecyclerView recycle;
    List<String>members=new ArrayList<>();
    CreateTodoPresenter presenter=new CreateTodoPresenter(this);
    DatabaseReference firebase;
    DatabaseReference reference;
    FirebaseAuth mAuth;

    Spinner spinner;
    ArrayAdapter<CharSequence> arrayAdapter;
    List<UserProfile> userProfiles;
    List<String> userprofiles_emails;
    List<String> userprofiles_names;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_todo_mvp);
        title=findViewById(R.id.todo_name);
        member=findViewById(R.id.todoMember);
        startTimeH=findViewById(R.id.startTimeH);
        startTimeM=findViewById(R.id.startTimeM);
        todoDated=findViewById(R.id.todoDated);
        todoDatem=findViewById(R.id.todoDatem);
        todoDatey=findViewById(R.id.todoDatey);
        todoDuration=findViewById(R.id.todoDuration);
        addMember=findViewById(R.id.addMember);
        addTodo=findViewById(R.id.addTodo);
        recycle=findViewById(R.id.todoMembersList);
        recycle.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        firebase= FirebaseDatabase.getInstance().getReference();
        reference=firebase.child("Todos");
        mAuth= FirebaseAuth.getInstance();
        userProfiles = new ArrayList<>();
        userprofiles_emails = new ArrayList<>();
        userprofiles_names = new ArrayList<>();
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity")
                .fallbackToDestructiveMigration().allowMainThreadQueries().build();

        userProfiles = db.userDao().getAllprofiles();
       // userProfiles.add(new UserProfile();
        for(int i=0;i<userProfiles.size();i++){
                userprofiles_names.add(userProfiles.get(i).getName());
                Toast.makeText(getApplicationContext(),"3andena "+userProfiles.get(i).getName(),Toast.LENGTH_LONG).show();
              }

        spinner = findViewById(R.id.members_spinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, userprofiles_names);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                member.setText(userProfiles.get(i).getName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // members.add(member.getText().toString());
                members.add(userProfiles.get(spinner.getSelectedItemPosition()).getName());
                userprofiles_emails.add(userProfiles.get(spinner.getSelectedItemPosition()).getEmail());
                Toast.makeText(getApplicationContext(),userProfiles.get(spinner.getSelectedItemPosition()).getEmail(),Toast.LENGTH_LONG).show();
                TodoAdapter t = new TodoAdapter(members);
                recycle.setAdapter(t);
                member.setText("");
            }
        });

        addTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(userprofiles_emails.isEmpty()||userprofiles_emails==null){
                    userprofiles_emails.add(userProfiles.get(spinner.getSelectedItemPosition()).getEmail());
                }
                presenter.checkTextField(member,members);
                AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity")
                        .fallbackToDestructiveMigration().allowMainThreadQueries().build();
                presenter.addTodo(db,firebase,title.getText().toString(),mAuth.getCurrentUser().getEmail(),members,userprofiles_emails,startTimeH.getText().toString()+":"+startTimeM.getText().toString(),new Date(Integer.parseInt(todoDatey.getText().toString()),Integer.parseInt(todoDatem.getText().toString()),Integer.parseInt(todoDated.getText().toString())),Double.valueOf(todoDuration.getText().toString()));
                Toast.makeText(CreateTodoView.this, "zay elfol", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(CreateTodoView.this, HomeActivity.class);
                startActivity(i);

            }
        });
    }
}
