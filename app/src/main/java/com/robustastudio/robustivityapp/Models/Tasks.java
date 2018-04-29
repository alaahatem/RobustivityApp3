package com.robustastudio.robustivityapp.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;
import android.text.format.DateFormat;

import java.util.Date;
import java.util.List;

/**
 * Created by hp on 28/03/2018.
 */
@Entity(tableName = "Tasks")
public class Tasks {
    @PrimaryKey
    @ColumnInfo(name ="id")
    public int id;
    @ColumnInfo(name="task_name")
    public String name ;
    @ColumnInfo(name="task_description")
    public String description;
    @ColumnInfo(name="task_assignee")
    public String assignee ;
    @ColumnInfo(name="task_members")
    @TypeConverters({Converter.class})
    public List<String> members;
    @ColumnInfo(name="task_estimated_hours")
    public float estimated_hours;

    @ColumnInfo(name="task_due_date")
    @TypeConverters({DateConverter.class})
    public Date due_date;

    @ColumnInfo(name="task_finished_hours")
    public float finished_hours;
    @ColumnInfo(name="task_start_date")
    @TypeConverters({DateConverter.class})
    public Date startDate ;
    @ColumnInfo(name = "project_name")
    public String projectname;

    public Tasks(int id,String name, String description, String assignee, List<String> members, float estimated_hours, Date due_date, float finished_hours, Date startDate, String projectname) {
       this.id=id;
        this.name = name;
        this.description = description;
        this.assignee = assignee;
        this.members = members;
        this.estimated_hours = estimated_hours;
        this.due_date = due_date;
        this.finished_hours = finished_hours;
        this.startDate = startDate;
        this.projectname = projectname;
    }

    public Tasks(){}

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public float getEstimated_hours() {
        return estimated_hours;
    }

    public void setEstimated_hours(float estimated_hours) {
        this.estimated_hours = estimated_hours;
    }

    public Date getDue_date() {
        return due_date;
    }

    public void setDue_date(Date due_date) {
        this.due_date = due_date;
    }

    public float getFinished_hours() {
        return finished_hours;
    }

    public void setFinished_hours(float finished_hours) {
        this.finished_hours = finished_hours;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }
}
