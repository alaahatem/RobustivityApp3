package com.robustastudio.robustivityapp;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.robustastudio.robustivityapp.Models.Projects;
import com.robustastudio.robustivityapp.Models.Sectors;
import com.robustastudio.robustivityapp.Models.UserProfile;

/**
 * Created by hp on 26/03/2018.
 */

@Database(entities = {UserProfile.class, Projects.class, Sectors.class},version = 1)

public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();

}
