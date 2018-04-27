package com.robustastudio.robustivityapp.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.robustastudio.robustivityapp.Models.Tasks;


import java.util.List;

/**
 * Created by sa2r_ on 4/2/2018.
 */
@Dao
public interface TaskDao {
    @Insert
    void addTask(Tasks task);
    @Query("SELECT task_name FROM Tasks")
    List<String> viewTasks();
    @Query("SELECT id FROM Tasks WHERE task_project_name=:name AND task_name=:tname")
    int viewTask(String name, String tname);
    @Query("SELECT * FROM Tasks WHERE id=:id")
    Tasks getUser(int id);
    @Query("SELECT *FROM Tasks")
    List<Tasks> getAllTasks();
    @Delete
    void deleteTask(Tasks task);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void updateTask(Tasks task);
}
