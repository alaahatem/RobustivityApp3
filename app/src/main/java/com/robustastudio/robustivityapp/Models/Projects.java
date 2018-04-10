package com.robustastudio.robustivityapp.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import java.util.jar.Attributes;


/**
 * Created by hp on 28/03/2018.
 */
@Entity

public class Projects {


    public Projects(@NonNull String name) {
        this.name = name;
    }
    @PrimaryKey(autoGenerate = true)
    public int projectid;

    public Projects( @NonNull String name, String type,List<String> engagement, Date startDate, Date endDate, String tagline, String accountName, double project_cost) {

        this.name = name;
        this.type = type;
        this.engagement=engagement;
        StartDate = startDate;
        this.endDate = endDate;
        Tagline = tagline;
        this.accountName = accountName;
        this.project_cost = project_cost;
    }

    @ColumnInfo(name = "project_name")
    @NonNull
    public String name ;

    @ColumnInfo(name = "project_type")
    public String type;

    @TypeConverters({Converter.class})
    public List<String> engagement;

    @ColumnInfo(name = "project_startDate")
    @TypeConverters({DateConverter.class})
    public Date StartDate;

    @ColumnInfo(name = "project_endDate")
    @TypeConverters({DateConverter.class})
    public Date endDate;

    @ColumnInfo(name = "project_tagline")
    public String Tagline;



    @ColumnInfo(name = "project_accountName")
    public String accountName ;

    @ColumnInfo(name = "project_Cost")
    public double project_cost;


    @NonNull
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public List<String> getEngagement() {
        return engagement;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getTagline() {
        return Tagline;
    }


    public String getAccountName() {
        return accountName;
    }

    public double getProject_cost() {
        return project_cost;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setEngagement(List<String> engagement) {
        this.engagement = engagement;
    }

    public void setStartDate(Date startDate) {
        StartDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setTagline(String tagline) {
        Tagline = tagline;
    }


    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setProject_cost(double project_cost) {
        this.project_cost = project_cost;
    }




}

