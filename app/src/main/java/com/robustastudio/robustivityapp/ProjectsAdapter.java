package com.robustastudio.robustivityapp;

<<<<<<< Updated upstream
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.robustastudio.robustivityapp.Models.Projects;

import java.util.List;

=======
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import static android.content.ContentValues.TAG;

>>>>>>> Stashed changes
/**
 * Created by MALAK SHAKER on 3/28/2018.
 */

<<<<<<< Updated upstream
public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.ViewHolder>{

    List <String>  projects;

    public ProjectsAdapter(List<String> projects) {
        this.projects = projects;
    }


    public ProjectsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_name,parent,false);
        return new ViewHolder(v);

=======
public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.ViewHolder> {

    List <String>  projects;
    Context ctx;


    public ProjectsAdapter(List<String> projects,Context ctx) {
      this.projects = projects;
      this.ctx=ctx;

    }

    //public ProjectsAdapter(List<String> projects, Context ctx) {
     //   this.projects = projects;
      //  this.ctx=ctx;

    //}


    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_name,parent,false);

        ViewHolder item = new ViewHolder(v);
        return item;

        //return new ViewHolder(v,projects,ctx);
>>>>>>> Stashed changes
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

<<<<<<< Updated upstream
    public void onBindViewHolder(ViewHolder VH, int position){

        VH.projectName.setText(projects.get(position));

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView projectName;

        public ViewHolder (View viewItem){
            super(viewItem);
                projectName = viewItem.findViewById(R.id.projectview);
        }
=======
    public void onBindViewHolder(ViewHolder VH, final int position){
        Log.d(TAG, "onBindViewHolder: ");
        VH.projectName.setText(projects.get(position));
        VH.Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   Intent i = new Intent(ctx, Project.class);
                   i.putExtra("projectName",projects.get(position));
                   ctx.startActivity(i);

            }
        });

    }
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView projectName;
        List<String> names;

        CardView Card;

       // public ViewHolder (View viewItem,List<String> names,Context ctx){
        public ViewHolder (View viewItem){
            super(viewItem);
           // this.names=names;
            //viewItem.setOnClickListener(this);
            projectName = (TextView) viewItem.findViewById(R.id.projectview);
            Card =(CardView) viewItem.findViewById(R.id.cardID);
        }
       // public void onClick(View nv){
        //    int position = getAdapterPosition();
        //    String pname = this.names.get(position);
         //   Intent i = new Intent(ctx, Project.class);
         //   i.putExtra("projectName",pname);
         //   this.ctx.startActivity(i);

        //}
>>>>>>> Stashed changes
    }
}
