package com.robustastudio.robustivityapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button myprofile = (Button)findViewById(R.id.myprofile);
        Button usersearch= (Button) findViewById(R.id.usersearch);
    myprofile.setOnClickListener(new View.OnClickListener() {
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
    }
}
