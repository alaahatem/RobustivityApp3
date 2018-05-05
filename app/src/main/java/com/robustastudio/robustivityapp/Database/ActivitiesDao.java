package com.robustastudio.robustivityapp.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.robustastudio.robustivityapp.Models.Activities;

import java.util.List;

/**
 * Created by hp on 02/05/2018.
 */
@Dao
public interface ActivitiesDao {
    @Query("SELECT *FROM Activities")
    List<Activities> getAllActivities();
    @Insert
    void insertActivities(Activities... activities);
    @Query("UPDATE Activities SET image_uri =:image   WHERE activity_cont = :cont ")

    void updateActivityImage(String image, String cont);
}
