package com.robustastudio.robustivityapp;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.robustastudio.robustivityapp.Models.UserProfile;

/**
 * Created by hp on 26/03/2018.
 */

@Database(entities = {UserProfile.class},version = 3)

public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();

}
