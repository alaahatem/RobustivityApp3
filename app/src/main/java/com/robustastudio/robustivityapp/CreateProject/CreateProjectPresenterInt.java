package com.robustastudio.robustivityapp.CreateProject;

import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.robustastudio.robustivityapp.Database.AppDatabase;

import java.util.Date;
import java.util.List;

/**
 * Created by sa2r_ on 4/14/2018.
 */

public interface CreateProjectPresenterInt {
    void checkTextField(EditText text,List<String>list);
    void addProject(AppDatabase db, DatabaseReference ref, String name, String type, Date startDate, Date dueDate, List<String>list, String tagLine, String accountName, float projectCost, float contractedCost , float plannedCost);
}
