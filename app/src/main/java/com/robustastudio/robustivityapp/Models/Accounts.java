package com.robustastudio.robustivityapp.Models;

import android.arch.persistence.room.PrimaryKey;

/**
 * Created by hp on 28/03/2018.
 */

public class Accounts {
    public String getName() {
        return name;
    }

    public Number getPhoneNumber() {

        return PhoneNumber;
    }

    public void setPhoneNumber(Number phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String[] getProjects() {
        return Projects;
    }

    public void setProjects(String[] projects) {
        Projects = projects;
    }

    public Accounts(String name, Number phoneNumber, String address, String email, String[] projects) {

        this.name = name;
        PhoneNumber = phoneNumber;
        Address = address;
        Email = email;
        Projects = projects;
    }

    public String name;
    public Number PhoneNumber;
    public String Address ;
    public String Email ;
    public String [] Projects;


}
