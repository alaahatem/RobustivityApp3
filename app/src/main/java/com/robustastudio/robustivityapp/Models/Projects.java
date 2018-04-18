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

    public Projects( @NonNull String name, String type,List<String> engagement, Date startDate, Date endDate, String tagline, String accountName, float project_cost, float contracted_cost, float planned_cost ) {

        this.name = name;
        this.type = type;
        this.engagement=engagement;
        StartDate = startDate;
        this.endDate = endDate;
        Tagline = tagline;
        this.accountName = accountName;
        this.project_cost = project_cost;
        this.contracted_cost=contracted_cost;
        this.planned_cost=planned_cost;
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

    public float getContracted_cost() {
        return contracted_cost;
    }

    public void setContracted_cost(float contracted_cost) {
        this.contracted_cost = contracted_cost;
    }

    public float getPlanned_cost() {
        return planned_cost;
    }

    public void setPlanned_cost(float planned_cost) {
        this.planned_cost = planned_cost;
    }

    @ColumnInfo(name = "project_tagline")
    public String Tagline;



    @ColumnInfo(name = "project_accountName")
    public String accountName ;

    @ColumnInfo(name = "project_Cost")
    public float project_cost;

    @ColumnInfo(name = "project_contracted_cost")
    public float contracted_cost;

    @ColumnInfo(name = "project_planned_cost")
    public float planned_cost;


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

    public float getProject_cost() {
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

    public void setProject_cost(float project_cost) {
        this.project_cost = project_cost;
    }




}

