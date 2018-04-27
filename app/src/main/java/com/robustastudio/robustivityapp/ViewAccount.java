package com.robustastudio.robustivityapp;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.robustastudio.robustivityapp.Accounts.AccountActivity;
import com.robustastudio.robustivityapp.CreateUserProfile.CreateUserProfActivity;
import com.robustastudio.robustivityapp.EditAccounts.EditAccountActivity;
import com.robustastudio.robustivityapp.ViewProfile.ViewProfileActivity;

public class ViewAccount extends AppCompatActivity {
    ImageView Image;
    TextView emailtv;
    TextView addresstv;
    TextView phonetv;
    TextView nametv;
    TextView sectortv;

    String name,email,address,phone,sector;
    int id;
    Button Edit_account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_account);
         Image = findViewById(R.id.imageView);
         emailtv = findViewById(R.id.emailtv);
         addresstv= findViewById(R.id.addresstv);
         phonetv=findViewById(R.id.phonetv);
         nametv = findViewById(R.id.account_name);
         Edit_account = findViewById(R.id.Edit_account);
         sectortv = findViewById(R.id.sectortv);
        if(getIntent().hasExtra("account_name")&&getIntent().hasExtra("account_email")&&getIntent().hasExtra("account_address")&&getIntent().hasExtra("account_phone")) {
            name = getIntent().getStringExtra("account_name");
            email = getIntent().getStringExtra("account_email");
            address = getIntent().getStringExtra("account_address");
            phone = getIntent().getStringExtra("account_phone");
            sector = getIntent().getStringExtra("account_sector");

            id = getIntent().getIntExtra("account_id",0);
        }

    SetTextViews();
   Edit_account.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           Intent myIntent = new Intent(ViewAccount.this, EditAccountActivity.class);
           myIntent.putExtra("name",nametv.getText().toString() );
           myIntent.putExtra("email",emailtv.getText().toString());
           myIntent.putExtra("phone",phonetv.getText().toString());
           myIntent.putExtra("address",addresstv.getText().toString());
           myIntent.putExtra("sector",sectortv.getText().toString());
           myIntent.putExtra("id",id);

           ViewAccount.this.startActivity(myIntent);
       }
   });
    }
    public void SetTextViews(){

        nametv.setText(name);
        emailtv.setText(email);
        addresstv.setText(address);
        phonetv.setText(phone);
        sectortv.setText(sector);

    }

}
