package com.robustastudio.robustivityapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 26/03/2018.
 */

class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    List<UserProfile> user_profile;

    public UserAdapter(List<UserProfile> user_profile) {
        this.user_profile = user_profile;
    }

    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(UserAdapter.ViewHolder holder, int position) {
    holder.name.setText(user_profile.get(position).getName());
    holder.email.setText(user_profile.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return user_profile.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView email;
        public ViewHolder(View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.name);
            email=itemView.findViewById(R.id.Email);

        }
    }
}
