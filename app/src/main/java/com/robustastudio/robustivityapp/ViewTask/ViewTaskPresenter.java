package com.robustastudio.robustivityapp.ViewTask;

import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.Models.Tasks;

/**
 * Created by sa2r_ on 4/18/2018.
 */

public class ViewTaskPresenter implements ViewTaskPresenterInt {
    ViewTaskView view=new ViewTaskView();

    public ViewTaskPresenter(ViewTaskView view) {
        this.view=view;
    }

    @Override
    public void viewTask(AppDatabase db,String temp,String temp2) {
        String id = db.taskDao().viewTask(temp2,temp);
        System.out.println(id);
        Tasks task= db.taskDao().getUser(id);
        //System.out.println(task.getName()+"--> "+task.getId());
        view.viewTask(task);
    }

    @Override
    public void delete(String id,AppDatabase db) {
        Tasks task=db.taskDao().getUser(id);
        db.taskDao().deleteTask(task);
    }
}
