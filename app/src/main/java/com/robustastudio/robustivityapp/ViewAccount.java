package com.robustastudio.robustivityapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewAccount extends AppCompatActivity {
    ImageView Image;
    TextView emailtv;
    TextView addresstv;
    TextView phonetv;
    TextView nametv;
    String name,email,address,phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_account);
         Image = findViewById(R.id.imageView);
         emailtv = findViewById(R.id.emailtv);
         addresstv= findViewById(R.id.addresstv);
         phonetv=findViewById(R.id.phonetv);
         nametv = findViewById(R.id.account_name);
        if(getIntent().hasExtra("account_name")&&getIntent().hasExtra("account_email")&&getIntent().hasExtra("account_address")&&getIntent().hasExtra("account_phone")) {
            name = getIntent().getStringExtra("account_name");
            email = getIntent().getStringExtra("account_email");
            address = getIntent().getStringExtra("account_address");
            phone = getIntent().getStringExtra("account_phone");
        }
    SetTextViews();
    }
    public void SetTextViews(){

        nametv.setText(name);
        emailtv.setText(email);
        addresstv.setText(address);
        phonetv.setText(phone);

    }

}
