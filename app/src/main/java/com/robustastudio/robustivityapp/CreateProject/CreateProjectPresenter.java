package com.robustastudio.robustivityapp.CreateProject;

import android.arch.persistence.room.Room;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.CreateTodo.CreateTodoView;
import com.robustastudio.robustivityapp.Models.Activities;
import com.robustastudio.robustivityapp.Models.Projects;

import java.util.Date;
import java.util.List;

/**
 * Created by sa2r_ on 4/14/2018.
 */

public class CreateProjectPresenter implements CreateProjectPresenterInt {
    CreateProjectView view;
    public CreateProjectPresenter(CreateProjectView view) {
        this.view=view;
    }

   /* @Override
    public void checkTextField(EditText text,List<String>list) {
        if (!TextUtils.isEmpty(text.getText()))
            list.add(text.getText().toString());
    }*/

    @Override
    public void addProject(AppDatabase db, DatabaseReference ref,int id,String name, String type, Date startDate, Date dueDate, String list, String tagLine, String accountName, float projectCost,float contractedCost, float plannedCost) {
        boolean flag=false;
        Projects project=new Projects(id+1,name,type,list,startDate,dueDate,tagLine,accountName,projectCost,contractedCost, plannedCost);
        if(dueDate.before(startDate)){
            Toast.makeText(view, "Due Date is before Start Date", Toast.LENGTH_LONG).show();
            flag=true;
        }
        Date temp=new Date();
        if ((startDate.before(temp) || dueDate.before(temp))&& !flag) {
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
        switch (dueDate.getMonth()){
            case 1:;
            case 3:;
            case 5:;
            case 7:;
            case 8:;
            case 10:;
            case 12: if(dueDate.getDay()>31){
                Toast.makeText(view, "Day cant exceed 31 at Start Date", Toast.LENGTH_LONG).show();
                flag=true;
            }break;
            case 2:if (dueDate.getDay()>29){
                Toast.makeText(view, "Day cant exceed 29 at Due Date", Toast.LENGTH_LONG).show();
                flag=true;
            }break;
            case 4:;
            case 6:;
            case 9:;
            case 11:if(dueDate.getDay()>30){
                Toast.makeText(view, "Day cant exceed 30 at Start Date", Toast.LENGTH_LONG).show();
                flag=true;
            }break;
            default:
                Toast.makeText(view, "You entered wrong month number", Toast.LENGTH_LONG).show();
                flag=true;
        }
        if (!flag) {
            //db.projectDao().addProject(project);
            ref.child(name).setValue(project);
            Toast.makeText(view, "kollo zal fol !!", Toast.LENGTH_SHORT).show();
        }

    }
    public void addActivity(DatabaseReference firebase , AppDatabase db, String type , String content, String account_name, long time){
        List<Activities> activities;
        activities = db.activitiesDao().getAllActivities();
        Activities activity = new Activities(activities.size(),type,content,account_name,time);
        firebase.child("Activities").child(String.valueOf(activities.size())).setValue(activity);
    }
}
