package com.robustastudio.robustivityapp.CreateTodo;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.robustastudio.robustivityapp.AppDatabase;
import com.robustastudio.robustivityapp.Models.Todo;

import java.util.Date;
import java.util.List;

/**
 * Created by sa2r_ on 4/18/2018.
 */

public class CreateTodoPresenter implements  CreateTodoPresenterInt {
    CreateTodoView view;
    public CreateTodoPresenter(CreateTodoView view) {
        this.view=view;
    }

    @Override
    public void checkTextField(EditText text, List<String> list) {
        if(!TextUtils.isEmpty(text.getText()))
            list.add(text.getText().toString());
    }

    @Override
    public void addTodo(AppDatabase db, DatabaseReference firebase, List<String> list, String startTime, Date date, double duration) {
        boolean flag=false;
        Todo todo=new Todo(list,startTime,date,duration);
        if (date.before(new Date())){
            Toast.makeText(view, "Date of todo cant before todays date", Toast.LENGTH_LONG).show();
            flag=true;
        }
        if (!flag) {
            db.todoDao().addTodo(todo);
            firebase.child("Todos").push().setValue(todo);

        }
    }
}
