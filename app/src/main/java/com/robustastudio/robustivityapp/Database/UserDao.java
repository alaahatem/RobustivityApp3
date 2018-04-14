package com.robustastudio.robustivityapp.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.robustastudio.robustivityapp.Models.Projects;
import com.robustastudio.robustivityapp.Models.Sectors;
import com.robustastudio.robustivityapp.Models.UserProfile;

import java.util.Date;
import java.util.List;

/**
 * Created by hp on 26/03/2018.
 */
@Dao
public interface UserDao {
    @Query("SELECT *FROM userprofile")
    List<UserProfile> getAllprofiles();
    @Insert
    void insertAll(UserProfile...userProfiles);
    @Query("UPDATE userprofile SET user_status=:status WHERE user_email = :email")
    void updateUsers(String status , String email);
    @Query("UPDATE userprofile SET user_name=:name ,user_phone=:phone  WHERE user_email = :email")
    void updateProfile(String name,String email,String phone);
    @Query("SELECT project_name FROM Projects WHERE project_accountName = :Account")
    List<String> getAllProjects(String Account);

    @Insert
    void insertAllProjects(Projects...projects);

    @Query("SELECT project_name FROM Projects")
    List<String> getAllProjects();


    @Insert
    void insertSector(Sectors s);

    @Query("SELECT name FROM Sectors WHERE name = :name")
    List<String> sector_exists(String name);

    //@Query("IF NOT EXISTS (SELECT name FROM Sectors Where name = :sector_name) ")
    //boolean theSectorExists(String sector_name)

    @Query("SELECT * FROM Projects WHERE project_name = :projectName")
    Projects getProjectDetails(String projectName);



    @Query("SELECT project_name FROM Projects WHERE project_name = :projectName")
    String getProjectDetails1(String projectName);

    @Query("SELECT project_type FROM Projects WHERE project_name = :projectName")
    String getProjectDetails2(String projectName);

    @Query("SELECT project_startDate FROM Projects WHERE project_name = :projectName")
    Date getProjectDetails3(String projectName);

    @Query("SELECT project_endDate FROM Projects WHERE project_name = :projectName")
    Date getProjectDetails4(String projectName);

    @Query("SELECT project_tagline FROM Projects WHERE project_name = :projectName")
    String getProjectDetails5(String projectName);


    @Query("SELECT project_accountName FROM Projects WHERE project_name = :projectName")
    String getProjectDetails7(String projectName);

    @Query("SELECT project_Cost FROM Projects WHERE project_name = :projectName")
    Double getProjectDetails8(String projectName);

    @Query("SELECT task_finished_hours FROM Tasks WHERE task_project_name = :project_name")
    List<Float> get_task_finishedHours(String project_name);

    @Query("SELECT task_estimated_hours FROM Tasks WHERE task_project_name = :project_name")
    List<Float> get_task_totalHours(String project_name);

    @Query("SELECT name FROM Sectors")
    List<String> getAllSectors();


}
