package com.robustastudio.robustivityapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.robustastudio.robustivityapp.Models.Search_Model;
import com.robustastudio.robustivityapp.R;
import com.robustastudio.robustivityapp.ViewSingleProject.Activity_Project;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by MALAK SHAKER on 5/2/2018.
 */

public class Search_Adapter extends RecyclerView.Adapter<Search_Adapter.ViewHolder>{


   List<Search_Model> list;
   Context ctx;

    public Search_Adapter(List<Search_Model> list, Context ctx) {
        this.list = list;
        this.ctx=ctx;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public Search_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_account_card,parent,false);

        Search_Adapter.ViewHolder item = new Search_Adapter.ViewHolder(v);
        return item;


    }

    public void onBindViewHolder(ViewHolder VH, final int position){
        Log.d(TAG, "onBindViewHolder: ");
        VH.accountName.setText(list.get(position).getName());


        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ctx);
        VH.projects.setLayoutManager(mLayoutManager);
        VH.projects.setHasFixedSize(true);
        Search_projectsAdapter mAdapter = new Search_projectsAdapter(list.get(position).getList(),ctx);
        VH.projects.setAdapter(mAdapter);

        /*VH.accountName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ctx, Activity_Project.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("projectName",list.get(position).getName());
                ctx.startActivity(i);
            }
        });*/

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView accountName;
        public RecyclerView projects;

        CardView Card;

        // public ViewHolder (View viewItem,List<String> names,Context ctx){
        public ViewHolder (View viewItem){
            super(viewItem);

            accountName = viewItem.findViewById(R.id.Account_name_search);
            projects = viewItem.findViewById(R.id.projects_recView);




          //  Card =(CardView) viewItem.findViewById(R.id.Big_card_search);
        }


    }


}
