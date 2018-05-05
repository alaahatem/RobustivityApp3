package com.robustastudio.robustivityapp.CreateTodo;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.MainActivity;
import com.robustastudio.robustivityapp.Models.Todo;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import okhttp3.MediaType;

/**
 * Created by sa2r_ on 4/18/2018.
 */

public class CreateTodoPresenter implements CreateTodoPresenterInt {
    CreateTodoView view;

    public final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    public CreateTodoPresenter(CreateTodoView view) {
        this.view=view;
    }

    @Override
    public void checkTextField(EditText text, List<String> list) {
        if(!TextUtils.isEmpty(text.getText()))
            list.add(text.getText().toString());
    }

    @Override
    public void addTodo(AppDatabase db, DatabaseReference firebase, String title, final String email, List<String> list, String startTime, Date date, double duration) {
        boolean flag=false;

        if (date.before(new Date())){
            Toast.makeText(view, "Date of todo cant before todays date", Toast.LENGTH_LONG).show();
            flag=true;
        }
        if (!flag) {

            String key = firebase.child("Todos").push().getKey();
            if(key==null){
                Toast.makeText(view,"Can't create" +
                        "check the internet",Toast.LENGTH_LONG).show();
            }else{

                Todo todo=new Todo(key,title,email,list,startTime,date,duration);
                db.todoDao().addTodo(todo);
                firebase.child("Todos").child(title).setValue(todo);

            }


        }
    }
}
