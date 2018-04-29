package com.robustastudio.robustivityapp.CreateTask;

import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.robustastudio.robustivityapp.AppDatabase;

import java.util.Date;
import java.util.List;

/**
 * Created by sa2r_ on 4/14/2018.
 */

public interface CreateTaskPresenterInt {
    void checkTextField(EditText text, List<String> list);
    void addTask(AppDatabase db,DatabaseReference firebase, String name, String description, String assignee, List<String> list, Date startDate, Date endDate, float estimatedHours, String projectName, int id);
}
