package com.robustastudio.robustivityapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.lzyzsd.circleprogress.DonutProgress;


/**
 * Created by MALAK SHAKER on 4/3/2018.
 */

public class statistics extends AppCompatActivity {


   // private List<String> finishedHourslist;
   // private List<String> estimatedHourslist;
  //  private Double totalFinished;
   // private Double totalEstimated;
    private DonutProgress x ;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_statistics);

        Bundle extras = getIntent().getExtras();
        String projectName  = extras.getString("projectName");
        x = (DonutProgress) findViewById(R.id.donut_progress);

        x.setDonut_progress("55");
        //x.setInnerBottomText("Done");

         



    }





}
