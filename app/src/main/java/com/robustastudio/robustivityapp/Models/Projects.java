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

/**
 * Created by hp on 28/03/2018.
 */
@Entity (tableName = "Projects")
public class Projects {
    @PrimaryKey
    @ColumnInfo(name="project_id")
    public int projectid;
    @ColumnInfo(name="project_name")
    public String name ;
    @ColumnInfo(name="project_type")
    public String type;
    @ColumnInfo(name="project_engagement")
    @TypeConverters({Converter.class} )
    public List<String> engagement;
    @ColumnInfo(name="project_startDate")
    @TypeConverters({DateConverter.class})
    public Date startDate;
    @ColumnInfo(name="project_endDate")
    @TypeConverters({DateConverter.class})
    public  Date endDate;
    @ColumnInfo(name="project_tagLine")
    public String tagline;
    @ColumnInfo(name="project_accountName")
    public String accountName ;
    @ColumnInfo(name="project_cost")
    public float project_cost;

    public Projects(int id,@NonNull String name, String type, List<String> engagement, Date startDate, Date endDate, String tagline, String accountName, float project_cost) {
        this.projectid=id;
        this.name = name;
        this.type = type;
        this.engagement = engagement;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tagline = tagline;
        this.accountName = accountName;
        this.project_cost = project_cost;
    }
    public Projects(){}

    public int getProjectid() {
        return projectid;
    }

    public void setProjectid(int projectid) {
        this.projectid = projectid;
    }

    @NonNull

    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getEngagement() {
        return engagement;
    }

    public void setEngagement(List<String> engagement) {
        this.engagement = engagement;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public float getProject_cost() {
        return project_cost;
    }

    public void setProject_cost(float project_cost) {
        this.project_cost = project_cost;
    }
}
