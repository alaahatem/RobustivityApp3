package com.robustastudio.robustivityapp.Models;

import android.arch.persistence.room.TypeConverter;

//import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hp on 28/03/2018.
 */

public class Converter {


    @TypeConverter
    public String  gettingListFromString(List<String> name) {

        if (name!=null&&name.size()> 0) {
            StringBuilder nameBuilder = new StringBuilder();

            for (String n : name) {
                nameBuilder.append("'").append(n.replace("'", "")).append("',");
                // can also do the following

            }

            nameBuilder.deleteCharAt(nameBuilder.length() - 1);

            return nameBuilder.toString();
        } else {
            return null;
        }
    }

    @TypeConverter
    public List<String> writingStringFromList(String name) {
        if(name==null)
            return new ArrayList<>();
        List<String> arr = Arrays.asList(name.split("\\s*,\\s*"));
        return arr;
    }
}




