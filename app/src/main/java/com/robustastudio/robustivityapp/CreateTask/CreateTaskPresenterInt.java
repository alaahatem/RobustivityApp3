package com.robustastudio.robustivityapp.CreateTask;

import android.widget.EditText;

import com.robustastudio.robustivityapp.Database.AppDatabase;

import java.util.Date;
import java.util.List;

/**
 * Created by sa2r_ on 4/14/2018.
 */

public interface CreateTaskPresenterInt {
    void checkTextField(EditText text, List<String> list);
    void addTask(AppDatabase db, String name, String description, String assignee, List<String> list, Date startDate, Date endDate, float estimatedHours, float finishedHours, String projectName,int days);
}
