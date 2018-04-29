package com.robustastudio.robustivityapp.ViewTasks;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.robustastudio.robustivityapp.R;
import com.robustastudio.robustivityapp.ViewTask.ViewTaskView;

import java.util.List;

/**
 * Created by sa2r_ on 4/5/2018.
 */

public class ViewTasksAdapter extends RecyclerView.Adapter<ViewTasksAdapter.ViewHolder> {
    List<String> names;
    String project;
    public ViewTasksAdapter(List<String> names,String project) {
        this.names = names;
        this.project=project;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_all_tasks_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.item.setText(names.get(position));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),ViewTaskView.class);
                intent.putExtra("taskName", names.get(position));
                intent.putExtra("projectName",project);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView item;
        CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.tasknamecard);
            cardView=itemView.findViewById(R.id.cardView);
        }
    }
}
