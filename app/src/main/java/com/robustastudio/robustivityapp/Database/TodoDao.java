package com.robustastudio.robustivityapp.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.robustastudio.robustivityapp.Models.Todo;

import java.util.Date;
import java.util.List;

/**
 * Created by sa2r_ on 4/3/2018.
 */
@Dao
public interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void addTodo(Todo todo);

    @Query("SELECT id FROM Todos WHERE todo_creator_email =:email")
    List<String> getTodos(String email);

    @Query("SELECT * FROM Todos WHERE todo_creator_email =:email")
    List<Todo> getTodos_details(String email);

    @Query("SELECT * FROM Todos WHERE id =:todo_id")
    Todo get_single_todo(String todo_id);

    @Query("DELETE FROM Todos WHERE id =:todo_id")
    void delete_todo(String todo_id);
}
