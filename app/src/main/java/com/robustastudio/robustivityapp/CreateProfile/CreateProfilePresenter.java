package com.robustastudio.robustivityapp.CreateProfile;

import com.robustastudio.robustivityapp.Models.UserProfile;

import java.util.List;

/**
 * Created by hp on 12/04/2018.
 */

public interface CreateProfilePresenter {
    List<UserProfile> filter(String s, List<UserProfile>user);
}
