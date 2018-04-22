package com.robustastudio.robustivityapp.CreateAccounts;

import android.accounts.Account;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.robustastudio.robustivityapp.Accounts.AccountActivity;
import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.HomeActivity;
import com.robustastudio.robustivityapp.Models.Accounts;
import com.robustastudio.robustivityapp.Models.UserProfile;
import com.robustastudio.robustivityapp.R;

import java.util.List;

public class CreateActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;

EditText account_name;
EditText account_email;
EditText account_phone ;
EditText account_address ;
Button save_account;
Spinner dropdown;
    List<String> sectors;
    List<Accounts> accounts;
    public AppDatabase db = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
         account_name = findViewById(R.id.Accountname);
         account_email = findViewById(R.id.AccountEmail);
         account_phone = findViewById(R.id.AccountPhone);
         account_address = findViewById(R.id.AccountAddress);

         save_account = findViewById(R.id.SaveAccount);

         mDatabase = FirebaseDatabase.getInstance().getReference();
        dropdown = findViewById(R.id.spinner1);
        db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity").allowMainThreadQueries().build();
        sectors  = db.userDao().getAllSectors();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sectors);
        dropdown.setAdapter(adapter);
save_account.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        Accounts account = new Accounts(account_name.getText().toString(),account_phone.getText().toString(),account_address.getText().toString(),account_email.getText().toString(),dropdown.getSelectedItem().toString());

       mDatabase.child("Accounts").child(account_name.getText().toString()).setValue(account);

        Intent myIntent = new Intent(CreateActivity.this,HomeActivity.class);
        CreateActivity.this.startActivity(myIntent);
    }
});

    }
}
