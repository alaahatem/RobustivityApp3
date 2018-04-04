package com.robustastudio.robustivityapp;

import android.arch.persistence.room.Room;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import com.robustastudio.robustivityapp.Models.UserProfile;

import java.util.List;

/**
 * Created by hp on 03/04/2018.
 */

public class WifiBroadcastReceiver extends BroadcastReceiver {
    static String bssid;
    List<UserProfile> userprofiles;
    @Override
    public void onReceive(Context context, Intent intent) {

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
                context.getApplicationContext().startActivity(i);
            if(bssid!=null) {
                if (bssid.equals("58:2a:f7:39:59:f8")) {
                    for (int j = 0; j <userprofiles.size() ; j++) {
                        if(MainActivity.mAuth.getCurrentUser().getEmail().equals(userprofiles.get(j).getEmail())){
                            userprofiles.get(j).setStatus("Checked In");
                            Toast.makeText(context.getApplicationContext(),userprofiles.get(j).getStatus(),Toast.LENGTH_LONG).show();
                            db.userDao().updateUsers("Checked in",userprofiles.get(j).getEmail());
                        }
                    }
                }
            }

            }
    }
}