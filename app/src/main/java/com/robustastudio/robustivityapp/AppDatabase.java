package com.robustastudio.robustivityapp;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

<<<<<<< Updated upstream
=======
import com.robustastudio.robustivityapp.Models.Converter;
import com.robustastudio.robustivityapp.Models.DateConverter;
>>>>>>> Stashed changes
import com.robustastudio.robustivityapp.Models.Projects;
import com.robustastudio.robustivityapp.Models.Sectors;
import com.robustastudio.robustivityapp.Models.UserProfile;

/**
 * Created by hp on 26/03/2018.
 */

<<<<<<< Updated upstream
@Database(entities = {UserProfile.class, Projects.class, Sectors.class},version = 1)

=======
@Database(entities = {UserProfile.class, Projects.class, Sectors.class},version = 3)
@TypeConverters({DateConverter.class, Converter.class})
>>>>>>> Stashed changes
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();

}
