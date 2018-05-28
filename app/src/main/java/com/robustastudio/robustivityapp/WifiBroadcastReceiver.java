package com.robustastudio.robustivityapp;

import android.arch.persistence.room.Room;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.Models.Activities;
import com.robustastudio.robustivityapp.Models.UserProfile;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by hp on 03/04/2018.
 */

public class WifiBroadcastReceiver extends BroadcastReceiver {
    private DatabaseReference mDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;
    static String bssid;
    public FirebaseAuth mAuth;
    List<UserProfile> userprofiles;
    List<Activities> activities;
    @Override
    public void onReceive(Context context, Intent intent) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth =FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                } else {

                }
                // ...
            }
        };
        NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
        if(info != null && info.isConnected()) {

            WifiManager wifiManager = (WifiManager)context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if(wifiManager.getConnectionInfo()!=null) {
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();

                bssid = wifiInfo.getBSSID();
                Toast.makeText(context.getApplicationContext(), bssid, Toast.LENGTH_LONG).show();
            }
            final AppDatabase db = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"robustivity").allowMainThreadQueries().build();
            userprofiles  = db.userDao().getAllprofiles();
            activities = db.activitiesDao().getAllActivities();
            Intent i = new Intent(context, HomeActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("bssid", bssid);

            if(bssid!=null) {
                if(mAuth.getCurrentUser()!=null && userprofiles!=null)
                    if (bssid.equals("44:d9:e7:f3:d8:aa")) {

                        for (int j = 0; j <userprofiles.size() ; j++) {
                            if(mAuth.getCurrentUser().getEmail().equals(userprofiles.get(j).getEmail())){
                                Toast.makeText(context.getApplicationContext(),FirebaseApp.EncodeString(mAuth.getCurrentUser().getEmail()),Toast.LENGTH_LONG).show();
                                userprofiles.get(j).setStatus("Checked in");
                                db.userDao().updateUsers("Checked in",mAuth.getCurrentUser().getEmail());
                                mDatabase.child("user_profile").child(FirebaseApp.EncodeString(mAuth.getCurrentUser().getEmail())).child("status").setValue("Checked in");
//                            HomeActivity.checkin.setText("Check out");
//                            String time= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new java.util.Date());
                                long time = System.currentTimeMillis();
                                Activities activity = new Activities(activities.size(),"Check in",userprofiles.get(j).getName()+ " has Checked in ",userprofiles.get(j).getName(),time);
                                mDatabase.child("Activities").child(String.valueOf(activities.size())).setValue(activity);

                            }
                        }
                    }
            }
        }

    }
}
