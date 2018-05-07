package com.robustastudio.robustivityapp.ViewTasks;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sa2r_ on 4/18/2018.
 */

public class ViewTasksView extends AppCompatActivity {
    List<String>tasks=new ArrayList<>();
    RecyclerView recycle;
    String projectName;
    ViewTasksPresenter presenter=new ViewTasksPresenter();
    DatabaseReference fireBase;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_all_tasks);
        fireBase=FirebaseDatabase.getInstance().getReference();
        Bundle extras = getIntent().getExtras();
        projectName="";


        if (extras != null) {
            projectName = extras.getString("projectName");
        }

        recycle=findViewById(R.id.allTasks);
        recycle.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity")
                .allowMainThreadQueries().build();
        tasks=presenter.viewTasks(db,fireBase,projectName);
        recycle.setAdapter(new ViewTasksAdapter(tasks,projectName));

    }
}
