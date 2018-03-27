package com.robustastudio.robustivityapp;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class createuserprof extends AppCompatActivity {
    EditText name;
    EditText Email;
    EditText Phone;
    Button button;
    private static final String TAG = "createuserprof";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createuserprof);
        name=findViewById(R.id.name);
        Email=findViewById(R.id.Email);
        Phone=findViewById(R.id.Phone);
        button=findViewById(R.id.button);
        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity")
                .allowMainThreadQueries().build();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 26/03/2018 Save to database
                UserProfile userprofile = new UserProfile("alaa","010033","kurdian@live.com");
                db.userDao().insertAll(userprofile);
            }
        });

    }
}
