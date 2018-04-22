package com.robustastudio.robustivityapp.ViewTasks;

import com.robustastudio.robustivityapp.Database.AppDatabase;

import java.util.List;

/**
 * Created by sa2r_ on 4/18/2018.
 */

public interface ViewTasksInt {
    List<String> viewTasks(AppDatabase db);
}
