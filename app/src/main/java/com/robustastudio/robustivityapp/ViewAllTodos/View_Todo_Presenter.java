package com.robustastudio.robustivityapp.ViewAllTodos;

import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.Models.Todo;

import java.util.List;

/**
 * Created by MALAK SHAKER on 5/3/2018.
 */

public class View_Todo_Presenter {

    Todo_view mview;
    List<Todo> todos;


    View_Todo_Presenter(Todo_view mview){
        this.mview=mview;
    }

    public void get_all_todos(AppDatabase db, String email){

        todos  = db.todoDao().getTodos_details(email);

        if(todos.isEmpty()){
            mview.List_Empty();

        }else{
            mview.get_details(todos);

        }

    }


}
