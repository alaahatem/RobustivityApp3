package com.robustastudio.robustivityapp.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by hp on 28/03/2018.
 */
@Entity
public class Accounts {


    public Accounts(@NonNull String name, String phonenumber, String address, String email, String sector) {
        this.name = name;
        this.phonenumber = phonenumber;
        this.address = address;
        this.email = email;
        this.sector = sector;
    }


    @PrimaryKey(autoGenerate = true)

    public int id;

    @ColumnInfo(name = "account_name")
    public String name;
    @ColumnInfo(name = "account_phone")
    public String phonenumber;
    @ColumnInfo(name = "account_address")
    public String address;
    @ColumnInfo(name = "account_email")
    public String email;
    @ColumnInfo(name = "account_sector")
    public String sector;

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }
}