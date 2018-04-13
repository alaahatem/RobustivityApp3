package com.robustastudio.robustivityapp.CreateUserProfile;

import android.widget.EditText;

import com.robustastudio.robustivityapp.Models.UserProfile;

import java.util.List;

/**
 * Created by hp on 13/04/2018.
 */

public interface CreateUserProfPresenter {
    void InsertUser(EditText Email, EditText name, List<UserProfile> userProfiles);
}
