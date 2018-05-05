package com.robustastudio.robustivityapp.ViewProfile;

import com.google.firebase.auth.FirebaseAuth;
import com.robustastudio.robustivityapp.MainActivity;
import com.robustastudio.robustivityapp.Models.UserProfile;

import java.util.List;

/**
 * Created by hp on 12/04/2018.
 */

public class ViewProfilePresenterImpl implements ViewProfilePresenter {
    List<UserProfile> userprofiles;
    ViewProfile mViewProfile;
    FirebaseAuth mAuth;


    public ViewProfilePresenterImpl(ViewProfile mViewProfile) {
        this.mViewProfile = mViewProfile;
    }

    public void ShowProfile(List<UserProfile> userProf){
    mAuth = FirebaseAuth.getInstance();
        for (int i = 0; i <userProf.size() ; i++) {
            if(userProf.get(i).getEmail().equals(mAuth.getCurrentUser().getEmail())){
            String name = userProf.get(i).getName();
            String email = userProf.get(i).getEmail();
            String phone = userProf.get(i).getPhone();
            String status = userProf.get(i).getStatus();
            mViewProfile.SetTextViews(name,email,phone,status);


            }
        }




    }
}
