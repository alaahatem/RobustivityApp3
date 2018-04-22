package com.robustastudio.robustivityapp.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;


import java.util.ArrayList;

import java.util.List;

/**
 * Created by hp on 28/03/2018.
 */
@Entity
public class Sectors {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String name ;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public Sectors(String name) {

        this.name = name;

    }
}
