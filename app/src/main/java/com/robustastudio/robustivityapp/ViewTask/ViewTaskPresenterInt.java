package com.robustastudio.robustivityapp.ViewTask;

import com.robustastudio.robustivityapp.Database.AppDatabase;

/**
 * Created by sa2r_ on 4/18/2018.
 */

public interface ViewTaskPresenterInt {
    void viewTask(AppDatabase db, String temp,String temp2);
    void delete(int id, AppDatabase db);
}
