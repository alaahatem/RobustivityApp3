package com.robustastudio.robustivityapp.CreateTask;

import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.robustastudio.robustivityapp.Database.AppDatabase;
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
    public void addTask(final AppDatabase db, DatabaseReference firebase, FirebaseAuth mAuth, String name, String description, String member, Date startDate, Date endDate, float estimatedHours, String projectName) {
        boolean flag=false;
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
            String key =firebase.push().getKey();
            Tasks task=new Tasks(key,name,description,mAuth.getCurrentUser().getEmail(),member,estimatedHours,endDate,0,startDate,projectName);
            if(key!=null){

                firebase.child("Tasks").child(key).setValue(task);
            }else{

            }
            /*firebase.child("Tasks").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Tasks t=dataSnapshot.getValue(Tasks.class);
                    db.taskDao().addTask(t);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });*/
        }

    }

    @Override
    public List<String> fillMembers(AppDatabase db) {
       List<String> list=db.userDao().getUserEmail();
        return list;
    }
}
