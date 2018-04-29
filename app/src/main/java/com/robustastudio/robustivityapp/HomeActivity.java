package com.robustastudio.robustivityapp;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.robustastudio.robustivityapp.Adapters.RecyclerTouchItemHelper;
import com.robustastudio.robustivityapp.Adapters.RecyclerTouchItemHelperListener;
import com.robustastudio.robustivityapp.Adapters.TasksAdapter;
import com.robustastudio.robustivityapp.CreateProfile.CreateProfileActivity;
import com.robustastudio.robustivityapp.CreateTask.CreateTaskView;
import com.robustastudio.robustivityapp.CreateTodo.CreateTodoView;
import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.Models.Accounts;
import com.robustastudio.robustivityapp.Models.Projects;
import com.robustastudio.robustivityapp.Models.Sectors;
import com.robustastudio.robustivityapp.Models.Tasks;
import com.robustastudio.robustivityapp.Models.UserProfile;
import com.robustastudio.robustivityapp.ViewProfile.ViewProfileActivity;
import com.robustastudio.robustivityapp.ViewTasks.ViewTasksView;
import com.robustastudio.robustivityapp.View_Sectors.viewSectors;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Constants.Constants;


public class HomeActivity extends AppCompatActivity implements RecyclerTouchItemHelperListener {
Context context;
    List<UserProfile> userprofiles;
String checkout = "Check out";
    private DatabaseReference mDatabase;
    public static  boolean account_stored;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    static Button checkin;
    static String checkedin = "checkout";
    private DrawerLayout mDrawerLayout;
    static AppDatabase db=null;
    List<String > sector_names;
    List<String> projects_id;
    DatabaseReference refac;
    List<String > available;
    List<Accounts> accounts;
    List<String> projects;
    DatabaseReference ref_sectors;
    DatabaseReference ref_projects;
    List<String> engagment_list;
    RecyclerView recyclerView;
    List<Tasks>  tasks;
    List<Tasks>  temptask;
    public TasksAdapter adapter;

    Tabs_Adapter Tabs_Adapter;
    ViewPager mViewPager;

boolean stored;
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
        temptask = new ArrayList<>();
        account_stored =false;
        stored = false;
        recyclerView =findViewById(R.id.recycler_view_tasks);
       // Button projectsList = (Button) findViewById(R.id.projectsList);
        //Button sectors = (Button) findViewById(R.id.view_sectors);
       // Button myprofile = (Button) findViewById(R.id.myprofile);
       // Button usersearch = (Button) findViewById(R.id.usersearch);
       // Button createuser = (Button) findViewById(R.id.CreateUser);
       // Button logout = (Button) findViewById(R.id.logout);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class, Constants.AppdatabaseName).allowMainThreadQueries().build();
        userprofiles = db.userDao().getAllprofiles();
        accounts = db.userDao().getAllAccounts();
        projects= db.userDao().getAllProjects();
        tasks =db.taskDao().getAllTasks();

        DatabaseReference ref = mDatabase.child("user_profile");
        refac = mDatabase.child("Accounts");

        ref_projects =mDatabase.child("Projects");

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

        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        Tabs_Adapter =
                new Tabs_Adapter(
                        getSupportFragmentManager());
        Tabs_Adapter.addFragment(fragmentOne);
        Tabs_Adapter.addFragment(fragmentTwo);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(Tabs_Adapter);

