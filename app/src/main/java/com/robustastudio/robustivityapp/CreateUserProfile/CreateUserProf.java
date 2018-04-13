package com.robustastudio.robustivityapp.CreateUserProfile;

import com.robustastudio.robustivityapp.Database.AppDatabase;

/**
 * Created by hp on 13/04/2018.
 */

public interface CreateUserProf {
    void InsertUserSuccess();
    void InsertUserFailure();
    void DuplicateEmail();
}
