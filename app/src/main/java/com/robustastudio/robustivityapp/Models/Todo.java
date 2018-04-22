package com.robustastudio.robustivityapp.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.Date;
import java.util.List;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;

/**
 * Created by hp on 28/03/2018.
 */
@Entity(tableName = "Todos")
public class Todo {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo(name="todo_members")
    @TypeConverters({Converter.class})
    public List<String> members;
    @ColumnInfo(name = "start_time")
    public String  starttime;
    @ColumnInfo(name = "todo_date")
    @TypeConverters({DateConverter.class})
    public Date date;
    @ColumnInfo(name = "todo_duration")
    public double duration;

    public Todo(List<String> members, String starttime, Date date, double duration) {
        this.members = members;
        this.starttime = starttime;
        this.date = date;
        this.duration = duration;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }
}
