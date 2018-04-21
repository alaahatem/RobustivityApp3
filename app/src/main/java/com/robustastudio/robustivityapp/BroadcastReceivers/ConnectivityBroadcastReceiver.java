package com.robustastudio.robustivityapp.BroadcastReceivers;

import android.arch.persistence.room.Room;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.robustastudio.robustivityapp.Constants.Constants;
import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.Models.Accounts;
import com.robustastudio.robustivityapp.Models.UserProfile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 21/04/2018.
 */

public class ConnectivityBroadcastReceiver extends BroadcastReceiver {
    private DatabaseReference mDatabase;

    List<UserProfile> userprofiles;
    List<Accounts> accounts;
    boolean user_stored =false;
    boolean account_stored=false;
    public AppDatabase db = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        db = Room.databaseBuilder(context,AppDatabase.class, Constants.AppdatabaseName).allowMainThreadQueries().build();
        userprofiles = db.userDao().getAllprofiles();
        accounts= db.userDao().getAllAccounts();
        DatabaseReference ref = mDatabase.child("user_profile");
        DatabaseReference refac = mDatabase.child("Accounts");
        Synchronize(context,accounts,userprofiles,ref,refac);

    }
    public boolean isOnline (Context context){

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null
        return (netInfo != null && netInfo.isConnected());
    }

    public void Synchronize(final Context context, final List<Accounts>accounts_sync, final List<UserProfile>userProfiles_sync, DatabaseReference refuser, DatabaseReference refacc){
        if(isOnline(context)){
            Toast.makeText(context,"connected",Toast.LENGTH_LONG).show();

            refacc.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        String account_name = postSnapshot.child("name").getValue(String.class);
                        String account_email = postSnapshot.child("email").getValue(String.class);
                        String account_phone = postSnapshot.child("phonenumber").getValue(String.class);
                        String account_address = postSnapshot.child("address").getValue(String.class);
                        Accounts acc = new Accounts(account_name,account_phone,account_address,account_email);
                        if(accounts_sync!=null) {
                                    for (int j = 0; j < accounts_sync.size(); j++) {
                                        if (accounts_sync.get(j).getName().equals(account_name)) {
                                            account_stored = true;
                                        }
                                    }
                                }
                                                if(!account_stored){
                            db.userDao().insertAccounts(acc);
                        }
                        else{
                            db.userDao().updateAccount(account_name,account_email,account_phone,account_address);
                        }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            refuser.addValueEventListener(new ValueEventListener() {
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

                        if(userProfiles_sync!=null) {
                            Toast.makeText(context,email+" "+name,Toast.LENGTH_LONG).show();
                            for (int i = 0; i < userProfiles_sync.size(); i++) {

                                if (userProfiles_sync.get(i).getEmail().equals(email)) {

                                    user_stored = true;
                                }


                            }
                        }
//

                        if(!user_stored){
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


            Toast.makeText(context,"CONNECTED!",Toast.LENGTH_LONG).show();
        }

    }
}
