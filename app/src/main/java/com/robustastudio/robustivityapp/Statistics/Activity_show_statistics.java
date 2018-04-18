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


/**
 * Created by MALAK SHAKER on 4/3/2018.
 */

public class Activity_show_statistics extends AppCompatActivity {


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
    public float cost;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_statistics);
        //grantPermission();
        grantPermission2();

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
        total_toggled_hours=0.0f;


        final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "robustivity").allowMainThreadQueries().build();
        finishedHours = db.userDao().get_task_finishedHours(projectName);
        estimatedHours = db.userDao().get_task_totalHours(projectName);
        project = db.userDao().get_the_project(projectName);
        tasks = db.userDao().get_project_tasks(projectName);

        Float completed_tasks_time = 0.0f;
        Float total_tasks_time = 0.0f;
        int totaldays_task =0;


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
        try {
            float totalprogress2 = round(totalprogress, 1);
        }catch (NumberFormatException ex){

        }
       // progress.setProgress(totalprogress2);
        progress.setProgress((5.0f / 10.0f) * 100.0f);


        //// 2
        //set Toggled hours
        for(int t=0;t<tasks.size();t++){
            total_toggled_hours += tasks.get(t).getFinished_hours();
        }
        //toggledHours.setText(String.valueOf(total_toggled_hours));
        try {
            toggledHours.setText(String.valueOf(15.5));
        }catch (NumberFormatException ex){
            toggledHours.setText("");
        }




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
        try {
            float totaldays_taskF = round(totalF, 1);
        }catch (NumberFormatException ex){

        }
        //stoppage.setProgress(totaldays_taskF);



        //Project cost
        cost = project.getProject_cost();
        try{
            costview.setText(""+cost);
        }catch (NumberFormatException ex){
            costview.setText("");
        }


        //project cost flow
        float costflow = (totalprogress/cost)*100.0f;
        try {
            float costflowfinal = round(costflow, 1);
        }catch (NumberFormatException ex){

        }
       // cost_flow.setProgress(costflowfinal);


        //project profitability
        float profit = (costflow *(project.getContracted_cost()-project.getPlanned_cost()));
        try {
            float profitTotal = round(profit, 1);
        }catch (NumberFormatException ex){

        }
       // profitability.setProgress(profitTotal);



        ////project expected progress
        Date start = project.getStartDate();
        Date end =project.getEndDate();
        format.format(start);
        format.format(end);
        int total_project_days=  (int)( (end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24));

        int expected = total_days/total_project_days;
        float expected_float=(float) expected;
        try {
            float expected_final = round(expected_float, 1);
        }catch (NumberFormatException ex){

        }
       // expected_progress.setProgress(expected_final);


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


                File file = new File(Environment.getExternalStorageDirectory().getPath()+"/Mypdf/");
                 file.mkdir();
                 File file1 = new File(file, "Statistics.pdf");

                try {
                    FileOutputStream output = new FileOutputStream(file1, false);
                    Toast.makeText(getApplicationContext(),Environment.getExternalStorageDirectory().getAbsolutePath(),Toast.LENGTH_LONG).show();


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

                }catch (FileNotFoundException x){
                    Toast.makeText(getApplicationContext(),"not found",Toast.LENGTH_LONG).show();
                }catch (ClassCastException x){
                    Toast.makeText(getApplicationContext(),x.toString(),Toast.LENGTH_LONG).show();
                }

            }
        });




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

    public void grantPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(Activity_show_statistics.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(Activity_show_statistics.this)
                    .setTitle("Request Permission")
                    .setMessage("This permission needed to access external storage")
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
                    .setMessage("This permission needed to access external storage")
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

    public float round(float d, int decimalPlace) {

        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }




        /*export.setOnClickListener(new View.OnClickListener() {
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
                 // File file1 = new File(file, "Activity_show_statistics.pdf");
                 // file1.createNewFile();
                   // File file1 = new File(file, "cards_01.pdf");
                  // FileOutputStream output = new FileOutputStream(file1,false);

                    //File yourFile = new File("Activity_show_statistics.pdf");
                   // yourFile.createNewFile(); // if file already exists will do nothing
                    //FileOutputStream oFile = new FileOutputStream(yourFile, false);

                   // document.writeTo(output);
                   // document.close();

                    File mFolder = new File(getFilesDir() + "/sample");
                    File imgFile = new File(mFolder.getAbsolutePath() + "/someimage.pdf");
                    if (!mFolder.exists()) {
                        mFolder.mkdir();
                    }
                    if (!imgFile.exists()) {
                        imgFile.createNewFile();
                    }
                    FileOutputStream fos = new FileOutputStream(imgFile);
                   // Document doc = new Document();
                   // doc.open();
                    //doc.add(new Paragraph("aa"));
                    //PdfWriter.getInstance(document,fos);



                    fos.close();
                    // Toast.makeText(getApplicationContext(),
                    //"Done"+getApplicationContext().getFilesDir(), Toast.LENGTH_LONG).show();


                   Intent target = new Intent(Intent.ACTION_SEND);
                   //target.setData(Uri.fromFile(imgFile));
                   //target.setType("application/pdf");
                    //target.setType("application/pdf");
                    Uri fileUri = FileProvider.getUriForFile(
                            getApplicationContext(),
                            "com.robustastudio.robustivityapp",
                            imgFile);
                    target .putExtra(Intent.EXTRA_STREAM, fileUri);
                    target.addFlags(target.FLAG_GRANT_READ_URI_PERMISSION);
                    target.addFlags(target.FLAG_GRANT_WRITE_URI_PERMISSION);






                    target.setDataAndType(fileUri, "application/pdf");
                    // Set the result
                    //MainActivity.this.setResult(Activity.RESULT_OK,mResultIntent);

                   // target.putExtra(Intent.EXTRA_STREAM, fileUri);
                   // target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                    try {
                    startActivity(Intent.createChooser(target, "Pick an Email provider"));

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
        });*/









}
