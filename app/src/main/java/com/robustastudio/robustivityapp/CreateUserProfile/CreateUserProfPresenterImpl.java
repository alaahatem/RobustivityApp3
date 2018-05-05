package com.robustastudio.robustivityapp.CreateUserProfile;

import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

import com.robustastudio.robustivityapp.Models.UserProfile;

import java.util.List;

/**
 * Created by hp on 13/04/2018.
 */

public class CreateUserProfPresenterImpl implements CreateUserProfPresenter {
    List<UserProfile> userProfiles;

    CreateUserProf mCreateUserProfile;

    public CreateUserProfPresenterImpl(CreateUserProf mCreateUserProfile) {
        this.mCreateUserProfile = mCreateUserProfile;
    }

    public final static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public void InsertUser(EditText phone,EditText Email, EditText name, List<UserProfile> userProfiles) {

            if (isValidEmail(Email.getText().toString())) {
                if(Patterns.PHONE.matcher(phone.getText().toString()).matches()){
                    mCreateUserProfile.InsertUserSuccess();

                }
                else{
                    mCreateUserProfile.seterror();
                }

            } else {
                mCreateUserProfile.InsertUserFailure();
            }

    }

}
