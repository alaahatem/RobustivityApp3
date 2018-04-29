package com.robustastudio.robustivityapp.CreateTask;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.robustastudio.robustivityapp.AppDatabase;
import com.robustastudio.robustivityapp.Models.Tasks;

import java.util.Date;
import java.util.List;

/**
 * Created by sa2r_ on 4/14/2018.
 */

public class CreateTaskPresenter implements CreateTaskPresenterInt {
    CreateTaskView view;
    public CreateTaskPresenter(CreateTaskView view) {
        this.view=view;
    }
    @Override
    public void checkTextField(EditText text, List<String> list) {
        if(!TextUtils.isEmpty(text.getText()))
            list.add(text.getText().toString());
    }

    @Override
    public void addTask(AppDatabase db,DatabaseReference firebase, String name, String description, String assignee, List<String> list, Date startDate, Date endDate, float estimatedHours, String projectName, int id) {
        boolean flag=false;
        Tasks task=new Tasks(id+1,name,description,assignee,list,estimatedHours,endDate,0,startDate,projectName);

        if(endDate.before(startDate)){
            Toast.makeText(view, "Due Date is before Start Date", Toast.LENGTH_LONG).show();
            flag=true;
        }
        Date temp=new Date();
        if ((startDate.before(temp) || startDate.before(temp))&& !flag) {
            Toast.makeText(view, "Start Date cant be before todays date", Toast.LENGTH_LONG).show();
            flag=true;
        }

        switch (startDate.getMonth()){
            case 1:;
            case 3:;
            case 5:;
            case 7:;
            case 8:;
            case 10:;
            case 12: if(startDate.getDay()>31){
                Toast.makeText(view, "Day cant exceed 31 at Start Date", Toast.LENGTH_LONG).show();
                flag=true;
            }break;
            case 2:if (startDate.getDay()>29){
                Toast.makeText(view, "Day cant exceed 29 at Start Date", Toast.LENGTH_LONG).show();
                flag=true;
            }break;
            case 4:;
            case 6:;
            case 9:;
            case 11:if(startDate.getDay()>30){
                Toast.makeText(view, "Day cant exceed 30 at Start Date", Toast.LENGTH_LONG).show();
                flag=true;
            }break;
            default:
                Toast.makeText(view, "You entered wrong month number", Toast.LENGTH_LONG).show();
                flag=true;
        }
        switch (endDate.getMonth()){
            case 1:;
            case 3:;
            case 5:;
            case 7:;
            case 8:;
            case 10:;
            case 12: if(endDate.getDay()>31){
                Toast.makeText(view, "Day cant exceed 31 at Start Date", Toast.LENGTH_LONG).show();
                flag=true;
            }break;
            case 2:if (endDate.getDay()>29){
                Toast.makeText(view, "Day cant exceed 29 at Due Date", Toast.LENGTH_LONG).show();
                flag=true;
            }break;
            case 4:;
            case 6:;
            case 9:;
            case 11:if(endDate.getDay()>30){
                Toast.makeText(view, "Day cant exceed 30 at Start Date", Toast.LENGTH_LONG).show();
                flag=true;
            }break;
            default:
                Toast.makeText(view, "You entered wrong month number", Toast.LENGTH_LONG).show();
                flag=true;
        }
        System.out.println(flag);

        if(!flag) {
           // db.taskDao().addTask(task);
            firebase.child("Tasks").child(String.valueOf(id+1)).setValue(task);
        }

    }
}
