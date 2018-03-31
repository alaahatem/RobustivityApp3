package com.robustastudio.robustivityapp;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.robustastudio.robustivityapp.Models.UserProfile;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

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
    public void onBindViewHolder(UserAdapter.ViewHolder holder, final int position) {

        holder.name.setText(user_profile.get(position).getName());

        holder.email.setText(user_profile.get(position).getEmail());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext() ,user_profile.get(position).getName(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(view.getContext(),UsersProfiles.class);

                intent.putExtra("Username", user_profile.get(position).getName());
                intent.putExtra("user_email",user_profile.get(position).getEmail());
                intent.putExtra("phone",user_profile.get(position).getPhone());
                intent.putExtra("Status",user_profile.get(position).getStatus());

                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return user_profile.size();
    }
    public void filterlist(List<UserProfile> filteredList) {

        user_profile = filteredList;

        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView email;
        public LinearLayout linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.name);
            email=itemView.findViewById(R.id.Email);
            linearLayout =itemView.findViewById(R.id.linear_layout);
        }
    }

}
