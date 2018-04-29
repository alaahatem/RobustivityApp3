package com.robustastudio.robustivityapp.ViewTask;

import com.robustastudio.robustivityapp.Database.AppDatabase;
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
    public void viewTask(AppDatabase db,String temp,String project) {
        int id=db.taskDao().viewTask(project,temp);

        Tasks task=db.taskDao().getUser(id);
        System.out.println(task.getName()+"--> "+task.getId());
        view.viewTask(String.valueOf(task.getId()),task.getName(),task.getDescription(),task.getAssigne(),task.getMembers(),task.getEstimated_hours(),task.due_date,task.finished_hours,task.StartDate,task.project_name);
    }

    @Override
    public void delete(int id,AppDatabase db) {
        Tasks task=db.taskDao().getUser(id);
        db.taskDao().deleteTask(task);
    }
}
