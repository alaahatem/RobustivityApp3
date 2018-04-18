package com.robustastudio.robustivityapp.UserProfiles;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.robustastudio.robustivityapp.FirebaseApp;
import com.robustastudio.robustivityapp.R;
import com.squareup.picasso.Picasso;

public class UsersProfilesImpl extends AppCompatActivity implements UserProfiles {
    TextView viewname ;
    TextView viewemail;
    TextView viewphone;
    TextView viewstatus;
    String downloadedImage;
    FloatingActionButton ping ;
    ImageView imageView;
    private DatabaseReference mDatabaseRef;
    private UserProfilePresenter mUserProfilePresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mUserProfilePresenter = new UserProfilePresenterImpl(this );
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_profiles);
        imageView = findViewById(R.id.imageview);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        getIncomingIntent();
        ping = findViewById(R.id.ping);
        DatabaseReference ref = mDatabaseRef.child("user_profile").child(FirebaseApp.EncodeString( getIntent().getStringExtra("user_email"))).child("image");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                downloadedImage=dataSnapshot.getValue(String.class);
                if(!downloadedImage.isEmpty())
                Picasso.get().load(downloadedImage).centerCrop().fit().into(imageView);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        ping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUserProfilePresenter.sendNotification(getIntent().getStringExtra("user_email"));
            }
        });

    }

    public void getIncomingIntent() {
        if (getIntent().hasExtra("Username") && getIntent().hasExtra("user_email")&&getIntent().hasExtra("phone")&&getIntent().hasExtra("Status")) {
            String  username = getIntent().getStringExtra("Username");

            String  Email = getIntent().getStringExtra("user_email");
            String  phone = getIntent().getStringExtra("phone");
            String  status = getIntent().getStringExtra("Status");

             setTextViews(username,Email,phone,status);
        }
    }

    public void setTextViews(String username , String email ,String phone , String status){
        Toast.makeText(this,username,Toast.LENGTH_LONG);
        viewname = findViewById(R.id.viewName);
        viewemail = findViewById(R.id.viewEmail);
        viewphone=findViewById(R.id.viewPhone);
        viewstatus=findViewById(R.id.viewStatus);
        viewname.setText(username);
        viewemail.setText(email);
        viewstatus.setText(status);
        viewphone.setText(phone);

    }


}