package com.robustastudio.robustivityapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.robustastudio.robustivityapp.Accounts.AccountActivity;
import com.robustastudio.robustivityapp.R;
import com.robustastudio.robustivityapp.ViewSingleProject.Activity_Project;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by MALAK SHAKER on 5/2/2018.
 */

public class Search_projectsAdapter extends RecyclerView.Adapter<Search_projectsAdapter.ViewHolder> {

    List<String> names;
    Context ctx;



    public Search_projectsAdapter(List<String> names, Context ctx)
    {
        this.names = names;
        this.ctx=ctx;
    }

    public Search_projectsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_project_name,parent,false);

        Search_projectsAdapter.ViewHolder item = new Search_projectsAdapter.ViewHolder(v);
        return item;

        //return new ViewHolder(v,projects,ctx);

    }


    public int getItemCount() {
        return names.size();
    }


    public void onBindViewHolder(ViewHolder VH, final int position){
        Log.d(TAG, "onBindViewHolder: ");
        VH.projectName.setText(names.get(position));

        VH.projectCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ctx, Activity_Project.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("projectName",names.get(position));
                ctx.startActivity(i);

            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView projectName;
        CardView projectCard;


        // public ViewHolder (View viewItem,List<String> names,Context ctx){
        public ViewHolder (View viewItem){
            super(viewItem);

            projectName = (TextView) viewItem.findViewById(R.id.search_pname);
            projectCard = viewItem.findViewById(R.id.pname_card);
        }


    }


}
