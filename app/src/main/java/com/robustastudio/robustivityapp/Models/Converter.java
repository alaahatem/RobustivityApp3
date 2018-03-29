package com.robustastudio.robustivityapp.Models;

import android.arch.persistence.room.TypeConverter;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hp on 28/03/2018.
 */

public class Converter {


    @TypeConverter
    public String  gettingListFromString(String [] name) {

        if (name.length > 0) {
            StringBuilder nameBuilder = new StringBuilder();

            for (String n : name) {
                nameBuilder.append("'").append(n.replace("'", "\\'")).append("',");
                // can also do the following
                // nameBuilder.append("'").append(n.replace("'", "''")).append("',");
            }

            nameBuilder.deleteCharAt(nameBuilder.length() - 1);

            return nameBuilder.toString();
        } else {
            return "";
        }
    }

    @TypeConverter
    public  String [] writingStringFromList(String name) {
        String[] arr = name.split(",");
        return arr;
    }
}




