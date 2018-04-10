package com.robustastudio.robustivityapp;

import android.arch.persistence.room.Room;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.robustastudio.robustivityapp.Models.UserProfile;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by hp on 03/04/2018.
 */

public class WifiBroadcastReceiver extends BroadcastReceiver {
    private DatabaseReference mDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;
    static String bssid;
    public FirebaseAuth mAuth;
    List<UserProfile> userprofiles;
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
            // Do your work.

            // e.g. To check the Network Name or other info:
            WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            bssid = wifiInfo.getBSSID();

            final AppDatabase db = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"robustivity").allowMainThreadQueries().build();
            userprofiles  = db.userDao().getAllprofiles();
                Intent i = new Intent(context, HomeActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("bssid", bssid);

            if(bssid!=null) {
                if (bssid.equals("58:2a:f7:39:59:f8")) {
                    for (int j = 0; j <userprofiles.size() ; j++) {
                        if(mAuth.getCurrentUser().getEmail().equals(userprofiles.get(j).getEmail())){
                           Toast.makeText(context.getApplicationContext(),FirebaseApp.EncodeString(mAuth.getCurrentUser().getEmail()),Toast.LENGTH_LONG).show();
                            userprofiles.get(j).setStatus("Checked in");
                            db.userDao().updateUsers("Checked in",mAuth.getCurrentUser().getEmail());
                        mDatabase.child("user_profile").child(FirebaseApp.EncodeString(mAuth.getCurrentUser().getEmail())).child("status").setValue("Checked in");
//                            HomeActivity.checkin.setText("Check out");


                        }
                    }
                    }
                }
            }

            }
    }
