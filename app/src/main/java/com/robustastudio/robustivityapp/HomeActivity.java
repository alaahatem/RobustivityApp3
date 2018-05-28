package com.robustastudio.robustivityapp;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.robustastudio.robustivityapp.Adapters.RecyclerTouchItemHelper;
import com.robustastudio.robustivityapp.Adapters.RecyclerTouchItemHelperListener;
import com.robustastudio.robustivityapp.Adapters.TasksAdapter;
import com.robustastudio.robustivityapp.CreateProfile.CreateProfileActivity;
import com.robustastudio.robustivityapp.CreateTask.CreateTaskView;
import com.robustastudio.robustivityapp.CreateTodo.CreateTodoView;
import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.Models.Accounts;
import com.robustastudio.robustivityapp.Models.Activities;
import com.robustastudio.robustivityapp.Models.Projects;
import com.robustastudio.robustivityapp.Models.Sectors;
import com.robustastudio.robustivityapp.Models.Tasks;
import com.robustastudio.robustivityapp.Models.Todo;
import com.robustastudio.robustivityapp.Models.UserProfile;
import com.robustastudio.robustivityapp.ViewProfile.ViewProfileActivity;
import com.robustastudio.robustivityapp.ViewTasks.ViewTasksView;
import com.robustastudio.robustivityapp.View_Sectors.viewSectors;

import org.apache.poi.hssf.record.formula.functions.T;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Constants.Constants;


public class HomeActivity extends AppCompatActivity implements RecyclerTouchItemHelperListener  {
Context context;
    DatabaseReference ref;
    List<UserProfile> userprofiles;
String checkout = "Check out";
    private DatabaseReference mDatabase;
    public static  boolean account_stored;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public Spinner checkin;
    static String checkedin = "checkout";
    private DrawerLayout mDrawerLayout;
    static AppDatabase db=null;
    List<String > sector_names;
    List<String> projects_id;
    DatabaseReference refac;
    DatabaseReference refact;
    List<String > available;
    List<Accounts> accounts;
    List<String> projects;
    DatabaseReference ref_sectors;
    DatabaseReference ref_projects;
    DatabaseReference ref_todos;
    List<String> engagment_list;
    RecyclerView recyclerView;
    List<Tasks>  tasks;
    List<Tasks>  temptask;
    List<Activities> activities;
    public TasksAdapter adapter;

    Tabs_Adapter Tabs_Adapter;
    ViewPager mViewPager;

    List<String> todos_id;
    List<String> available_todos;
boolean firstime=false;
boolean stored;
    boolean activity_stored;
    ValueEventListener myValueEventListner;
    List<String> Status= new ArrayList<>();
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Override

    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home);
        setContentView(R.layout.activity_tabs);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        sector_names = new ArrayList<>();
         ref_sectors =mDatabase.child("Sectors");
        engagment_list = new ArrayList<String >();
        projects_id = new ArrayList<String >();
        available = new ArrayList<String >();
        todos_id= new ArrayList<String>();
        checkin = findViewById(R.id.check_in);
        available_todos = new ArrayList<String >();
        temptask = new ArrayList<>();
        account_stored =false;
        stored = false;
        recyclerView =findViewById(R.id.recycler_view_tasks);
        Status.add("Off Premises");
        Status.add("Checked in");

        Status.add("Working from Home");


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,Status );
        checkin.setAdapter(adapter);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class, Constants.AppdatabaseName).allowMainThreadQueries().build();
        userprofiles = db.userDao().getAllprofiles();
        accounts = db.userDao().getAllAccounts();
        projects= db.userDao().getAllProjects();
        tasks =db.taskDao().getAllTasks();
        available_todos=db.todoDao().getTodos(mAuth.getCurrentUser().getEmail());
