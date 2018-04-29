package com.robustastudio.robustivityapp.CreateTodo;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.robustastudio.robustivityapp.AppDatabase;
import com.robustastudio.robustivityapp.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sa2r_ on 4/18/2018.
 */

public class CreateTodoView extends AppCompatActivity {
    EditText member, startTimeH,startTimeM,todoDated,todoDatem, todoDatey,todoDuration;
    Button addMember,addTodo;
    RecyclerView recycle;
    List<String>members=new ArrayList<>();
    CreateTodoPresenter presenter=new CreateTodoPresenter(this);
    DatabaseReference firebase;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_todo_mvp);
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
        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                members.add(member.getText().toString());
                recycle.setAdapter(new TodoAdapter(members));
                member.setText("");
            }
        });
        addTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.checkTextField(member,members);
                AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity")
                        .fallbackToDestructiveMigration().allowMainThreadQueries().build();
                presenter.addTodo(db,firebase,members,startTimeH.getText().toString()+":"+startTimeM.getText().toString(),new Date(Integer.parseInt(todoDatey.getText().toString()),Integer.parseInt(todoDatem.getText().toString()),Integer.parseInt(todoDated.getText().toString())),Double.valueOf(todoDuration.getText().toString()));
                Toast.makeText(CreateTodoView.this, "zay elfol", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
