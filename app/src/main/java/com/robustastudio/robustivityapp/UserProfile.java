package com.robustastudio.robustivityapp;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by hp on 26/03/2018.
 */
@Entity
public class UserProfile {


    public UserProfile(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @PrimaryKey(autoGenerate = true)
    private int id ;
    @ColumnInfo(name = "user_name")
    private String name;
    @ColumnInfo(name = "user_phone")
    private String phone;
    @ColumnInfo(name ="user_email")
    private String email;


}