     ///////////////TABS/////////////////


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
                        Toast.makeText(getApplicationContext(),"sec id"+s1.name,Toast.LENGTH_LONG).show();
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
                        db.userDao().insertAll(userp);
                    }
                    else{
                        db.userDao().updateProfile(name,email,phone,status);
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
            if (bssid.equals("58:2a:f7:39:59:f8")) {
            checkin.setText(checkedin);
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


        /*navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();
                        if(menuItem.getItemId() == R.id.projectsList){
                            Intent myIntent = new Intent(HomeActivity.this, CollectionDemoActivity.class);
                            HomeActivity.this.startActivity(myIntent);
                            //menuItem.setChecked(false);
                        }

                        if(menuItem.getItemId() == R.id.myprofile){
                            Intent myIntent = new Intent(HomeActivity.this, ViewProfileActivity.class);
                            HomeActivity.this.startActivity(myIntent);
                        }
                        if(menuItem.getItemId() == R.id.usersearch){
                            Intent myIntent = new Intent(HomeActivity.this, CreateProfileActivity.class);
                            HomeActivity.this.startActivity(myIntent);
                        }
                        if(menuItem.getItemId() == R.id.view_sectors){
                            Intent myIntent = new Intent(HomeActivity.this, viewSectors.class);
                            HomeActivity.this.startActivity(myIntent);
                        }
                        if(menuItem.getItemId() == R.id.createTasknew){
                            Intent myIntent = new Intent(HomeActivity.this, CreateTaskView.class);
                            HomeActivity.this.startActivity(myIntent);
                        }
                        if(menuItem.getItemId() == R.id.createTodonew){
                            Intent myIntent = new Intent(HomeActivity.this, CreateTodoView.class);
                            HomeActivity.this.startActivity(myIntent);
                        }
                        if(menuItem.getItemId() == R.id.viewtaskall){
                            Intent myIntent = new Intent(HomeActivity.this, ViewTasksView.class);
                            HomeActivity.this.startActivity(myIntent);
                        }

                        if(menuItem.getItemId() == R.id.logout){
                            mAuth.signOut();
                        }



                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });*/
    /*myprofile.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent myIntent = new Intent(HomeActivity.this, viewprofile.class);
            HomeActivity.this.startActivity(myIntent);



        }
    });

    usersearch.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent myIntent = new Intent(HomeActivity.this, createProfile.class);
            HomeActivity.this.startActivity(myIntent);
        }
    });

    createuser.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent myIntent = new Intent(HomeActivity.this,createuserprof.class);
            HomeActivity.this.startActivity(myIntent);

        }
    });

    logout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        mAuth.signOut();

        }
    });



        projectsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(HomeActivity.this, Activity_View_Projects.class);
                HomeActivity.this.startActivity(myIntent);



            }
        });

        sectors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(HomeActivity.this, viewSectors.class);
                HomeActivity.this.startActivity(myIntent);



            }
        });
*/



        new Thread(new Runnable() {
            @Override

            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        checkin = (Button)findViewById(R.id.check_in);
                        //checkin = (Button)findViewById(R.id.Checkin);
                        //your code
                        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity").allowMainThreadQueries().build();
                        userprofiles =db.userDao().getAllprofiles();
                        setButton();
                        mDatabase = FirebaseDatabase.getInstance().getReference();
                        checkin.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (checkin.getText().toString().equals("Check in")) {
                                    mDatabase.child("user_profile").child(FirebaseApp.EncodeString(mAuth.getCurrentUser().getEmail())).child("status").setValue("Checked in");
                                    checkin.setText("Check out");
                                    db.userDao().updateUsers("Checked in",mAuth.getCurrentUser().getEmail());
                                }
                                else{
                                    mDatabase.child("user_profile").child(FirebaseApp.EncodeString(mAuth.getCurrentUser().getEmail())).child("status").setValue("Off premises");
                                    checkin.setText("Check in");
                                    db.userDao().updateUsers("off Premises",mAuth.getCurrentUser().getEmail());
                                }
                            }
                        });

                    }
                });
            }
        }).start();





    }
    public  void setButton(){

        for (int i = 0; i <userprofiles.size() ; i++) {
            if(mAuth.getCurrentUser().getEmail().equals(userprofiles.get(i).getEmail())){
                if(userprofiles.get(i).getStatus().equals("Checked in")){
                    checkin.setText("Check out");
                }
                else{
                    checkin.setText("Check in");
                }
            }
        }

    }
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if(viewHolder instanceof TasksAdapter.ViewHolder){
            String name = temptask.get(viewHolder.getAdapterPosition()).getName();
            final Tasks deletedTask = temptask.get(viewHolder.getAdapterPosition());
            final int deleteIndex =viewHolder.getAdapterPosition();

            adapter.removeItem(deleteIndex);

        }
    }

   public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.addAuthStateListener(mAuthListener);}
}