//        mSwipeRefreshLayout = findViewById(R.id.swiperefresh);
//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                Toast.makeText(getApplicationContext(),"refresh from home ", Toast.LENGTH_SHORT).show();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        mSwipeRefreshLayout.setRefreshing(false);
//                    }
//                },2000);
//            }
//        });
        setDefaultValues();
        for (int i=0;i<available_todos.size();i++){
           // Toast.makeText(getApplicationContext(), available_todos.get(i), Toast.LENGTH_SHORT).show();
        }



        ref = mDatabase.child("user_profile");
        refac = mDatabase.child("Accounts");
        refact =mDatabase.child("Activities");
        ref_projects =mDatabase.child("Projects");
        ref_todos=mDatabase.child("Todos");


        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
       // Log.d(TAG,FirebaseInstanceId.getInstance().getToken());
      //  Toast.makeText(getApplicationContext(),refreshedToken,Toast.LENGTH_LONG).show();


        /*for (int i = 0; i <tasks.size() ; i++) {
            Date Taskdate = tasks.get(i).getDue_date();
            Date today = new Date();

            SimpleDateFormat parser = new SimpleDateFormat("EEE, MM d,yyyy");
            parser.format(today);
            parser.format(Taskdate);

            int days = (int) ((today.getTime()- Taskdate.getTime())/(1000 * 60 *60 * 24));


            Toast.makeText(getApplicationContext(),String.valueOf(days),Toast.LENGTH_LONG).show();
            for (int j = 0; j <tasks.get(i).getMembers().size() ; j++) {

                if(tasks.get(i).getMembers().get(j).equals("'"+mAuth.getCurrentUser().getDisplayName()+"'")){
                    if(days+693989==0)

                        temptask.add(tasks.get(i));
                }
            }
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TasksAdapter(temptask);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback
                = new RecyclerTouchItemHelper(0,ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);*/


       ///////////////// TABS////////////////


        Reminders_fragment fragmentOne = new Reminders_fragment();
        Shortcuts_fragment fragmentTwo= new Shortcuts_fragment();
        NewsFeed fragmentThree = new NewsFeed();

        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        Tabs_Adapter =
                new Tabs_Adapter(
                        getSupportFragmentManager());
        Tabs_Adapter.addFragment(fragmentOne);
        Tabs_Adapter.addFragment(fragmentTwo);
        Tabs_Adapter.addFragment(fragmentThree);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(Tabs_Adapter);

     ///////////////TABS/////////////////



        mDatabase.child("Tasks").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Tasks t= snapshot.getValue(Tasks.class);
                    db.taskDao().addTask(t);

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        ref_projects.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d :dataSnapshot.getChildren()){
                    projects_id.add(d.getKey());
                    projects = db.userDao().getAllProjects();
                    /*for (int i = 0; i <projects.size() ; i++) {
                        Toast.makeText(getApplicationContext(),available.get(i),Toast.LENGTH_LONG).show();
                    }*/

                    if(!projects.contains(d.getKey())){
                        Projects pnew = d.getValue(Projects.class);
                       // Toast.makeText(getApplicationContext(),"id"+pnew.projectid,Toast.LENGTH_LONG).show();
                        db.projectDao().addProject(pnew);
                       // Toast.makeText(getApplicationContext(),pnew.projectid,Toast.LENGTH_LONG).show();
                        // mpresenter.update_sectors(db,s1);
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ref_todos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d :dataSnapshot.getChildren()) {
                  //  Toast.makeText(getApplicationContext(),mAuth.getCurrentUser().getEmail(),Toast.LENGTH_LONG).show();

                    if (d.child("email").getValue(String.class).equals(mAuth.getCurrentUser().getEmail()) ) {
                        available_todos = db.todoDao().getTodos(mAuth.getCurrentUser().getEmail());

                        if (!available_todos.contains(d.getKey())) {

                            if(d.hasChild("members")){
                                Todo todonew = d.getValue(Todo.class);
                                // Toast.makeText(getApplicationContext(),"id"+pnew.projectid,Toast.LENGTH_LONG).show();
                                db.todoDao().addTodo(todonew);
                               // Toast.makeText(getApplicationContext(),d.child("title").getValue(String.class)+"inserted",Toast.LENGTH_LONG).show();

                            }else
                            {
                                List<String> mm =new ArrayList<>();
                                Todo todonew = new Todo(d.child("id").getValue(String.class),d.child("title").getValue(String.class),d.child("email").getValue(String.class),
                                        mm,d.child("starttime").getValue(String.class),d.child("date").getValue(Date.class),
                                        d.child("duration").getValue(Integer.class));
                                db.todoDao().addTodo(todonew);
                               // Toast.makeText(getApplicationContext(),d.child("title").getValue(String.class)+"inserted",Toast.LENGTH_LONG).show();

                            }

                            // Toast.makeText(getApplicationContext(),pnew.projectid,Toast.LENGTH_LONG).show();
                            // mpresenter.update_sectors(db,s1);
                        }
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        ref_sectors.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d :dataSnapshot.getChildren()){
                    sector_names.add(d.getKey());
                    available = db.userDao().getAllSectors();
                  /*  for (int i = 0; i <available.size() ; i++) {
                        Toast.makeText(getApplicationContext(),available.get(i),Toast.LENGTH_LONG).show();
                    }*/

                    if(!available.contains(d.getKey())){

                        Sectors s1 = (d.getValue(Sectors.class));
                        db.userDao().insertSector(s1);
                       // Toast.makeText(getApplicationContext(),"sec id"+s1.name,Toast.LENGTH_LONG).show();
                        // mpresenter.update_sectors(db,s1);
                    }


                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        refac.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (accounts != null) {
                    accounts = db.userDao().getAllAccounts();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        account_stored = false;
                        String address = postSnapshot.child("address").getValue(String.class);
                        String email = postSnapshot.child("email").getValue(String.class);
                        String name = postSnapshot.child("name").getValue(String.class);
                        String phone = postSnapshot.child("phonenumber").getValue(String.class);
                        String sector = postSnapshot.child("sector").getValue(String.class);
                        int id = postSnapshot.child("id").getValue(int.class);

                        Accounts acc = new Accounts(name, phone, address, email, sector,id);


                        if (accounts != null) {
                            for (int i = 0; i < accounts.size(); i++) {

                                if (accounts.get(i).getId()==id) {
                                    account_stored = true;

                                }
                            }
                        }
                        if (!account_stored) {


                            db.userDao().insertAccounts(acc);
                        } else {


                            db.userDao().updateAccount(name, email, phone, address, sector ,id );
                        }


                    }
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        refact.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    activities = db.activitiesDao().getAllActivities();
                    if (activities != null) {
                        activity_stored = false;
                        int id = postSnapshot.child("id").getValue(int.class);
                        String type = postSnapshot.child("type").getValue(String.class);
                        String content = postSnapshot.child("content").getValue(String.class);
                        String cont = postSnapshot.child("cont").getValue(String.class);
                        long date = postSnapshot.child("date").getValue(long.class);
                        Activities activity = new Activities(id, type, content, cont,date);

                        for (int i = 0; i < activities.size(); i++) {
                            if (activities.get(i).getId() == id) {
                                activity_stored = true;


                            }
                        }
                        if (!activity_stored) {
                            db.activitiesDao().insertActivities(activity);
                        }

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        /*ref_todos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d :dataSnapshot.getChildren()){

                    if(d.child("email").getKey()==mAuth.getCurrentUser().getEmail()){

                    }
                   // projects_id.add();
                    projects = db.userDao().getAllProjects();
                    *//*for (int i = 0; i <projects.size() ; i++) {
                        Toast.makeText(getApplicationContext(),available.get(i),Toast.LENGTH_LONG).show();
                    }*//*

                    if(!projects.contains(d.getKey())){
                        Projects pnew = d.getValue(Projects.class);
                        // Toast.makeText(getApplicationContext(),"id"+pnew.projectid,Toast.LENGTH_LONG).show();
                        db.projectDao().addProject(pnew);
                        // Toast.makeText(getApplicationContext(),pnew.projectid,Toast.LENGTH_LONG).show();
                        // mpresenter.update_sectors(db,s1);
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/


        ref.addValueEventListener(new ValueEventListener() {

            List<UserProfile> usertemp= new ArrayList<>();
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String email = postSnapshot.child("email").getValue(String.class);
                    String image = postSnapshot.child("image").getValue(String.class);
                    String name= postSnapshot.child("name").getValue(String.class);
                    String phone = postSnapshot.child("phone").getValue(String.class);
                    String status = postSnapshot.child("status").getValue(String.class);
                    UserProfile userp = new UserProfile(image,name,phone,email,status);
                    for (int i = 0; i < userprofiles.size() ; i++) {

                        if(userprofiles.get(i).getEmail().equals(email)){

                            stored= true;
                        }

                    }
                    if(!stored){
                        if(userp.getEmail()!=null)
                        db.userDao().insertAll(userp);
                    }
                    else{
                        db.userDao().updateProfile(name,email,phone,status,image);
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mAuth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        String bssid = intent.getStringExtra("bssid");

        if(bssid!=null) {
            if (bssid.equals("44:d9:e7:f3:d8:aa")) {
            checkin.setSelection(1);
            }
        }
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(HomeActivity.this, MainActivity.class));
                }
            }
        };
        checkin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String thename="";

                if(mAuth.getCurrentUser()!=null && userprofiles!=null)
        for (int i = 0; i <userprofiles.size(); i++) {
            if(userprofiles.get(i).getEmail().equals(mAuth.getCurrentUser().getEmail()))
                 thename = userprofiles.get(i).getName();
        }
                List<Activities> activities = db.activitiesDao().getAllActivities();
                if(checkin.getSelectedItem().equals("Checked in")){
//            checkin.setBackgroundColor(Color.parseColor("#7CFC00"));
//            String time= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new java.util.Date());
                    long time = System.currentTimeMillis();

                    Activities activity = new Activities(activities.size(),"Check in", thename+" has checked in",mAuth.getCurrentUser().getEmail(),time);
                    mDatabase.child("user_profile").child(FirebaseApp.EncodeString(mAuth.getCurrentUser().getEmail())).child("status").setValue("Checked in");
                    mDatabase.child("Activities").child(String.valueOf(activities.size())).setValue(activity);

                }
                else if(checkin.getSelectedItem().equals("Off Premises")){
//            String time= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new java.util.Date());
//            checkin.setBackgroundColor(Color.parseColor("#ff0000"));
                    long time = System.currentTimeMillis();
                    Activities activity = new Activities(activities.size(),"Check out", thename+" has checked out",mAuth.getCurrentUser().getEmail(),time);
                    mDatabase.child("user_profile").child(FirebaseApp.EncodeString(mAuth.getCurrentUser().getEmail())).child("status").setValue("Off Premises");
                    mDatabase.child("Activities").child(String.valueOf(activities.size())).setValue(activity);
                }
                else{
//            String time= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new java.util.Date());
                    long time = System.currentTimeMillis();
                    Activities activity = new Activities(activities.size(),"From Home", thename+" is working from home",mAuth.getCurrentUser().getEmail(),time);
                    mDatabase.child("user_profile").child(FirebaseApp.EncodeString(mAuth.getCurrentUser().getEmail())).child("status").setValue("Working from Home");
                    mDatabase.child("Activities").child(String.valueOf(activities.size())).setValue(activity);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        new Thread(new Runnable() {
//            @Override
//
//            public void run() {
//
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        checkin = (Button)findViewById(R.id.check_in);
//
//                        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity").allowMainThreadQueries().build();
//                        userprofiles =db.userDao().getAllprofiles();
//                        setButton();
//                        mDatabase = FirebaseDatabase.getInstance().getReference();
//                        checkin.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                String Image ="";
//                                if(userprofiles!=null &&mAuth.getCurrentUser()!=null)
//                                    for (int i = 0; i <userprofiles.size() ; i++) {
//                                        if(mAuth.getCurrentUser().getEmail().equals(userprofiles.get(i).getEmail()))
//                                            Image = userprofiles.get(i).getName();
//                                    }
//                                activities = db.activitiesDao().getAllActivities();
//
//                                if (checkin.getText().toString().equals("Check in")) {
//                                    String time= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new java.util.Date());
//                                    Activities activity = new Activities(activities.size(),"Check in", mAuth.getCurrentUser().getDisplayName()+" has checked in",Image,time);
//                                    mDatabase.child("user_profile").child(FirebaseApp.EncodeString(mAuth.getCurrentUser().getEmail())).child("status").setValue("Checked in");
//                                    mDatabase.child("Activities").child(String.valueOf(activities.size())).setValue(activity);                                    checkin.setText("Check out");
//                                    db.userDao().updateUsers("Checked in",mAuth.getCurrentUser().getEmail());
//                                }
//                                else{
//                                    String time= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new java.util.Date());
//                                    Activities activity = new Activities(activities.size(),"Check out", mAuth.getCurrentUser().getDisplayName()+" has checked out",Image,time);
//                                    mDatabase.child("Activities").child(String.valueOf(activities.size())).setValue(activity);
//                                    mDatabase.child("user_profile").child(FirebaseApp.EncodeString(mAuth.getCurrentUser().getEmail())).child("status").setValue("Off premises");                                    checkin.setText("Check in");
//                                    db.userDao().updateUsers("off Premises",mAuth.getCurrentUser().getEmail());
//                                }
//                            }
//                        });
//
//                    }
//                });
//            }
//        }).start();

//                        checkin.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                String current_status = "";
//                                String Image ="";
//                                if(userprofiles!=null &&mAuth.getCurrentUser()!=null)
//                                    for (int i = 0; i <userprofiles.size() ; i++) {
//                                        if(mAuth.getCurrentUser().getEmail().equals(userprofiles.get(i).getEmail()))
//                                            Image = userprofiles.get(i).getName();
//                                        current_status = userprofiles.get(i).getStatus();
//                                        Toast.makeText(getApplicationContext(),userprofiles.get(i).getStatus(),Toast.LENGTH_LONG).show();
//                                        Toast.makeText(getApplicationContext(),current_status+ " user",Toast.LENGTH_LONG).show();
//                                    }
//                                activities = db.activitiesDao().getAllActivities();
//
//                                        if(current_status.equals("Off Premises")) {
//
//                                            String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new java.util.Date());
//                                            Activities activity = new Activities(activities.size(), "Check in", mAuth.getCurrentUser().getDisplayName() + " has checked in", Image, time);
//                                            mDatabase.child("user_profile").child(FirebaseApp.EncodeString(mAuth.getCurrentUser().getEmail())).child("status").setValue("Checked in");
//                                            mDatabase.child("Activities").child(String.valueOf(activities.size())).setValue(activity);
////
//                                            db.userDao().updateUsers("Checked in", mAuth.getCurrentUser().getEmail());
//                                        }
//
//                                else{
//                                            Toast.makeText(getApplicationContext(),"Clicked not off",Toast.LENGTH_LONG).show();
//                                    String time= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new java.util.Date());
//                                    Activities activity = new Activities(activities.size(),"Check out", mAuth.getCurrentUser().getDisplayName()+" has checked out",Image,time);
//                                    mDatabase.child("Activities").child(String.valueOf(activities.size())).setValue(activity);
//                                    mDatabase.child("user_profile").child(FirebaseApp.EncodeString(mAuth.getCurrentUser().getEmail())).child("status").setValue("Off Premises");
////                                    checkin.setText("Check in");
//                                    db.userDao().updateUsers("off Premises",mAuth.getCurrentUser().getEmail());
//                                }
//                            }
//                        });

                    }







//    public  void setButton(){
//
//        for (int i = 0; i <userprofiles.size() ; i++) {
//            if(mAuth.getCurrentUser().getEmail().equals(userprofiles.get(i).getEmail())){
//                if(userprofiles.get(i).getStatus().equals("Checked in")){
//                    checkin.setSelection(1);
//                }
//                else{
//                    checkin.setSelection(0);
//                }
//            }
//        }
//
//    }
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if(viewHolder instanceof TasksAdapter.ViewHolder){
            String name = temptask.get(viewHolder.getAdapterPosition()).getName();
            final Tasks deletedTask = temptask.get(viewHolder.getAdapterPosition());
            final int deleteIndex =viewHolder.getAdapterPosition();

            adapter.removeItem(deleteIndex);

        }
    }
    public void setDefaultValues(){
        String status ="";
        if(userprofiles!=null && mAuth.getCurrentUser()!=null)
            for (int i = 0; i <userprofiles.size() ; i++) {
                if(userprofiles.get(i).getEmail().equals(mAuth.getCurrentUser().getEmail())){
                    status = userprofiles.get(i).getStatus();
                }
            }
        if( status.equals("Checked in")) {
            checkin.setSelection(1);
//        checkin.setBackgroundColor(Color.parseColor("#7CFC00"));
        }else if (status.equals("Off Premises")){
            checkin.setSelection(0);
//            checkin.setBackgroundColor(Color.parseColor("#ff0000"));
        }

        else checkin.setSelection(2);
    }

    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.addAuthStateListener(mAuthListener);}


}
