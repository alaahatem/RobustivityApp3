package com.robustastudio.robustivityapp.CreateTodo;

import android.text.TextUtils;
import android.widget.EditText;

import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.Models.Todo;

import java.util.Date;
import java.util.List;

/**
 * Created by sa2r_ on 4/18/2018.
 */

public class CreateTodoPresenter implements CreateTodoPresenterInt {
    public CreateTodoPresenter() {
    }

    @Override
    public void checkTextField(EditText text, List<String> list) {
        if(!TextUtils.isEmpty(text.getText()))
            list.add(text.getText().toString());
    }

    @Override
    public void addTodo(AppDatabase db, List<String> list, String startTime, Date date, double duration) {
        Todo todo=new Todo(list,startTime,date,duration);
        db.todoDao().addTodo(todo);
    }
}
