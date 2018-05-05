package com.robustastudio.robustivityapp.CreateTask;

import android.text.TextUtils;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.Models.Activities;
import com.robustastudio.robustivityapp.Models.Tasks;

import java.util.Date;
import java.util.List;

/**
 * Created by sa2r_ on 4/14/2018.
 */

public class CreateTaskPresenter implements CreateTaskPresenterInt {

    public CreateTaskPresenter() {
    }

    @Override
    public void checkTextField(EditText text, List<String> list) {
        if(!TextUtils.isEmpty(text.getText()))
            list.add(text.getText().toString());
    }

    @Override
    public void addTask(AppDatabase db, String name, String description, String assignee, List<String> list, Date startDate, Date endDate, float estimatedHours,float finishedHours, String projectName,int days) {
        Tasks task=new Tasks(name,description,assignee,list,estimatedHours,endDate,projectName,finishedHours,startDate,days);
        db.taskDao().addTask(task);
    }

    public void addActivity(DatabaseReference mDatabase, AppDatabase db, String type, String content, String assignee, String time){
        List<Activities> activities;
//        List<UserProfile> userProfiles;
//        String Image ="";
//        userProfiles = db.userDao().getAllprofiles();
        activities= db.activitiesDao().getAllActivities();
//        for (int i = 0; i < userProfiles.size(); i++) {
//            if (userProfiles.get(i).getName().equals(assignee)) {
//                if (userProfiles.get(i).getImage() != null)
//                    Image = userProfiles.get(i).getImage();
//
//            }
//        }

        Activities activity = new Activities(activities.size(), type, content,assignee ,time);

        mDatabase.child("Activities").child(String.valueOf(activities.size())).setValue(activity);


    }
}

