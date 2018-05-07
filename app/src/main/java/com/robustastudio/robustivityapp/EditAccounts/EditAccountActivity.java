package com.robustastudio.robustivityapp.EditAccounts;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.HomeActivity;
import com.robustastudio.robustivityapp.Models.Accounts;
import com.robustastudio.robustivityapp.R;

import java.util.List;

public class EditAccountActivity extends AppCompatActivity {
EditText account_name ;
EditText account_email;
EditText account_address ;
EditText account_phone ;
Spinner sec_spinner ;
Button save ;
String name , email, address,phone;
int id;
    public AppDatabase db = null;
    List<String> sectors;
    public DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_account);
        account_name = findViewById(R.id.account_name);
        account_email= findViewById(R.id.account_email);
        account_address = findViewById(R.id.account_address);
        account_phone = findViewById(R.id.account_phone);
        sec_spinner = findViewById(R.id.sec_spinner);
        save = findViewById(R.id.account_save);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity").allowMainThreadQueries().build();

        sectors  = db.userDao().getAllSectors();

        if(getIntent().hasExtra("name")&&getIntent().hasExtra("email")&&getIntent().hasExtra("address")&&getIntent().hasExtra("phone")) {
            name = getIntent().getStringExtra("name");
            email = getIntent().getStringExtra("email");
            address = getIntent().getStringExtra("address");
            phone = getIntent().getStringExtra("phone");
            id = (getIntent().getIntExtra("id",0));
        }
        setEditTexts();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Accounts account = new Accounts(account_name.getText().toString(),account_phone.getText().toString(),account_address.getText().toString(),account_email.getText().toString(),sec_spinner.getSelectedItem().toString(),id);
//
//
//

                mDatabase.child("Accounts").child(String.valueOf(id)).child("name").setValue(account_name.getText().toString());
                mDatabase.child("Accounts").child(String.valueOf(id)).child("address").setValue(account_address.getText().toString());
                mDatabase.child("Accounts").child(String.valueOf(id)).child("phonenumber").setValue(account_phone.getText().toString());
                mDatabase.child("Accounts").child(String.valueOf(id)).child("sector").setValue(sec_spinner.getSelectedItem().toString());
                mDatabase.child("Accounts").child(String.valueOf(id)).child("email").setValue(account_email.getText().toString());
                Intent myIntent = new Intent(EditAccountActivity.this, HomeActivity.class);
                EditAccountActivity.this.startActivity(myIntent);
            }
        });



    }
    public void setEditTexts(){

    account_name.setText(name);
    account_address.setText(address);
    account_email.setText(email);
    account_phone.setText(phone);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sectors);
        sec_spinner.setAdapter(adapter);
    }
}
