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
public class Projects{
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "project_name")
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

    @ColumnInfo(name = "project_sectorName")
    public String SectorName;

    @ColumnInfo(name = "project_accountName")
    public String accountName ;

    @ColumnInfo(name = "project_Cost")
    public double project_cost;

}

