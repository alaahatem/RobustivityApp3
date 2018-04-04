package com.robustastudio.robustivityapp;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.robustastudio.robustivityapp.Models.UserProfile;

import java.util.ArrayList;
import java.util.List;

public class createuserprof extends AppCompatActivity {
    public List<String> temp = new ArrayList<String>();
    EditText name;
    EditText Email;
    EditText Phone;
    Button button;
    String regx ="^[a-zA-Z0-9._-]{3,}$";
    private static final String TAG = "createuserprof";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createuserprof);
        name=findViewById(R.id.name);
        Email=findViewById(R.id.Email);
        Phone=findViewById(R.id.Phone);
        button=findViewById(R.id.button);
        new Thread(new Runnable() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //your code
                        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity").allowMainThreadQueries().build();
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // TODO: 26/03/2018 Save to database
                                if(isValidEmail(Email.getText())&&name.getText().toString().matches(regx)) {
                                    UserProfile userprofile = new UserProfile(name.getText().toString(), Phone.getText().toString(), Email.getText().toString(), temp, "Online");
                                    db.userDao().insertAll(userprofile);
                                    Intent myIntent = new Intent(createuserprof.this, viewprofile.class);
                                    createuserprof.this.startActivity(myIntent);
                                }
                                else{
                                    Toast.makeText(view.getContext(),"Please enter a valid credentials",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });
            }
        }).start();





    }
    public final static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
