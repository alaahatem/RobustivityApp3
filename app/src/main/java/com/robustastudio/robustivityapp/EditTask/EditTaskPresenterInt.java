package com.robustastudio.robustivityapp.EditTask;

import com.robustastudio.robustivityapp.AppDatabase;
import com.robustastudio.robustivityapp.Models.Tasks;

/**
 * Created by sa2r_ on 4/19/2018.
 */

public interface EditTaskPresenterInt {
    void taskInfo(AppDatabase db,int id);
    void editTask(AppDatabase db, Tasks task);
}
