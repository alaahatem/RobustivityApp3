package com.robustastudio.robustivityapp;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.robustastudio.robustivityapp.Accounts.AccountActivity;
import com.robustastudio.robustivityapp.BroadcastReceivers.ConnectivityBroadcastReceiver;
import com.robustastudio.robustivityapp.Constants.Constants;
import com.robustastudio.robustivityapp.CreateProfile.CreateProfileActivity;
import com.robustastudio.robustivityapp.CreateUserProfile.CreateUserProfActivity;
import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.Models.Accounts;
import com.robustastudio.robustivityapp.Models.UserProfile;
import com.robustastudio.robustivityapp.ViewProfile.ViewProfileActivity;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity {
   public static boolean user_stored;
   public static  boolean account_stored;
    Context context;

    String checkout = "Check out";
    private DatabaseReference mDatabase;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    static Button checkin;
    static String checkedin = "checkout";
    DatabaseReference ref;
    DatabaseReference refac;
    Button view_account;
    List<UserProfile> userprofiles;
    List<Accounts> accounts;
    boolean stored;
    public AppDatabase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        stored = false;
        account_stored =false;
        user_stored =false;
        Button projectsList = (Button) findViewById(R.id.projectsList);
        Button sectors = (Button) findViewById(R.id.view_sectors);
        Button myprofile = (Button) findViewById(R.id.myprofile);
        Button usersearch = (Button) findViewById(R.id.usersearch);
        Button createuser = (Button) findViewById(R.id.CreateUser);
        Button logout = (Button) findViewById(R.id.logout);
        view_account = findViewById(R.id.viewaccount);
         db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, Constants.AppdatabaseName).allowMainThreadQueries().build();
         ref = mDatabase.child("user_profile");
         refac = mDatabase.child("Accounts");

        userprofiles = db.userDao().getAllprofiles();
        accounts = db.userDao().getAllAccounts();
        mAuth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        String bssid = intent.getStringExtra("bssid");

        if (bssid != null) {
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
                Intent myIntent = new Intent(HomeActivity.this, CreateUserProfActivity.class);
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
        view_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(HomeActivity.this, AccountActivity.class);
                HomeActivity.this.startActivity(myIntent);
            }
        });


        new Thread(new Runnable() {
            @Override

            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        checkin = (Button) findViewById(R.id.checkin);
                        //your code
                        final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "robustivity").allowMainThreadQueries().build();
                        userprofiles = db.userDao().getAllprofiles();
                        setButton();
                        mDatabase = FirebaseDatabase.getInstance().getReference();
                        checkin.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (checkin.getText().toString().equals("Check in")) {
                                    mDatabase.child("user_profile").child(FirebaseApp.EncodeString(mAuth.getCurrentUser().getEmail())).child("status").setValue("Checked in");
                                    checkin.setText("Check out");
                                    db.userDao().updateUsers("Checked in", mAuth.getCurrentUser().getEmail());
                                } else {
                                    mDatabase.child("user_profile").child(FirebaseApp.EncodeString(mAuth.getCurrentUser().getEmail())).child("status").setValue("Off premises");
                                    checkin.setText("Check in");
                                    db.userDao().updateUsers("off Premises", mAuth.getCurrentUser().getEmail());
                                }
                            }
                        });

                    }
                });
            }
        }).start();


//
Synchronize(getApplicationContext());
    }

    public void setButton() {

        for (int i = 0; i < userprofiles.size(); i++) {
            if (mAuth.getCurrentUser().getEmail().equals(userprofiles.get(i).getEmail())) {
                if (userprofiles.get(i).getStatus().equals("Checked in")) {
                    checkin.setText("Check out");
                } else {
                    checkin.setText("Check in");
                }
            }
        }

    }

    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.addAuthStateListener(mAuthListener);
    }

    public boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null
        return (netInfo != null && netInfo.isConnected());
    }

    public void Synchronize(final Context context) {

           refac.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                   if (accounts != null) {
                       for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                           account_stored = false;
                           String address = postSnapshot.child("address").getValue(String.class);
                           String email = postSnapshot.child("email").getValue(String.class);
                           String name = postSnapshot.child("name").getValue(String.class);
                           String phone = postSnapshot.child("phonenumber").getValue(String.class);
                           String sector = postSnapshot.child("sector").getValue(String.class);
                           Accounts acc = new Accounts(name, phone, address, email, sector);

                           if (accounts != null) {
                               for (int i = 0; i < accounts.size(); i++) {
                                   Toast.makeText(getApplicationContext(), accounts.get(i).getName(), Toast.LENGTH_LONG).show();
                                   if (accounts.get(i).getName().equals(name)) {
                                       account_stored = true;

                                   }
                               }
                           }
                           if (!account_stored) {

                                Toast.makeText(getApplicationContext(),name+" "+" not stored",Toast.LENGTH_LONG).show();
                               db.userDao().insertAccounts(acc);
                           } else {
                               Toast.makeText(getApplicationContext(),name+" "+"  stored",Toast.LENGTH_LONG).show();
                               db.userDao().updateAccount(name, email, phone, address, sector);
                           }


                       }
                   }
               }


               @Override
               public void onCancelled(DatabaseError databaseError) {

               }
           });
            ref.addValueEventListener(new ValueEventListener() {
                List<UserProfile> usertemp = new ArrayList<>();

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        user_stored =false;
                        String email = postSnapshot.child("email").getValue(String.class);
                        String image = postSnapshot.child("image").getValue(String.class);
                        String name = postSnapshot.child("name").getValue(String.class);
                        String phone = postSnapshot.child("phone").getValue(String.class);
                        String status = postSnapshot.child("status").getValue(String.class);

                        UserProfile userp = new UserProfile(image, name, phone, email, status);

                        if (userprofiles != null) {
//                            Toast.makeText(context, email + " " + name, Toast.LENGTH_LONG).show();
                            for (int i = 0; i < userprofiles.size(); i++) {

                                if (userprofiles.get(i).getEmail().equals(email)) {

                                    user_stored = true;

                                }


                            }
                        }
//

                        if (!user_stored) {
                            db.userDao().insertAll(userp);
                        } else {
                            db.userDao().updateProfile(name,email,phone,status);
                        }

                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

//
//            Toast.makeText(context, "CONNECTED!", Toast.LENGTH_LONG).show();
        }


    }

