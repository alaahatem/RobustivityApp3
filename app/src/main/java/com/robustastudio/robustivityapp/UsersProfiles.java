package com.robustastudio.robustivityapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class UsersProfiles extends AppCompatActivity {
    TextView viewname ;
    TextView viewemail;
    TextView viewphone;
    TextView viewstatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_profiles);
        getIncomingIntent();

    }

    public void getIncomingIntent() {
        if (getIntent().hasExtra("Username") && getIntent().hasExtra("user_email")&&getIntent().hasExtra("phone")&&getIntent().hasExtra("Status")) {
            String  username = getIntent().getStringExtra("username");
            String  Email = getIntent().getStringExtra("user_email");
            String  phone = getIntent().getStringExtra("phone");
            String  status = getIntent().getStringExtra("status");
            Toast.makeText(this,username,Toast.LENGTH_LONG);
             setTextViews(username,Email,phone,status);
        }
    }

    public void setTextViews(String username , String email ,String phone , String status){
        viewname = findViewById(R.id.viewName);
        viewemail = findViewById(R.id.viewEmail);
        viewphone=findViewById(R.id.viewphone);
        viewstatus=findViewById(R.id.viewStatus);
        viewname.setText(username);
        viewemail.setText(email);


    }


}