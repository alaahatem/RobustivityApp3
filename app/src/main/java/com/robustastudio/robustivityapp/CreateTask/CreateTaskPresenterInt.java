package com.robustastudio.robustivityapp.CreateTask;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.robustastudio.robustivityapp.Database.AppDatabase;

import java.util.Date;
import java.util.List;

/**
 * Created by sa2r_ on 4/14/2018.
 */

public interface CreateTaskPresenterInt {
    void addTask(AppDatabase db, DatabaseReference firebase, FirebaseAuth mAuth, String name, String description, String member, Date startDate, Date endDate, float estimatedHours, String projectName);
    List<String> fillMembers(AppDatabase db);
}
