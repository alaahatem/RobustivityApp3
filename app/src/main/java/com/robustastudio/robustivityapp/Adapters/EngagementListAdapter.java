package com.robustastudio.robustivityapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.robustastudio.robustivityapp.R;

import java.util.List;

/**
 * Created by MALAK SHAKER on 4/1/2018.
 */

public class EngagementListAdapter extends RecyclerView.Adapter<EngagementListAdapter.ViewHolder>  {

    List<String> team;

    public EngagementListAdapter(List<String> team) {
        this.team = team;
    }

    public EngagementListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_row,parent,false);

        return new ViewHolder(v);
    }

    public void onBindViewHolder(ViewHolder VH, int position){

        VH.member.setText(team.get(position));

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView member;

        public ViewHolder(View viewItem) {
            super(viewItem);
            member = viewItem.findViewById(R.id.member);
        }
    }

    public int getItemCount() {
        return team.size();
    }


}
