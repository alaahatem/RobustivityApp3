package com.robustastudio.robustivityapp.Models;

import android.arch.persistence.room.TypeConverter;
import java.util.Date;

/**
 * Created by sa2r_ on 4/2/2018.
 */

public class DateConverter {

    @TypeConverter
    public static Date toDate(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long toLong(Date value) {
        return value == null ? null : value.getTime();
    }
}
