package com.robustastudio.robustivityapp.CreateProject;

import android.arch.persistence.room.Room;
import android.text.TextUtils;
import android.widget.EditText;

import com.robustastudio.robustivityapp.AppDatabase;
import com.robustastudio.robustivityapp.Models.Projects;

import java.util.Date;
import java.util.List;

/**
 * Created by sa2r_ on 4/14/2018.
 */

public class CreateProjectPresenter implements CreateProjectPresenterInt {
    public CreateProjectPresenter() {
    }

    @Override
    public void checkTextField(EditText text,List<String>list) {
        if (!TextUtils.isEmpty(text.getText()))
            list.add(text.getText().toString());
    }

    @Override
    public void addProject(AppDatabase db,String name, String type, Date startDate, Date dueDate, List<String> list, String tagLine, String accountName, float projectCost) {
        Projects project=new Projects(name,type,list,startDate,dueDate,tagLine,accountName,projectCost);
        db.projectDao().addProject(project);
    }
}
