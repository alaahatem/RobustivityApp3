package com.robustastudio.robustivityapp;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.robustastudio.robustivityapp.Models.UserProfile;

import java.util.List;

public class viewprofile extends AppCompatActivity {
    TextView emailtv ;
    TextView nametv;
    List<UserProfile> userprofiles;
    String UserEmail;
    TextView userphone;
    TextView userstatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewprofile);
        emailtv = findViewById(R.id.viewEmail);
        nametv = findViewById(R.id.viewName);
        userphone=findViewById(R.id.viewphone);
        userstatus=findViewById(R.id.viewStatus);
        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity").allowMainThreadQueries().build();
        userprofiles  = db.userDao().getAllprofiles();
        UserEmail= MainActivity.account.getEmail();

        showProfile();
    }
    public void showProfile(){
        for (int i = 0; i <userprofiles.size() ; i++) {
            if(userprofiles.get(i).getEmail().equals(UserEmail)){
                emailtv.setText(userprofiles.get(i).getEmail());
                nametv.setText(userprofiles.get(i).getName());
                userphone.setText(userprofiles.get(i).getPhone());
                userstatus.setText(userprofiles.get(i).getStatus());

            }
        }




    }
}
