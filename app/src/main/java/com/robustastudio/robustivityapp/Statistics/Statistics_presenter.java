package com.robustastudio.robustivityapp.Statistics;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;

import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.Models.Projects;
import com.robustastudio.robustivityapp.Models.Tasks;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by MALAK SHAKER on 4/14/2018.
 */

public class Statistics_presenter {
    float progress;
    float total_toggled_hours =0.0f;
    int totaldays_task =0;
    String val ="";
    float stoppage;
    public float cost;

    Statistics_view mview;

    public Statistics_presenter(Statistics_view mview){
        this.mview = mview;
    }

    public void getProgress(AppDatabase db,String projectName){

       List<Tasks> tasks = db.userDao().get_project_tasks(projectName);
        Float completed_tasks_time = 0.0f;
        Float total_tasks_time = 0.0f;

        for(int j=0;j<tasks.size();j++){
            if(tasks.get(j).getFinished_hours() ==tasks.get(j).getEstimated_hours())
            {
                completed_tasks_time+= tasks.get(j).getFinished_hours();
            }
            total_tasks_time += tasks.get(j).getEstimated_hours();
        }

        /// 1
        //set project progress
        float totalprogress =(completed_tasks_time/total_tasks_time)*100.0f;
        //String val = String.format("%.2f",totalprogress);
       val = String.format("%.2f",1.353564);
        progress=Float.parseFloat(val);


        //// 2
        //set Toggled hours
        for(int t=0;t<tasks.size();t++){
            total_toggled_hours += tasks.get(t).getFinished_hours();
        }
        //toggledHours.setText(String.valueOf(total_toggled_hours));
        String value;

        try {
             value=String.valueOf(15.5);

        }catch (NumberFormatException ex){
            value="";

        }

       Projects project = db.userDao().get_the_project(projectName);

        /////set stoppage
        Date date = project.getStartDate();
        //Date date = new Date(18,2,10);
        Date today = new Date(18,3,10);

        SimpleDateFormat format = new SimpleDateFormat(
                "EEE, MM d, yyyy");
        format.format(today);
        format.format(date);

        int total_days=  (int)( (today.getTime() - date.getTime()) / (1000 * 60 * 60 * 24));
        //loop on all tasks and get the toggled days and add them
        for(int i=0;i<tasks.size();i++){
            // totaldays_task  += tasks.get(i).getDays();
        }

        //stoppage.setProgress((totaldays_task/total_days)*100.0f);
        // total_days=10;
        totaldays_task=2;
        float ee = (float) total_days;
        float eee =(float) totaldays_task;
        float totalF = (eee/ee)*100.0f;
        //val = String.format(".2f",totalF);
        val = String.format(".2f",30.55);
        stoppage = Float.parseFloat(val);

        //Project cost
        String c_cost;
        cost = project.getProject_cost();
        try{
            c_cost =""+cost;

        }catch (NumberFormatException ex){
            c_cost="";
        }

        //project cost flow
        float costflow = (totalprogress/cost)*100.0f;
        val = String.format(".2f",costflow);
        costflow=Float.parseFloat("50");
       // costflow=Float.parseFloat(val);


        //project profitability
        float profit = (costflow *(project.getContracted_cost()-project.getPlanned_cost()));
        val =String.format(".2f",profit);
        float profitabile =Float.parseFloat("20");
       // float profit =Float.parseFloat(val);


        ////project expected progress
        Date start = project.getStartDate();
        Date end =project.getEndDate();
        format.format(start);
        format.format(end);
        int total_project_days=  (int)( (end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24));

        int expected = total_days/total_project_days;
        float expected_float=(float) expected;
        val =String.format(".2f",expected_float);
        //float expect = Float.parseFloat(val);
        float expect = Float.parseFloat("30");



        mview.set_project_progress(progress,value,stoppage,c_cost,costflow,profitabile,expect);



    }

    public void createFile(){

        try {
            File file = new File(Environment.getExternalStorageDirectory().getPath() + "/Mypdf/");
            file.mkdir();
            File file1 = new File(file, "Statistics1.pdf");
            FileOutputStream output = new FileOutputStream(file1, false);
            mview.createimage(output);
        }catch (FileNotFoundException x){
            mview.fileNotFound();
        }catch (ClassCastException ex){
            mview.castclass();

        }


    }






}