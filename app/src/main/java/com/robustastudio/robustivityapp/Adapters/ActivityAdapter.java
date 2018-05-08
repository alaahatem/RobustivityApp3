package com.robustastudio.robustivityapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.Models.Activities;
import com.robustastudio.robustivityapp.Models.UserProfile;
import com.robustastudio.robustivityapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by hp on 02/05/2018.
 */

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {
DatabaseReference mDatabaseRef;
List<UserProfile> userProfiles;
AppDatabase db =null;
String tempImage;

    List<Activities> Activities;
    Context ctx;
    public ActivityAdapter(List<com.robustastudio.robustivityapp.Models.Activities> activities , Context ctx, AppDatabase db) {
        Activities = activities;
        this.ctx = ctx;
        this.db =db;
    }



    @Override
    public ActivityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_row,parent,false);
        return new ActivityAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ActivityAdapter.ViewHolder holder, int position) {

        tempImage ="";
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        userProfiles = db.userDao().getAllprofiles();

        holder.activity_content.setText(Activities.get(position).getContent());
        holder.time.setText(Activities.get(position).getDate());
        if(userProfiles!=null)
        for (int i = 0; i <userProfiles.size() ; i++) {

            if (userProfiles.get(i).getEmail().equals(Activities.get(position).getCont())) {
                if (!userProfiles.isEmpty()) {

                    tempImage = userProfiles.get(i).getImage();
                    break;
                }
            }
        }
        if(!tempImage.isEmpty()){
            Picasso.get().load(tempImage).centerCrop().fit().into(holder.image);
        }else{
            Picasso.get().load(R.drawable.theimage).centerCrop().fit().into(holder.image);
        }
//        if(!Activities.get(position).getImage().isEmpty()) {
//            Picasso.get().load(Activities.get(position).getImage()).centerCrop().fit().into(holder.image);
//        }else{
//            Picasso.get().load(R.drawable.theimage).fit().centerCrop().into(holder.image);
//
//        }
    }

    @Override
    public int getItemCount() {
        return null!=Activities?Activities.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView activity_content;
        ImageView image;
        public LinearLayout linearLayout;
        public TextView time;
        public ViewHolder(View itemView) {
            super(itemView);
            activity_content= itemView.findViewById(R.id.activity_content);
           image = itemView.findViewById(R.id.Image_act);
            time= itemView.findViewById(R.id.time);

            linearLayout =itemView.findViewById(R.id.linear_layout_activityfeed);
        }
    }
}
