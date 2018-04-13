package com.robustastudio.robustivityapp.UserProfiles;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.robustastudio.robustivityapp.R;

public class UsersProfilesImpl extends AppCompatActivity implements UserProfiles {
    TextView viewname ;
    TextView viewemail;
    TextView viewphone;
    TextView viewstatus;
    Button ping ;
    private UserProfilePresenter mUserProfilePresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mUserProfilePresenter = new UserProfilePresenterImpl(this );
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_profiles);
        getIncomingIntent();
        ping = findViewById(R.id.ping);
        ping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUserProfilePresenter.sendNotification();
            }
        });

    }

    public void getIncomingIntent() {
        if (getIntent().hasExtra("Username") && getIntent().hasExtra("user_email")&&getIntent().hasExtra("phone")&&getIntent().hasExtra("Status")) {
            String  username = getIntent().getStringExtra("Username");

            String  Email = getIntent().getStringExtra("user_email");
            String  phone = getIntent().getStringExtra("phone");
            String  status = getIntent().getStringExtra("Status");

             setTextViews(username,Email,phone,status);
        }
    }

    public void setTextViews(String username , String email ,String phone , String status){
        Toast.makeText(this,username,Toast.LENGTH_LONG);
        viewname = findViewById(R.id.viewName);
        viewemail = findViewById(R.id.viewEmail);
        viewphone=findViewById(R.id.viewPhone);
        viewstatus=findViewById(R.id.viewStatus);
        viewname.setText(username);
        viewemail.setText(email);
        viewstatus.setText(status);
        viewphone.setText(phone);

    }


}