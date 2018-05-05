package com.robustastudio.robustivityapp.CreateTodo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.HomeActivity;
import com.robustastudio.robustivityapp.Models.Projects;
import com.robustastudio.robustivityapp.R;
import com.robustastudio.robustivityapp.Shortcuts_fragment;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sa2r_ on 4/18/2018.
 */

public class CreateTodoView extends AppCompatActivity {
    EditText title,member, startTimeH,startTimeM,todoDated,todoDatem, todoDatey,todoDuration;
    Button addMember,addTodo;
    RecyclerView recycle;
    List<String>members=new ArrayList<>();
    CreateTodoPresenter presenter=new CreateTodoPresenter(this);
    DatabaseReference firebase;
    DatabaseReference reference;
    FirebaseAuth mAuth;


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





      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            channel.setDescription(description);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));

        }*/


       /* reference.addValueEventListener(new ValueEventListener() {
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
        });*/


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
                presenter.addTodo(db,firebase,title.getText().toString(),mAuth.getCurrentUser().getEmail(),members,startTimeH.getText().toString()+":"+startTimeM.getText().toString(),new Date(Integer.parseInt(todoDatey.getText().toString()),Integer.parseInt(todoDatem.getText().toString()),Integer.parseInt(todoDated.getText().toString())),Double.valueOf(todoDuration.getText().toString()));
                Toast.makeText(CreateTodoView.this, "zay elfol", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(CreateTodoView.this, HomeActivity.class);
                startActivity(i);

            }
        });
    }
}
