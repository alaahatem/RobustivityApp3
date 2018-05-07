package com.robustastudio.robustivityapp.ViewTasks;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.Models.Tasks;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sa2r_ on 4/18/2018.
 */

public class ViewTasksPresenter implements ViewTasksInt {
    public ViewTasksPresenter() {
    }
    @Override
    public List<String> viewTasks(final AppDatabase db, DatabaseReference fireBase, String name) {
        List<String>temp= new ArrayList<>();
     /*   fireBase.child("Tasks").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Tasks t= snapshot.getValue(Tasks.class);
                    db.taskDao().addTask(t);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
        temp=db.taskDao().viewTasks(name);
        return temp;
    }
}
