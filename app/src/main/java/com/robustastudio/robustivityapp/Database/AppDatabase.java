package com.robustastudio.robustivityapp.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.robustastudio.robustivityapp.Models.Accounts;
import com.robustastudio.robustivityapp.Models.Converter;
import com.robustastudio.robustivityapp.Models.DateConverter;
import com.robustastudio.robustivityapp.Models.Projects;
import com.robustastudio.robustivityapp.Models.Sectors;
import com.robustastudio.robustivityapp.Models.Tasks;
import com.robustastudio.robustivityapp.Models.UserProfile;

/**
 * Created by hp on 26/03/2018.
 */

@Database(entities = {UserProfile.class,Projects.class, Sectors.class,Tasks.class, Accounts.class},version = 13)
@TypeConverters({DateConverter.class, Converter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();

}
