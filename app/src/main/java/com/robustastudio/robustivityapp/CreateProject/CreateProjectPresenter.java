package com.robustastudio.robustivityapp.CreateProject;

import android.text.TextUtils;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.robustastudio.robustivityapp.Database.AppDatabase;
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
    public void addProject(AppDatabase db, DatabaseReference reference,String name, String type, Date startDate, Date dueDate, List<String> list, String tagLine, String accountName, float projectCost, float contractedCost, float plannedCost) {
        Projects project=new Projects(name,type,list,startDate,dueDate,tagLine,accountName,projectCost,contractedCost,plannedCost);
        db.projectDao().addProject(project);
        reference.child(name).setValue(project);

    }
}
