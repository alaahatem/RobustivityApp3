package com.robustastudio.robustivityapp.Add_sector;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.Models.Sectors;
import com.robustastudio.robustivityapp.R;
import com.robustastudio.robustivityapp.View_Sectors.viewSectors;

import java.util.ArrayList;
import java.util.List;

public class Activity_add_sector extends AppCompatActivity  implements Add_sector_View{

public List<String> Accounts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_pop_add_sector);

       // Button done = (Button) findViewById(R.id.DoneBtn);
        final EditText mEdit = (EditText) findViewById(R.id.sectorName);

        //showdialogs();
        Accounts = new ArrayList<>();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height =dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.8));

        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity")
                .allowMainThreadQueries().build();

        //done.setOnClickListener(new View.OnClickListener() {
            //@Override
          //  public void onClick(View view) {
          //      try {

                 //   String name = mEdit.getText().toString();
                   // Cursor c = db.rawQuery("SELECT * FROM Sectors Where name =")
                  //  List<String> names =db.userDao().sector_exists(name);

                  //  if(names.isEmpty() ){
                  //      Sectors s = new Sectors(name, Accounts);
                    //    db.userDao().insertSector(s);
                    //    Intent myIntent = new Intent(Activity_add_sector.this, viewSectors.class);
                    //    Activity_add_sector.this.startActivity(myIntent);
                  //  }else {
                       // Toast.makeText(getApplicationContext(),"This sector exists",Toast.LENGTH_LONG).show();
                   // }



               // }catch (NullPointerException x){
               //     String name = mEdit.getText().toString();
               //     Toast.makeText(getApplicationContext(),"please enter a name"+name,Toast.LENGTH_LONG).show();
               // }
           // }
      //  });

}

}
