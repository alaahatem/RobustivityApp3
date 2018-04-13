package com.robustastudio.robustivityapp.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;


import java.util.List;

/**
 * Created by hp on 26/03/2018.
 */
@Entity
public class UserProfile {

    public UserProfile(String name, String phone, String email, List<String> projects, String status) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.projects = projects;
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {

        return id;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "user_name")
    private String name;
    @ColumnInfo(name = "user_phone")
    private String phone;
    @ColumnInfo(name = "user_email")
    private String email;
    @TypeConverters({Converter.class})
    public List<String> projects;
    @ColumnInfo(name = "user_status")
    private String status ;

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

    public List<String> getProjects() {
        return projects;
    }

    public void setProjects(List<String> projects) {
        this.projects = projects;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}