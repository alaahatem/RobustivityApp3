package com.robustastudio.robustivityapp;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.robustastudio.robustivityapp.Models.UserProfile;

import java.util.List;

/**
 * Created by hp on 26/03/2018.
 */
@Dao
public interface UserDao {
    @Query("SELECT *FROM userprofile")
    List<UserProfile> getAllprofiles();
    @Insert
    void insertAll(UserProfile...userProfiles);
    
}
