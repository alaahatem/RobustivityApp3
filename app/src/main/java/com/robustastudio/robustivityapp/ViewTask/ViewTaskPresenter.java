package com.robustastudio.robustivityapp.ViewTask;

import android.content.Intent;

import com.robustastudio.robustivityapp.AppDatabase;
import com.robustastudio.robustivityapp.Models.Tasks;

/**
 * Created by sa2r_ on 4/18/2018.
 */

public class ViewTaskPresenter implements ViewTaskPresenterInt{
    ViewTaskView view=new ViewTaskView();

    public ViewTaskPresenter(ViewTaskView view) {
        this.view=view;
    }

    @Override
    public void viewTask(AppDatabase db,String temp) {
        int id=db.taskDao().viewTask("test",temp);
        System.out.println(id);
        Tasks task=db.taskDao().getUser(id);
        System.out.println(task.getName()+"--> "+task.getId());
        view.viewTask(String.valueOf(task.getId()),task.getName(),task.getDescription(),task.getAssignee(),task.getMembers(),task.getEstimated_hours(),task.due_date,task.finished_hours,task.startDate,task.getProjectname());
    }

    @Override
    public void delete(int id,AppDatabase db) {
        Tasks task=db.taskDao().getUser(id);
        db.taskDao().deleteTask(task);
    }
}
