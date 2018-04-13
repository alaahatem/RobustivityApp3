package com.robustastudio.robustivityapp.CreateUserProfile;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class CreateUserProfActivity extends AppCompatActivity implements CreateUserProf{
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    List<UserProfile> userProfiles;
    public  AppDatabase db = null;
    public List<String> temp = new ArrayList<String>();
    EditText name;
    EditText Email;
    EditText Phone;
    Button button;

    CreateUserProfPresenter mCreateUserProfPresenter;
    private static final String TAG = "CreateUserProfActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mCreateUserProfPresenter = new CreateUserProfPresenterImpl(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createuserprof);
        mAuth =FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final FirebaseUser user = mAuth.getCurrentUser();

        name=findViewById(R.id.name);
        Email=findViewById(R.id.Email);
        Phone=findViewById(R.id.Phone);
        button=findViewById(R.id.button);

        name.setText(mAuth.getCurrentUser().getDisplayName());
        Email.setText(mAuth.getCurrentUser().getEmail());
        Phone.setText(mAuth.getCurrentUser().getPhoneNumber());
         db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity").allowMainThreadQueries().build();
        userProfiles= db.userDao().getAllprofiles();
                        //your code

                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mCreateUserProfPresenter.InsertUser(Email,name,userProfiles);






                            }
                        });

    }

    public void InsertUserSuccess() {

        UserProfile userprofile = new UserProfile(name.getText().toString(), Phone.getText().toString(), Email.getText().toString(), temp, "Off Premises");

        db.userDao().insertAll(userprofile);

        mDatabase.child("user_profile").child(FirebaseApp.EncodeString(Email.getText().toString())).setValue(userprofile);


        Intent myIntent = new Intent(CreateUserProfActivity.this, HomeActivity.class);
        CreateUserProfActivity.this.startActivity(myIntent);
    }
public  void InsertUserFailure(){
    Toast.makeText(getApplicationContext(),"Please enter a valid credentials",Toast.LENGTH_LONG).show();
}


public void DuplicateEmail(){
    Toast.makeText(getApplicationContext(),"There's already an account with this information ",Toast.LENGTH_LONG).show();
}



}
