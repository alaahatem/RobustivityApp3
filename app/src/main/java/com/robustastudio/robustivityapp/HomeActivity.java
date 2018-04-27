package com.robustastudio.robustivityapp;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.robustastudio.robustivityapp.Adapters.AccountAdapter;
import com.robustastudio.robustivityapp.Adapters.TasksAdapter;
import com.robustastudio.robustivityapp.CreateProfile.CreateProfileActivity;
import com.robustastudio.robustivityapp.CreateTask.CreateTaskView;
import com.robustastudio.robustivityapp.CreateTask.TaskAdapter;
import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.Models.Accounts;
import com.robustastudio.robustivityapp.Models.Sectors;
import com.robustastudio.robustivityapp.Models.Tasks;
import com.robustastudio.robustivityapp.Models.UserProfile;
import com.robustastudio.robustivityapp.ViewProfile.ViewProfileActivity;
import com.robustastudio.robustivityapp.ViewProjects.Activity_View_Projects;
import com.robustastudio.robustivityapp.ViewTasks.ViewTasksView;
import com.robustastudio.robustivityapp.View_Sectors.viewSectors;

import java.util.ArrayList;
import java.util.List;

import Constants.Constants;


public class HomeActivity extends AppCompatActivity {
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
    DatabaseReference refac;
    List<String > available;
    List<Tasks>  tasks;
    List<Tasks>  temptask;
    List<Accounts> accounts;
    RecyclerView recyclerView;
    public TasksAdapter adapter;
boolean stored;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mDatabase = FirebaseDatabase.getInstance().getReference();
mAuth = FirebaseAuth.getInstance();
        recyclerView =findViewById(R.id.recycler_view_tasks);
        sector_names = new ArrayList<>();
        temptask = new ArrayList<>();
        DatabaseReference reference = mDatabase.child("Sectors");
        account_stored =false;
        stored = false;
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
        tasks =db.taskDao().getAllTasks();
        DatabaseReference ref = mDatabase.child("user_profile");
        refac = mDatabase.child("Accounts");


        for (int i = 0; i <tasks.size() ; i++) {
            for (int j = 0; j <tasks.get(i).getMembers().size() ; j++) {
                Toast.makeText(getApplicationContext(),tasks.get(i).getMembers().get(j),Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),mAuth.getCurrentUser().getDisplayName(),Toast.LENGTH_LONG).show();
                if(tasks.get(i).getMembers().get(j).equals("'"+mAuth.getCurrentUser().getDisplayName()+"'")){

                    temptask.add(tasks.get(i));
                }
            }
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TasksAdapter(temptask);
        recyclerView.setAdapter(adapter);




        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d :dataSnapshot.getChildren()){
                    sector_names.add(d.getKey());
                    available = db.userDao().getAllSectors();


                    if(!available.contains(d.getKey())){

                        Sectors s1 = new Sectors(d.getKey());
                        db.userDao().insertSector(s1);
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


        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();
                        if(menuItem.getItemId() == R.id.projectsList){
                            Intent myIntent = new Intent(HomeActivity.this, Activity_View_Projects.class);
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
                });
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
                        checkin = (Button)findViewById(R.id.Checkin);
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


        for (int i = 0; i <tasks.size() ; i++) {
            Toast.makeText(getApplicationContext(),tasks.get(i).getName(),Toast.LENGTH_LONG).show();
        }

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


   public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.addAuthStateListener(mAuthListener);}
}
