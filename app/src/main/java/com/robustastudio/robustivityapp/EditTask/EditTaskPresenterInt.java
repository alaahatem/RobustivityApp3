package com.robustastudio.robustivityapp.EditTask;

import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.Models.Tasks;

import java.util.List;

/**
 * Created by sa2r_ on 4/19/2018.
 */

public interface EditTaskPresenterInt {
    void taskInfo(AppDatabase db, String id);
    void editTask(AppDatabase db, Tasks task);
    List<String> fillMembers(AppDatabase db);
}
