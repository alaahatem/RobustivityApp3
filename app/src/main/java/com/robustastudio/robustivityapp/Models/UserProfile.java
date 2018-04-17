package com.robustastudio.robustivityapp.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;


import java.net.URI;
import java.util.List;

/**
 * Created by hp on 26/03/2018.
 */
@Entity
public class UserProfile {



    public UserProfile() {
    }
    @ColumnInfo(name = "image_uri")
    private String Image;
    @ColumnInfo(name = "user_name")
    private String name;
    @ColumnInfo(name = "user_phone")
    private String phone;
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "user_email")
    private String email;
//    @TypeConverters({Converter.class})
//    public List<String> projects;
    @ColumnInfo(name = "user_status")
    private String status ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public UserProfile(String image, String name, String phone, @NonNull String email, String status) {

        Image = image;
        this.name = name;
        this.phone = phone;
        this.email = email;

        this.status = status;
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



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}