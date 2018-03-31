package com.robustastudio.robustivityapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.robustastudio.robustivityapp.Models.Projects;

import java.util.List;

/**
 * Created by MALAK SHAKER on 3/28/2018.
 */

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.ViewHolder>{

    List <String>  projects;

    public ProjectsAdapter(List<String> projects) {
        this.projects = projects;
    }


    public ProjectsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_name,parent,false);
        return new ViewHolder(v);

    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    public void onBindViewHolder(ViewHolder VH, int position){

        VH.projectName.setText(projects.get(position));

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView projectName;

        public ViewHolder (View viewItem){
            super(viewItem);
                projectName = viewItem.findViewById(R.id.projectview);
        }
    }
}
