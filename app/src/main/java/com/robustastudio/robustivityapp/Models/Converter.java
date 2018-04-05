package com.robustastudio.robustivityapp.Models;

import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

//import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hp on 28/03/2018.
 */

public class Converter {


    @TypeConverter
    public String  gettingListFromString(List<String> name) {

        if (name.size()> 0) {
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
    public List<String> writingStringFromList(String name) {
        List<String> arr = Arrays.asList(name.split("\\s*,\\s*"));
        return arr;
    }

    @TypeConverter
    public String  gettingListFromStr(List<String> name) {
        String temp= StringUtils.join(name, ',');
//        if (name.size()> 0 && name!=null) {
//            StringBuilder nameBuilder = new StringBuilder();
//
//            for (String n : name) {
//                nameBuilder.append("'").append(n.replace("'", "\\'")).append("',");
//                // can also do the following
//                // nameBuilder.append("'").append(n.replace("'", "''")).append("',");
//            }
//
//            nameBuilder.deleteCharAt(nameBuilder.length() - 1);
//
//            return nameBuilder.toString();
//        } else {
//            return "";
//        }
        return temp;
    }

   

    @TypeConverter
    public List<String> writingStringFromLi(String name) {
        List<String> arr = Arrays.asList(name.split("\\s*,\\s*"));
        return arr;
    }


}




