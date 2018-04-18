package com.robustastudio.robustivityapp.ViewSingleProject;

import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.Models.Projects;

/**
 * Created by MALAK SHAKER on 4/12/2018.
 */

public class Project_presenter {

    Project_View mview;

    Project_presenter(Project_View mview){
        this.mview = mview;
    }

    public void get_Single_Project(AppDatabase db, String name){

        Projects p = db.userDao().getProjectDetails(name);
        mview.view_details(p);


    }



}
