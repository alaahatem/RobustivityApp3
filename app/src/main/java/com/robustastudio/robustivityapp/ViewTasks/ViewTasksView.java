package com.robustastudio.robustivityapp.ViewTasks;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
    ViewTasksPresenter presenter=new ViewTasksPresenter();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_all_tasks);
        recycle=findViewById(R.id.allTasks);
        recycle.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity")
                .allowMainThreadQueries().build();
        tasks=presenter.viewTasks(db);
        recycle.setAdapter(new ViewTasksAdapter(tasks));

    }
}
