package com.robustastudio.robustivityapp.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.robustastudio.robustivityapp.Models.Accounts;
import com.robustastudio.robustivityapp.Models.Projects;
import com.robustastudio.robustivityapp.Models.Sectors;
import com.robustastudio.robustivityapp.Models.Tasks;
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
    @Query("SELECT *FROM Accounts")
    List<Accounts> getAllAccounts();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(UserProfile...userProfiles);
    @Query("UPDATE userprofile SET user_status=:status WHERE user_email = :email")

    void updateUsers(String status , String email);
    @Insert
    void insertAccounts(Accounts...accounts);
    @Query("SELECT project_name FROM Projects WHERE project_accountName = :Account")
    List<String> getAllProjects(String Account);
    @Query("UPDATE userprofile SET user_name=:name ,user_phone=:phone ,user_status=:status WHERE user_email = :email")
    void updateProfileInfo(String name,String email,String phone,String status);

    @Query("UPDATE userprofile SET user_name=:name ,user_phone=:phone ,user_status=:status , image_uri=:image WHERE user_email = :email")
    void updateProfile(String name,String email,String phone,String status , String image);

    @Query("UPDATE Accounts SET account_name =:name,account_email=:email ,account_phone=:phone ,account_address=:address ,account_sector=:sector  WHERE account_id= :id")
    void updateAccount(String name,String email,String phone,String address ,String sector,int id);

    @Insert
    void insertAllProjects(Projects...projects);

    @Query("SELECT project_name FROM Projects")
    List<String> getAllProjects();

    @Query("SELECT * FROM Projects")
    List<Projects> query_all_projects();


    @Insert
    void insertSector(Sectors...s);



    @Query("SELECT name FROM Sectors WHERE name = :name")
    List<String> sector_exists(String name);

    //@Query("IF NOT EXISTS (SELECT name FROM Sectors Where name = :sector_name) ")
    //boolean theSectorExists(String sector_name)

    @Query("SELECT * FROM Projects WHERE project_name = :projectName")
    Projects getProjectDetails(String projectName);


    @Query("SELECT task_finished_hours FROM Tasks WHERE task_project_name = :project_name")
    List<Float> get_task_finishedHours(String project_name);

    @Query("SELECT task_estimated_hours FROM Tasks WHERE task_project_name = :project_name")
    List<Float> get_task_totalHours(String project_name);

    @Query("SELECT name FROM Sectors")
    List<String> getAllSectors();

    @Query("SELECT * FROM Tasks WHERE task_project_name = :project_name")
    List<Tasks> get_project_tasks(String project_name);

    @Query("SELECT * FROM Projects WHERE project_name =:name")
    Projects get_the_project(String name);

    @Query("SELECT * FROM Sectors")
    List<Sectors> sectors_list();

    @Query("SELECT account_name FROM Accounts")
    List<String> retrieve_accounts();

  /*  @Query("SELECT task_member FROM Tasks WHERE task_project_name=:name")
    List<String> get_project_contributers(String name);*/

    @Query("SELECT user_email FROM UserProfile")
    List<String>getUserEmail();








}
