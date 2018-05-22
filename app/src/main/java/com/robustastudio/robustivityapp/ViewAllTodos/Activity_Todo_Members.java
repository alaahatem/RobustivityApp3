package com.robustastudio.robustivityapp.ViewAllTodos;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.robustastudio.robustivityapp.Adapters.EngagementListAdapter;
import com.robustastudio.robustivityapp.Adapters.ProjectsAdapter;
import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.Models.Todo;
import com.robustastudio.robustivityapp.R;

import java.util.List;

/**
 * Created by MALAK SHAKER on 5/3/2018.
 */

public class Activity_Todo_Members extends AppCompatActivity {

    String todo_id;
    Todo todo;
    TextView name,start_time_hour,duration,Date;
    RecyclerView members;
    private EngagementListAdapter mAdapter;
    List<String> mem_list;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_todo_members);

        members = findViewById(R.id.todo_members_rec);


        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            todo_id = extras.getString("todoID");
            // temp=extras.getString("taskName");
        } else {
            todo_id = "";
        }

        final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "robustivity").allowMainThreadQueries().build();
        todo = db.todoDao().get_single_todo(todo_id);
        mem_list= todo.getMembers();
        for(int i=0;i<mem_list.size();i++){
            mem_list.set(i,mem_list.get(i).replace("'",""));
        }

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        members.setLayoutManager(mLayoutManager);
        members.setHasFixedSize(true);

        mAdapter = new EngagementListAdapter(todo.getMembers());
        members.setAdapter(mAdapter);
    }
}
