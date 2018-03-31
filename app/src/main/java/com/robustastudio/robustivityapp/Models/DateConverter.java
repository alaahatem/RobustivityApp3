package com.robustastudio.robustivityapp.Models;

/**
 * Created by MALAK SHAKER on 3/31/2018.
 */


import java.util.Date;



import android.arch.persistence.room.TypeConverter;

import java.util.Date;

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


