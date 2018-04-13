package com.robustastudio.robustivityapp;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.robustastudio.robustivityapp.CreateProfile.CreateProfileActivity;
import com.robustastudio.robustivityapp.CreateUserProfile.CreateUserProfActivity;
import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.Models.UserProfile;
import com.robustastudio.robustivityapp.ViewProfile.ViewProfileActivity;

import java.util.List;


public class HomeActivity extends AppCompatActivity {
Context context;
    List<UserProfile> userprofiles;
String checkout = "Check out";
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    static Button checkin;
    static String checkedin = "checkout";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Button projectsList = (Button) findViewById(R.id.projectsList);
        Button sectors = (Button) findViewById(R.id.view_sectors);
        Button myprofile = (Button) findViewById(R.id.myprofile);
        Button usersearch = (Button) findViewById(R.id.usersearch);
        Button createuser = (Button) findViewById(R.id.CreateUser);
        Button logout = (Button) findViewById(R.id.logout);




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
    myprofile.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent myIntent = new Intent(HomeActivity.this, ViewProfileActivity.class);
            HomeActivity.this.startActivity(myIntent);



        }
    });

    usersearch.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent myIntent = new Intent(HomeActivity.this, CreateProfileActivity.class);
            HomeActivity.this.startActivity(myIntent);
        }
    });

    createuser.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent myIntent = new Intent(HomeActivity.this,CreateUserProfActivity.class);
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
                Intent myIntent = new Intent(HomeActivity.this, viewProjects.class);
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




        new Thread(new Runnable() {
            @Override

            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        checkin = (Button)findViewById(R.id.checkin);
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

   public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.addAuthStateListener(mAuthListener);}
}
