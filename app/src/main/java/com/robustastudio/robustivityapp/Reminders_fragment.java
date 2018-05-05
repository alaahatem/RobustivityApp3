package com.robustastudio.robustivityapp;


import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.robustastudio.robustivityapp.Accounts.AccountActivity;
import com.robustastudio.robustivityapp.ActivityFeed.ActivityFeed;
import com.robustastudio.robustivityapp.Adapters.RecyclerTouchItemHelper;
import com.robustastudio.robustivityapp.Adapters.RecyclerTouchItemHelperListener;
import com.robustastudio.robustivityapp.Adapters.TasksAdapter;
import com.robustastudio.robustivityapp.CreateAccounts.CreateActivity;
import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.Models.Tasks;
import com.robustastudio.robustivityapp.Models.Todo;
import com.robustastudio.robustivityapp.Models.UserProfile;
import com.robustastudio.robustivityapp.ViewProfile.ViewProfileActivity;
import com.robustastudio.robustivityapp.View_Sectors.viewSectors;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Constants.Constants;

import static com.robustastudio.robustivityapp.MainActivity.mAuth;
import static com.robustastudio.robustivityapp.MainActivity.user;

/**
 * Created by MALAK SHAKER on 4/28/2018.
 */
// Instances of this class are fragments representing a single
// object in our collection.
public class Reminders_fragment extends Fragment implements RecyclerTouchItemHelperListener {
    private FirebaseAuth mAuth;
    RecyclerView recyclerView;
    List<Tasks> tasks;
    List<Tasks>  temptask;
    List<UserProfile>  userProfiles;
    public TasksAdapter adapter;
    private DrawerLayout mDrawerLayout;
    Button Activities;
String tempname;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(
                R.layout.activity_home, container, false);

        temptask = new ArrayList<>();
        tasks= new ArrayList<>();
        recyclerView = rootView.findViewById(R.id.recycler_view_tasks);
        mDrawerLayout = rootView.findViewById(R.id.drawer_layout);
        final AppDatabase db = Room.databaseBuilder(getActivity().getApplicationContext(),AppDatabase.class, Constants.AppdatabaseName).allowMainThreadQueries().build();
        tasks =db.taskDao().getAllTasks();
        mAuth = FirebaseAuth.getInstance();
        Activities = rootView.findViewById(R.id.Activities);
        Activities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), ActivityFeed.class);
                Reminders_fragment.this.startActivity(myIntent);
            }
        });
        userProfiles= db.userDao().getAllprofiles();
        if(userProfiles!=null && mAuth.getCurrentUser()!=null)
        for (int i = 0; i <userProfiles.size() ; i++) {
            if(userProfiles.get(i).getEmail().equals(mAuth.getCurrentUser().getEmail()))
                tempname = userProfiles.get(i).getName();
        }
        for (int i = 0; i <tasks.size() ; i++) {
            Date Taskdate = tasks.get(i).getDue_date();
            Date today = new Date();

            SimpleDateFormat parser = new SimpleDateFormat("EEE, MM d,yyyy");
            parser.format(today);
            parser.format(Taskdate);

            int days = (int) ((today.getTime()- Taskdate.getTime())/(1000 * 60 *60 * 24));


            for (int j = 0; j <tasks.get(i).getMembers().size() ; j++) {

                if(tasks.get(i).getMembers().get(j).equals("'"+tempname+"'")){
                    if(Taskdate.getYear()==(today.getYear()+1900) && Taskdate.getMonth()==(today.getMonth()+1) && Taskdate.getDay()== (today.getDay()-3))
                        Toast.makeText(getActivity().getApplicationContext(),"ITS TODAY",Toast.LENGTH_LONG).show();
                        temptask.add(tasks.get(i));
                }
            }
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        adapter = new TasksAdapter(temptask);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback
                = new RecyclerTouchItemHelper(0,ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);


        return rootView;
    }

    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if(viewHolder instanceof TasksAdapter.ViewHolder){
            String name = temptask.get(viewHolder.getAdapterPosition()).getName();
            final Tasks deletedTask = temptask.get(viewHolder.getAdapterPosition());
            final int deleteIndex =viewHolder.getAdapterPosition();

            adapter.removeItem(deleteIndex);

        }
    }

}