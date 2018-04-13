package com.robustastudio.robustivityapp.CreateUserProfile;

import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import com.robustastudio.robustivityapp.Models.UserProfile;

import java.util.List;

/**
 * Created by hp on 13/04/2018.
 */

public class CreateUserProfPresenterImpl implements CreateUserProfPresenter {
    List<UserProfile> userProfiles;
    String regx = "^[a-zA-Z0-9._-]{3,}$";
    String pregx = "^\\+[0-9]{10,13}$";
    CreateUserProf mCreateUserProfile;

    public CreateUserProfPresenterImpl(CreateUserProf mCreateUserProfile) {
        this.mCreateUserProfile = mCreateUserProfile;
    }

    public final static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public void InsertUser(EditText Email, EditText name, List<UserProfile> userProfiles) {
         boolean Dup=false;
        for (int i = 0; i < userProfiles.size(); i++) {
            if (userProfiles.get(i).getEmail().equals(Email.getText().toString())) {
                mCreateUserProfile.DuplicateEmail();
                Dup = true;

            }
        }
        if (Dup == false) {
            if (isValidEmail(Email.getText().toString()) && name.getText().toString().matches(regx)) {
                mCreateUserProfile.InsertUserSuccess();
            } else {
                mCreateUserProfile.InsertUserFailure();
            }
        }
    }

}
