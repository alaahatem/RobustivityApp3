package com.robustastudio.robustivityapp;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import com.robustastudio.robustivityapp.Models.Todo;

/**
 * Created by sa2r_ on 4/3/2018.
 */
@Dao
public interface TodoDao {
    @Insert
    public void addTodo(Todo todo);
}
