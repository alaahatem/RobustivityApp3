package com.robustastudio.robustivityapp.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import com.robustastudio.robustivityapp.Models.Projects;

/**
 * Created by sa2r_ on 4/1/2018.
 */
@Dao
public interface ProjectDao {
    @Insert
    void addProject(Projects...project);
}