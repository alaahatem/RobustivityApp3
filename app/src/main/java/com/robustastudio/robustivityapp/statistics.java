package com.robustastudio.robustivityapp;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.print.PrintAttributes;
import android.print.pdf.PrintedPdfDocument;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by MALAK SHAKER on 4/3/2018.
 */

public class statistics extends AppCompatActivity {


   // private List<String> finishedHourslist;
   // private List<String> estimatedHourslist;
  //  private Double totalFinished;
   // private Double totalEstimated;
    private DonutProgress progress ;
    private DonutProgress toggledHours ;
    private DonutProgress stoppage ;
    private BarChart contributers;

    private List<Float> finishedHours;
    private List<Float> estimatedHours;
    String value;
    private Button export;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_statistics);

        Bundle extras = getIntent().getExtras();
        String projectName  = extras.getString("projectName");
        progress = (DonutProgress) findViewById(R.id.donut_progress);
        toggledHours = (DonutProgress) findViewById(R.id.toggled_hours);
        stoppage = (DonutProgress) findViewById(R.id.stoppage);
        contributers = (BarChart) findViewById(R.id.bar_chart);
        export =findViewById(R.id.export);

        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity").allowMainThreadQueries().build();
        finishedHours  = db.userDao().get_task_finishedHours(projectName);
        estimatedHours  = db.userDao().get_task_totalHours(projectName);

         Float completed_tasks_time =0.0f;
         Float total_tasks_time=0.0f;

        for(int i=0;i<finishedHours.size();i++){
            completed_tasks_time += finishedHours.get(i);
        }
        for(int i=0;i<estimatedHours.size();i++){
            total_tasks_time += estimatedHours.get(i);
        }

        ArrayList<BarEntry> BarEntries = new ArrayList<>();
        BarEntries.add(new BarEntry(0,30));
        BarEntries.add(new BarEntry(1,20));
        BarEntries.add(new BarEntry(2,10));

        BarDataSet dataSet = new BarDataSet(BarEntries,"Dates");

        ArrayList<String> Dates = new ArrayList<>();
        Dates.add("malak");
        Dates.add("mostafa");
        Dates.add("mohamed");
       // bar_data.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));


        BarData bar_data = new BarData(dataSet);
        bar_data.setBarWidth(0.3f);
        contributers.setData(bar_data);

        XAxis xAxis = contributers.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]{"malak","mohamed","mostafa"}));


        //String percentage = String.valueOf((completed_tasks_time/total_tasks_time)*100);
        //double y =((20.0/100.0)*100.0);

          //  String percentage = Double.toString(20.0);
        Double malak;
        // malak = ((5.0 / 10.0) * 100);
        // x.setDonut_progress(malak);
        //Toast.makeText(getApplicationContext(), malak, Toast.LENGTH_LONG).show();
        //value =Double.toString(malak);

        progress.setProgress((5.0f/10.0f)*100.0f);
        // progress.donut_text_color();
        toggledHours.setProgress(40.0f);
        toggledHours.setText(""+Math.round(40.0f));
        stoppage.setProgress(70.0f);



       final PrintAttributes x = new PrintAttributes.Builder().build();
        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PdfDocument document= new PdfDocument();
                PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 300, 0).create();
               PdfDocument.Page p= document.startPage(pageInfo);

                   View content = progress;
                   content.draw(p.getCanvas());
                   document.finishPage(p);

                try {

                 //   File file = new File(getApplicationContext().getFilesDir(), "malak.pdf");
                   //FileOutputStream outputStream = openFileOutput("malak.pdf", Context.MODE_PRIVATE);
                    //FileOutputStream outputStream = new FileOutputStream("malak.pdf");


                //File file = new File(Environment.getExternalStorageDirectory().getPath()+"/Mypdf/");
                // file.mkdir();
                 // File file1 = new File(file, "statistics.pdf");
                 // file1.createNewFile();
                   // File file1 = new File(file, "cards_01.pdf");
                  // FileOutputStream output = new FileOutputStream(file1,false);

                    //File yourFile = new File("statistics.pdf");
                   // yourFile.createNewFile(); // if file already exists will do nothing
                    //FileOutputStream oFile = new FileOutputStream(yourFile, false);

                   // document.writeTo(output);
                   // document.close();

                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "statistics.pdf");
                    FileOutputStream output = new FileOutputStream(file,false);
                   //  Toast.makeText(getApplicationContext(),
                   //  "Done"+getApplicationContext().getExternalFilesDir("statistics.pdf"), Toast.LENGTH_LONG).show();


                    //Intent target = new Intent(Intent.ACTION_SEND);
                   // target.setData(Uri.fromFile(file1));
                   // target.setType("application/pdf");
                   // target.addFlags(target.FLAG_GRANT_READ_URI_PERMISSION);
                    //target.addFlags(target.FLAG_GRANT_WRITE_URI_PERMISSION);




                    //Uri fileUri = FileProvider.getUriForFile(
                           // getApplicationContext(),
                           // "com.robustastudio.robustivityapp",
                           // file);

                   // target.setDataAndType(fileUri, "application/pdf");
                    // Set the result
                    //MainActivity.this.setResult(Activity.RESULT_OK,mResultIntent);

                   // target.putExtra(Intent.EXTRA_STREAM, fileUri);
                   // target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                    try {
                   // startActivity(Intent.createChooser(target, "Pick an Email provider"));

                    } catch (ActivityNotFoundException e) {
                        // Instruct the user to install a PDF reader here, or something
                        Toast.makeText(getApplicationContext(),"msh 3aref yeb3at",Toast.LENGTH_LONG).show();
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Something wrong: " + e.toString(),
                            Toast.LENGTH_LONG).show();
                }






            }
        });










        //x.setInnerBottomText("Done");



         



    }





}
