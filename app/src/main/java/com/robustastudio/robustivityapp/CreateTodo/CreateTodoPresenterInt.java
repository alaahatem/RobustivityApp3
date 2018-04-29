package com.robustastudio.robustivityapp.CreateTodo;

import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.robustastudio.robustivityapp.AppDatabase;

import java.util.Date;
import java.util.List;

/**
 * Created by sa2r_ on 4/18/2018.
 */

public interface CreateTodoPresenterInt {
    void checkTextField(EditText text, List<String> list);
    void addTodo(AppDatabase db, DatabaseReference firebase, List<String>list, String startTime, Date date, double duration);
}
