package com.robustastudio.robustivityapp.ViewAllTodos;

import com.robustastudio.robustivityapp.Models.Todo;

import java.util.List;

/**
 * Created by MALAK SHAKER on 5/3/2018.
 */

public interface Todo_view {

    void List_Empty();
    void get_details(List<Todo> x);
}
