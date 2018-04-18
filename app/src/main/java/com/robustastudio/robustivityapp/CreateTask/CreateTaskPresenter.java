package com.robustastudio.robustivityapp.CreateTask;

import android.text.TextUtils;
import android.widget.EditText;

import com.robustastudio.robustivityapp.AppDatabase;
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
    public void addTask(AppDatabase db, String name, String description, String assignee, List<String> list, Date startDate, Date endDate, float estimatedHours, String projectName) {
        Tasks task=new Tasks(name,description,assignee,list,estimatedHours,endDate,0,startDate,projectName);
        db.taskDao().addTask(task);
    }
}
