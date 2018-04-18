package com.robustastudio.robustivityapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sa2r_ on 4/10/2018.
 */

public class EditTaskAdapter extends RecyclerView.Adapter<EditTaskAdapter.ViewHolder> {
    List<String> members;

    public EditTaskAdapter(List<String> members) {
        this.members = members;
    }

    @Override
    public EditTaskAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_task_card,parent,false);
        return new EditTaskAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final EditTaskAdapter.ViewHolder holder, final int position) {
        holder.item.setText(members.get(position));
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                members=delete(members,position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,members.size());
                notifyDataSetChanged();

            }
        });
    }

    @Override
    public int getItemCount() {

        return members.size();
    }
    public static List<String> delete(List<String>list,int pos){
        List<String>temp=new ArrayList<>();
        for(int i=0;i<list.size();i++)
            if(i!=pos)
                temp.add(list.get(i));
        return temp;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView item;
        Button delete;
        public ViewHolder(View itemView) {
            super(itemView);
            item= itemView.findViewById(R.id.editMember);
            delete=itemView.findViewById(R.id.deleteMember);
        }
    }
}
