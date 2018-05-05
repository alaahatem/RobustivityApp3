package com.robustastudio.robustivityapp.CreateUserProfile;

import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import com.robustastudio.robustivityapp.EditAccounts.EditAccountActivity;
import com.robustastudio.robustivityapp.Models.UserProfile;

import java.util.List;

/**
 * Created by hp on 13/04/2018.
 */

public interface CreateUserProfPresenter {
    void InsertUser(TextInputLayout phonelayout,EditText phone, EditText Email, EditText name, List<UserProfile> userProfiles);
}
