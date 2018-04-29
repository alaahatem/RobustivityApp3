package com.robustastudio.robustivityapp.EditTask;

import android.widget.Toast;

import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.Models.Tasks;

import java.util.Date;

/**
 * Created by sa2r_ on 4/19/2018.
 */

public class EditTaskPresenter implements EditTaskPresenterInt {
    EditTaskView view;

    public EditTaskPresenter(EditTaskView view) {
        this.view = view;
    }

    @Override
    public void taskInfo(AppDatabase db, int id) {
        Tasks task=db.taskDao().getUser(id);
        view.viewInfo(task);
    }

    @Override
    public void editTask(AppDatabase db, Tasks task) {
        boolean flag=false;
        if(task.getDue_date().before(task.getStartDate())){
            Toast.makeText(view, "Due Date is before Start Date", Toast.LENGTH_LONG).show();
            flag=true;
        }
        Date temp=new Date();
        if ((task.getStartDate().before(temp) || task.getDue_date().before(temp))&& !flag) {
            Toast.makeText(view, "Start Date cant be before todays date", Toast.LENGTH_LONG).show();
            flag=true;
        }

        switch (task.getStartDate().getMonth()){
            case 1:;
            case 3:;
            case 5:;
            case 7:;
            case 8:;
            case 10:;
            case 12: if(task.getStartDate().getDay()>31){
                Toast.makeText(view, "Day cant exceed 31 at Start Date", Toast.LENGTH_LONG).show();
                flag=true;
            }break;
            case 2:if (task.getStartDate().getDay()>29){
                Toast.makeText(view, "Day cant exceed 29 at Start Date", Toast.LENGTH_LONG).show();
                flag=true;
            }break;
            case 4:;
            case 6:;
            case 9:;
            case 11:if(task.getStartDate().getDay()>30){
                Toast.makeText(view, "Day cant exceed 30 at Start Date", Toast.LENGTH_LONG).show();
                flag=true;
            }break;
            default:
                Toast.makeText(view, "You entered wrong month number", Toast.LENGTH_LONG).show();
                flag=true;
        }
        switch (task.getDue_date().getMonth()){
            case 1:;
            case 3:;
            case 5:;
            case 7:;
            case 8:;
            case 10:;
            case 12: if(task.getDue_date().getDay()>31){
                Toast.makeText(view, "Day cant exceed 31 at Start Date", Toast.LENGTH_LONG).show();
                flag=true;
            }break;
            case 2:if (task.getDue_date().getDay()>29){
                Toast.makeText(view, "Day cant exceed 29 at Due Date", Toast.LENGTH_LONG).show();
                flag=true;
            }break;
            case 4:;
            case 6:;
            case 9:;
            case 11:if(task.getDue_date().getDay()>30){
                Toast.makeText(view, "Day cant exceed 30 at Start Date", Toast.LENGTH_LONG).show();
                flag=true;
            }break;
            default:
                Toast.makeText(view, "You entered wrong month number", Toast.LENGTH_LONG).show();
                flag=true;
        }
        if(!flag)
            db.taskDao().updateTask(task);
    }
}
