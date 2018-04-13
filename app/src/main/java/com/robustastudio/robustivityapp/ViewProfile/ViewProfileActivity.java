package com.robustastudio.robustivityapp.ViewProfile;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.Constants.Constants;
import com.robustastudio.robustivityapp.Models.UserProfile;
import com.robustastudio.robustivityapp.R;

import java.util.List;

public class ViewProfileActivity extends AppCompatActivity implements  ViewProfile {

    private ViewProfilePresenter mViewProfilePresenter;
    TextView emailtv ;
    TextView nametv;
    List<UserProfile> userprofiles;
    String UserEmail;
    TextView userphone;
    TextView userstatus;
    String DbName = Constants.AppdatabaseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    mViewProfilePresenter = new ViewProfilePresenterImpl(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewprofile);
        emailtv = findViewById(R.id.viewEmail);
        nametv = findViewById(R.id.viewName);
        userphone=findViewById(R.id.viewPhone);
        userstatus=findViewById(R.id.viewStatus);
        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,DbName).allowMainThreadQueries().build();
        userprofiles  = db.userDao().getAllprofiles();

    mViewProfilePresenter.ShowProfile(userprofiles);

    }
    public void SetTextViews(String name,String Email,String phone,String status) {


        emailtv.setText(Email);
        nametv.setText(name);
        userphone.setText(phone);
        userstatus.setText(status);

    }





    }
