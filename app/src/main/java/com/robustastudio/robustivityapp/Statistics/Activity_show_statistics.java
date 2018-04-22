package com.robustastudio.robustivityapp.Statistics;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.print.PrintAttributes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.Models.Projects;
import com.robustastudio.robustivityapp.Models.Tasks;
import com.robustastudio.robustivityapp.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SplittableRandom;


/**
 * Created by MALAK SHAKER on 4/3/2018.
 */

public class Activity_show_statistics extends AppCompatActivity  implements Statistics_view{


   // private List<String> finishedHourslist;
   // private List<String> estimatedHourslist;
  //  private Double totalFinished;
   // private Double totalEstimated;
    private DonutProgress progress ;
    private TextView toggledHours ;
    private DonutProgress stoppage ;
    private BarChart contributers;
    private TextView costview ;
    private DonutProgress cost_flow;
    private DonutProgress profitability;
    private DonutProgress expected_progress;
    private DonutProgress velocity;


    private List<Float> finishedHours;
    private List<Float> estimatedHours;
    String value;
    private Button export;
    private int STORAGE_CODE =1;
    private int STORAGE_CODE_READ =2;
    public List<String> users;
    public List<Tasks> tasks;
    public float total_toggled_hours;
    public Projects project;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_statistics);


        Bundle extras = getIntent().getExtras();
        String projectName = extras.getString("projectName");
        progress = (DonutProgress) findViewById(R.id.donut_progress);
        toggledHours =  findViewById(R.id.toggled_hours);
        stoppage = (DonutProgress) findViewById(R.id.stoppage);
        contributers = (BarChart) findViewById(R.id.bar_chart);
        costview =findViewById(R.id.project_cost);
        cost_flow =findViewById(R.id.cost_flow);
        profitability =findViewById(R.id.profitability);
        expected_progress=findViewById(R.id.expected_progress);
        velocity =findViewById(R.id.velocity);
        export = findViewById(R.id.export);
        String val;
        float totalprogress=0.0f;

        grantPermission();
        grantPermission2();

        final Statistics_presenter mpresenter = new Statistics_presenter(Activity_show_statistics.this);


        final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "robustivity").allowMainThreadQueries().build();

        mpresenter.getProgress(db,projectName);



        finishedHours = db.userDao().get_task_finishedHours(projectName);
        estimatedHours = db.userDao().get_task_totalHours(projectName);
        project = db.userDao().get_the_project(projectName);
        tasks = db.userDao().get_project_tasks(projectName);



        ////project velocity
        //float pvelocity = totalprogress2/expected_final;
       // velocity.setProgress(pvelocity);


        ArrayList<BarEntry> BarEntries = new ArrayList<>();
        BarEntries.add(new BarEntry(0, 30));
        BarEntries.add(new BarEntry(1, 20));
        BarEntries.add(new BarEntry(2, 10));

        BarDataSet dataSet = new BarDataSet(BarEntries, "Dates");

        ArrayList<String> Dates = new ArrayList<>();
        Dates.add("malak");
        Dates.add("mostafa");
        Dates.add("mohamed");
        // bar_data.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));


        BarData bar_data = new BarData(dataSet);
        bar_data.setBarWidth(0.3f);
        contributers.setData(bar_data);

        XAxis xAxis = contributers.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]{"malak", "mohamed", "mostafa"}));

        // progress.donut_text_color();
        //toggledHours.setProgress(40.0f);
        //toggledHours.setText("" + Math.round(40.0f));



        final PrintAttributes x = new PrintAttributes.Builder().build();

        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mpresenter.createFile();
                Toast.makeText(getApplicationContext(),Environment.getExternalStorageDirectory().getAbsolutePath(),Toast.LENGTH_LONG).show();

            }
        });

    }

    public void grantPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(Activity_show_statistics.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(Activity_show_statistics.this)
                    .setTitle("Request Permission")
                    .setMessage("This permission needed to write external storage")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(Activity_show_statistics.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_CODE);
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).create().show();
        } else {
            ActivityCompat.requestPermissions(Activity_show_statistics.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_CODE);
        }
    }

    public void grantPermission2(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(Activity_show_statistics.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(Activity_show_statistics.this)
                    .setTitle("Request Permission")
                    .setMessage("This permission needed to read external storage")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(Activity_show_statistics.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_CODE_READ);
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).create().show();
        } else {
            ActivityCompat.requestPermissions(Activity_show_statistics.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_CODE_READ);
        }
    }


    public void set_project_progress(float x,String v,float stop,String c,float costflow_c,float p,float f){
        progress.setProgress(x);
        toggledHours.setText(v);
        stoppage.setProgress(stop);
        //stoppage.setProgress(Float.parseFloat(val));

        costview.setText(c);

        cost_flow.setProgress(costflow_c);

        profitability.setProgress(p);
        expected_progress.setProgress(f);
    }

    public void onRequestPermissionsResult(int requestcode, @NonNull String[]permissions,@NonNull int[] grant){
        if(requestcode== STORAGE_CODE){
            if(grant.length >0 && grant[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getApplicationContext(),"granted for write",Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getApplicationContext(),"Not granted for write",Toast.LENGTH_LONG).show();
            }

        }


            if(requestcode== STORAGE_CODE_READ&&grant.length >0 && grant[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getApplicationContext(),"granted for read",Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getApplicationContext(),"Not granted for read",Toast.LENGTH_LONG).show();
            }

    }



    public Bitmap get_Bitmap(Context context,View view){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    public void fileNotFound(){
        Toast.makeText(getApplicationContext(),"not found",Toast.LENGTH_LONG).show();
    }

    public void castclass(){
        Toast.makeText(getApplicationContext(),"cast class problem",Toast.LENGTH_LONG).show();
    }

    public void createimage(FileOutputStream output){

        Bitmap x = get_Bitmap(Activity_show_statistics.this,findViewById(R.id.statistics));

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(x.getWidth(), x.getHeight(), 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#ffffff"));
        canvas.drawPaint(paint);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(x, 0, 0 , null);
        document.finishPage(page);

        try{
            document.writeTo(output);
            Toast.makeText(getApplicationContext(),"PDF created",Toast.LENGTH_LONG).show();

        }catch (IOException ex){
            Toast.makeText(getApplicationContext(),"failed at PDF",Toast.LENGTH_LONG).show();
        }

    }


}
