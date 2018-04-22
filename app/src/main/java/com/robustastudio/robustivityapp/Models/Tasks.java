package com.robustastudio.robustivityapp.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.text.format.DateFormat;

import java.util.Date;
import java.util.List;

/**
 * Created by hp on 28/03/2018.
 */
@Entity
public class Tasks {

    @PrimaryKey(autoGenerate = true)
    public int id ;

    @ColumnInfo(name="task_name")
    public String name ;

    @ColumnInfo(name ="task_description")
    public String description;

    @ColumnInfo(name ="task_assigne")
    public String assigne ;

    @ColumnInfo(name="task_members_list")
    public List<String> members;

    @ColumnInfo(name ="task_estimated_hours")
    public Float estimated_hours;

    @ColumnInfo(name ="task_due_date")
    public Date due_date;

    @ColumnInfo(name ="task_project_name")
    public String project_name;

    @ColumnInfo(name ="task_finished_hours")
    public Float finished_hours;

    @ColumnInfo(name ="task_start_date")
    public Date StartDate ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ColumnInfo(name ="task_toggled_days")
    public int days ;


    public Tasks(String name, String description, String assigne, List<String> members, Float estimated_hours, Date due_date, String project_name, Float finished_hours, Date StartDate, int days) {
        this.name = name;
        this.description = description;
        this.assigne = assigne;
        this.members = members;
        this.estimated_hours = estimated_hours;
        this.due_date = due_date;
        this.project_name = project_name;
        this.finished_hours = finished_hours;
        this.StartDate = StartDate;
        this.days=days;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAssigne() {
        return assigne;
    }

    public void setAssigne(String assigne) {
        this.assigne = assigne;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public Float getEstimated_hours() {
        return estimated_hours;
    }

    public void setEstimated_hours(Float estimated_hours) {
        this.estimated_hours = estimated_hours;
    }

    public Date getDue_date() {
        return due_date;
    }

    public void setDue_date(Date due_date) {
        this.due_date = due_date;
    }

    public Float getFinished_hours() {
        return finished_hours;
    }

    public void setFinished_hours(Float finished_hours) {
        this.finished_hours = finished_hours;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public void setStartDate(Date startDate) {
        StartDate = startDate;
    }



}
