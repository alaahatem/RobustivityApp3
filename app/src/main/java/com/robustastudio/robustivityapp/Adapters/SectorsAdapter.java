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
import com.robustastudio.robustivityapp.ViewAccount;
import com.robustastudio.robustivityapp.ViewProjects.Activity_View_Projects;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by MALAK SHAKER on 4/10/2018.
 */

public class SectorsAdapter extends RecyclerView.Adapter<SectorsAdapter.ViewHolder> {


    List<String> sectors;
    Context ctx;


    public SectorsAdapter(List<String> sectors, Context ctx) {
        this.sectors = sectors;
        this.ctx=ctx;

    }

    //public ViewProjectsAdapter(List<String> projects, Context ctx) {
    //   this.projects = projects;
    //  this.ctx=ctx;

    //}


    public SectorsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sector_row,parent,false);

        SectorsAdapter.ViewHolder item = new SectorsAdapter.ViewHolder(v);
        return item;

        //return new ViewHolder(v,projects,ctx);

    }


    public int getItemCount() {
        return sectors.size();
    }


    public void onBindViewHolder(SectorsAdapter.ViewHolder VH, final int position){
        Log.d(TAG, "onBindViewHolder: ");
        VH.sectorName.setText(sectors.get(position));
        VH.Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ctx, AccountActivity.class);
                i.putExtra("sectorName",sectors.get(position));
                ctx.startActivity(i);

            }
        });

    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView sectorName;
        List<String> names;

        CardView Card;

        // public ViewHolder (View viewItem,List<String> names,Context ctx){
        public ViewHolder (View viewItem){
            super(viewItem);
            // this.names=names;
            //viewItem.setOnClickListener(this);
            sectorName = (TextView) viewItem.findViewById(R.id.sectorName);
            Card =(CardView) viewItem.findViewById(R.id.cardID2);
        }
        // public void onClick(View nv){
        //    int position = getAdapterPosition();
        //    String pname = this.names.get(position);
        //   Intent i = new Intent(ctx, Activity_Project.class);
        //   i.putExtra("projectName",pname);
        //   this.ctx.startActivity(i);

        //}

    }

}
