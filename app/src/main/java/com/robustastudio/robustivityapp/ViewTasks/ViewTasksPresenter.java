package com.robustastudio.robustivityapp.ViewTasks;

import com.robustastudio.robustivityapp.AppDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sa2r_ on 4/18/2018.
 */

public class ViewTasksPresenter implements ViewTasksInt {
    public ViewTasksPresenter() {
    }
    @Override
    public List<String> viewTasks(AppDatabase db) {
        List<String>temp=new ArrayList<>();
        temp=db.taskDao().viewTasks();
        return temp;
    }
}
