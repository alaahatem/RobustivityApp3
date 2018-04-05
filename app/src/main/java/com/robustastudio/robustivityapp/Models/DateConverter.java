package com.robustastudio.robustivityapp.Models;

/**
<<<<<<< Updated upstream
 * Created by MALAK SHAKER on 3/31/2018.
 */


import java.util.Date;



=======
 * Created by MALAK SHAKER on 4/4/2018.
 */

>>>>>>> Stashed changes
import android.arch.persistence.room.TypeConverter;

import java.util.Date;

<<<<<<< Updated upstream
    public class DateConverter {
        @TypeConverter
        public static Date toDate(Long timestamp) {
            return timestamp == null ? null : new Date(timestamp);
        }

        @TypeConverter
        public static Long toTimestamp(Date date) {
            return date == null ? null : date.getTime();
        }
    }


=======
public class DateConverter {
    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
>>>>>>> Stashed changes
