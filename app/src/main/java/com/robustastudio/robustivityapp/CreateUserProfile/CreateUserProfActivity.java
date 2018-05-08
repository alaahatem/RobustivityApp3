package com.robustastudio.robustivityapp.CreateUserProfile;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.FirebaseApp;
import com.robustastudio.robustivityapp.HomeActivity;
import com.robustastudio.robustivityapp.Models.UserProfile;
import com.robustastudio.robustivityapp.R;

import java.util.ArrayList;
import java.util.List;

public class CreateUserProfActivity extends AppCompatActivity implements CreateUserProf {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    List<UserProfile> userProfiles;
    public  AppDatabase db = null;
    public List<String> temp = new ArrayList<String>();
    EditText name;
    EditText Email;
    EditText Phone;
    Button button;
    TextInputLayout phone_layout ;
    TextInputLayout email_layout;

    CreateUserProfPresenter mCreateUserProfPresenter;
    private static final String TAG = "CreateUserProfActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mCreateUserProfPresenter = new CreateUserProfPresenterImpl(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createuserprof);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final FirebaseUser user = mAuth.getCurrentUser();

        name = findViewById(R.id.name);
        Email = findViewById(R.id.Email);
        Phone = findViewById(R.id.Phone);
        button = findViewById(R.id.button);
        phone_layout = findViewById(R.id.phone_layout);
        email_layout= findViewById(R.id.email_layout);
        if (getIntent().hasExtra("name") && getIntent().hasExtra("email")&&getIntent().hasExtra("phone")) {
            name.setText(getIntent().getStringExtra("name"));
            Email.setText(getIntent().getStringExtra("email"));
            Phone.setText(getIntent().getStringExtra("phone"));

        }

            db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "robustivity").allowMainThreadQueries().build();
            userProfiles = db.userDao().getAllprofiles();
            //your code

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCreateUserProfPresenter.InsertUser(Phone,Email, name, userProfiles);


                }
            });

        }
        public void seterror(){
        phone_layout.setError("Please enter a valid phone number");
        }

    public void InsertUserSuccess() {
            if(mAuth.getCurrentUser()!=null)
        mDatabase.child("user_profile").child(FirebaseApp.EncodeString(mAuth.getCurrentUser().getEmail())).child("name").setValue(name.getText().toString());
        mDatabase.child("user_profile").child(FirebaseApp.EncodeString(mAuth.getCurrentUser().getEmail())).child("phone").setValue(Phone.getText().toString());

        db.userDao().updateProfileInfo(name.getText().toString(),Email.getText().toString(),Phone.getText().toString(),getIntent().getStringExtra("status"));


        Intent myIntent = new Intent(CreateUserProfActivity.this, HomeActivity.class);
        CreateUserProfActivity.this.startActivity(myIntent);
    }
public  void InsertUserFailure(){
    email_layout.setError("Please enter a valid Email");
        }


public void DuplicateEmail(){


}



}
