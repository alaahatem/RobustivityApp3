package com.robustastudio.robustivityapp.ViewAllTodos;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.robustastudio.robustivityapp.Models.Todo;
import com.robustastudio.robustivityapp.R;
import com.robustastudio.robustivityapp.ViewSingleProject.Activity_Project;
import com.robustastudio.robustivityapp.ViewTodo.Activity_Single_Todo;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by MALAK SHAKER on 5/3/2018.
 */

public class Todos_Adapter extends RecyclerView.Adapter<Todos_Adapter.ViewHolder>{

    List<Todo> todo;
    Context ctx;

    public Todos_Adapter(List<Todo> todo, Context ctx) {
        this.todo = todo;
        this.ctx=ctx;

    }

    public Todos_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.create_todo_card,parent,false);

        Todos_Adapter.ViewHolder item = new Todos_Adapter.ViewHolder(v);
        return item;

    }

    @Override
    public int getItemCount() {
        return todo.size();
    }

    public void onBindViewHolder(Todos_Adapter.ViewHolder VH, final int position){
        Log.d(TAG, "onBindViewHolder: ");
        VH.todoName.setText(todo.get(position).getTitle());
        VH.Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ctx, Activity_Single_Todo.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("todoID",todo.get(position).getId());
                ctx.startActivity(i);

            }
        });

    }




    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView todoName;
        List<String> names;

        CardView Card;

        // public ViewHolder (View viewItem,List<String> names,Context ctx){
        public ViewHolder (View viewItem){
            super(viewItem);

            todoName = (TextView) viewItem.findViewById(R.id.todomembercard);
            Card =(CardView) viewItem.findViewById(R.id.todo_card_rec);
        }


    }



}
