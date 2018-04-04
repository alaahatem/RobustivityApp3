package com.robustastudio.robustivityapp;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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
    @Query("UPDATE userprofile SET user_status=:status WHERE user_email = :email")
    void updateUsers(String status , String email);
    
}
