package com.robustastudio.robustivityapp.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.robustastudio.robustivityapp.Models.Projects;

import java.util.List;

/**
 * Created by sa2r_ on 4/1/2018.
 */
@Dao
public interface ProjectDao {
    @Insert
    void addProject(Projects...project);
    @Query("SELECT account_name FROM Accounts")
    List<String> get_all_accounts();
}
