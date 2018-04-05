package com.robustastudio.robustivityapp;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.robustastudio.robustivityapp.Models.UserProfile;

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
<<<<<<< Updated upstream
        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity")
                .allowMainThreadQueries().build();
=======
        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity").allowMainThreadQueries().build();
>>>>>>> Stashed changes

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 26/03/2018 Save to database
                UserProfile userprofile = new UserProfile(name.getText().toString(),Phone.getText().toString(),Email.getText().toString());
                db.userDao().insertAll(userprofile);
                Intent myIntent = new Intent(createuserprof.this, viewprofile.class);
                createuserprof.this.startActivity(myIntent);

            }
        });

    }
}
