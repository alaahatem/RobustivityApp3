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
    @PrimaryKey
    @NonNull
    public String name ;
    @TypeConverters({Converter.class})
    public List<String> accounts ;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<String> accounts) {
        this.accounts = accounts;
    }

    public Sectors(String name, List<String> accounts) {

        this.name = name;
        this.accounts = accounts;
    }
}
