package com.robustastudio.robustivityapp;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.robustastudio.robustivityapp.Models.Converter;
import com.robustastudio.robustivityapp.Models.DateConverter;
import com.robustastudio.robustivityapp.Models.Projects;
import com.robustastudio.robustivityapp.Models.Tasks;
import com.robustastudio.robustivityapp.Models.Todo;
import com.robustastudio.robustivityapp.Models.UserProfile;

/**
 * Created by hp on 26/03/2018.
 */

@Database(entities = {UserProfile.class, Projects.class,Tasks.class,Todo.class},version = 5)
@TypeConverters({DateConverter.class, Converter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract ProjectDao projectDao();
    public abstract TaskDao taskDao();
    public abstract TodoDao todoDao();
}
