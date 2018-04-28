package com.robustastudio.robustivityapp.Adapters;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLayoutListener;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;
import com.robustastudio.robustivityapp.Models.Tasks;
import com.robustastudio.robustivityapp.R;

import java.util.List;

/**
 * Created by hp on 26/04/2018.
 */

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {
    List<Tasks> Tasks;
    Context context;
    SparseBooleanArray expandState =  new SparseBooleanArray();


    public TasksAdapter(List<Tasks> tasks) {
       this.Tasks = tasks;
        for (int i = 0; i <tasks.size() ; i++) {
            expandState.append(i,false);
        }
    }
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    @Override
    public TasksAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tasks_row,parent,false);
        return new TasksAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TasksAdapter.ViewHolder holder, final int position) {
//        holder.setIsRecyclable(false);
        holder.TaskViewParent.setText(Tasks.get(position).getName());
        holder.expandableLinearLayout.setInRecyclerView(true);
        holder.expandableLinearLayout.setExpanded(expandState.get(position));
        holder.expandableLinearLayout.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onAnimationStart() {

            }

            @Override
            public void onAnimationEnd() {

            }

            @Override
            public void onPreOpen() {
            changeRotate(holder.button,0f,180f).start();
            expandState.put(position,true);
            }

            @Override
            public void onPreClose() {
                changeRotate(holder.button,180f,0f).start();
                expandState.put(position,false);
            }


        });
        holder.button.setRotation(expandState.get(position)?180f:0f);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.expandableLinearLayout.toggle();
            }
        });
        holder.TaskViewChild.setText("Description : "+Tasks.get(position).getDescription()+"\n" +"Assigned by : "+Tasks.get(position).getAssigne());
        }

    private ObjectAnimator changeRotate(RelativeLayout button, float from, float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(button,"rotation",from ,to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }
    public void removeItem(int position){
        Tasks.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Tasks task , int position){
        Tasks.add(position,task);
    }
    @Override
    public int getItemCount() {
        return null!=Tasks?Tasks.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView TaskViewParent;
        public TextView TaskViewChild;
        public ExpandableLinearLayout expandableLinearLayout;
        public RelativeLayout button;
        public ViewHolder(View itemView) {
            super(itemView);
            TaskViewParent= itemView.findViewById(R.id.TaskTextView);
            TaskViewChild=itemView.findViewById(R.id.TaskTextDesc);
            button =itemView.findViewById(R.id.theButton);
            expandableLinearLayout = itemView.findViewById(R.id.expandableLayout);
        }
    }
}
