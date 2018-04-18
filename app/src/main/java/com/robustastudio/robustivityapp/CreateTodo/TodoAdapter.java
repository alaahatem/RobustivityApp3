package com.robustastudio.robustivityapp.CreateTodo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.robustastudio.robustivityapp.R;

import java.util.List;

/**
 * Created by sa2r_ on 4/5/2018.
 */

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {
    List<String>todoMembers;

    public TodoAdapter(List<String> todoMembers) {
        this.todoMembers = todoMembers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.create_todo_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.item.setText(todoMembers.get(position));
    }

    @Override
    public int getItemCount() {
        return todoMembers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView item;

        public ViewHolder(View itemView) {
            super(itemView);
            item=itemView.findViewById(R.id.todomembercard);
        }
    }
}
