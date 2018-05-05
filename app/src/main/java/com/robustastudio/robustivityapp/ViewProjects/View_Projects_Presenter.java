package com.robustastudio.robustivityapp.ViewProjects;

import com.robustastudio.robustivityapp.Database.AppDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MALAK SHAKER on 4/12/2018.
 */

public class View_Projects_Presenter  {

    All_Projects_View mview;
    List<String> projects;

    View_Projects_Presenter(All_Projects_View mview){
        this.mview=mview;
    }
  /*
    public List<String> projectsNames_search(AppDatabase db ,String name){
        List<String> x = new ArrayList<>();
        x=db.userDao().getAllProjects(name);
        return x;
    }
*/
    public void get_all_projects(AppDatabase db,String name){
        //final AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity").allowMainThreadQueries().build();
        projects  = db.userDao().getAllProjects(name);


        if(projects.isEmpty()){

            mview.List_Empty();


        }else{
            mview.get_details(projects);


        }

    }

    public void viewAll(List<String> x){
      //  mview.view_all_projects(x);
    }



}
