package com.robustastudio.robustivityapp.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.TypeConverters;
import android.text.format.DateFormat;

import java.util.Date;
import java.util.List;

/**
 * Created by hp on 28/03/2018.
 */

public class Tasks {

    @ColumnInfo(name = "task_name")
    public String name ;

    @ColumnInfo(name = "task_title")
    public String title ;

    @ColumnInfo(name = "task_description")
    public String description;

    @ColumnInfo(name = "task_assignee")
    public String assigne ;

    @ColumnInfo(name = "task_members")
    public List<String> members;

    @ColumnInfo(name = "task_estimated_hours")
    public String estimated_hours;

    @ColumnInfo(name = "task_due_date")
    @TypeConverters({DateConverter.class})
    public Date due_date;

    @ColumnInfo(name = "task_finished_hours")
    public String finished_hours;

    @ColumnInfo(name = "project_endDate")
    @TypeConverters({DateConverter.class})
    public Date StartDate ;

    @ColumnInfo(name ="project_name")
    public String project ;



    public Tasks(String name, String title, String project, String description, String assigne, List<String> members, String estimated_hours, Date due_date, String finished_hours, Date startDate) {
        this.name = name;
        this.title = title;
        this.description = description;
        this.assigne = assigne;
        this.members = members;
        this.estimated_hours = estimated_hours;
        this.due_date = due_date;
        this.finished_hours = finished_hours;
        StartDate = startDate;
        this.project=project;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getEstimated_hours() {
        return estimated_hours;
    }

    public void setEstimated_hours(String estimated_hours) {
        this.estimated_hours = estimated_hours;
    }

    public Date getDue_date() {
        return due_date;
    }

    public void setDue_date(Date due_date) {
        this.due_date = due_date;
    }

    public String getFinished_hours() {
        return finished_hours;
    }

    public void setFinished_hours(String finished_hours) {
        this.finished_hours = finished_hours;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public void setStartDate(Date startDate) {
        StartDate = startDate;
    }
}
