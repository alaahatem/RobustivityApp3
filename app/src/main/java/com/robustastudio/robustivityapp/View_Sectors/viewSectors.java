package com.robustastudio.robustivityapp.View_Sectors;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.robustastudio.robustivityapp.Add_sector.Add_sector_dialog;
import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.Models.Sectors;
import com.robustastudio.robustivityapp.R;
import com.robustastudio.robustivityapp.Adapters.SectorsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MALAK SHAKER on 3/29/2018.
 */

public class  viewSectors extends AppCompatActivity implements All_sectors_View,Add_sector_dialog.sectorDialog{


    Button AddSector ;
    List<String> sectors;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    public View_sectors_Presenter mpresenter;
    public List<String> Accounts;
    FirebaseDatabase database;
    DatabaseReference reference;
    List<String> sector_names ;

    List<String> available;





    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_sectors);
        mpresenter = new View_sectors_Presenter(viewSectors.this);

        database = FirebaseDatabase.getInstance();
        reference =database.getReference().child("Sectors");
        sector_names = new ArrayList<String>();


        AddSector = (Button) findViewById(R.id.buttonSector);
        mRecyclerView = findViewById(R.id.sectors_list);
        Accounts = new ArrayList<>();
       // AddSector = () findViewById(R.id.sectors_list);

        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity").allowMainThreadQueries().build();

        available =mpresenter.get_sectors(db);
        show_sectors(available);





        AddSector.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                    Add_sector_dialog mdialog = new Add_sector_dialog();
                    mdialog.show(getSupportFragmentManager(),"");


                //startActivity(new Intent(viewSectors.this,Activity_add_sector.class));
            }
        });

    }
    public void applyname(String name){

        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity").allowMainThreadQueries().build();
           // Accounts.add("Gazef");
           // Accounts.add("Sodic");
        mpresenter.name_isValid(db,name,sectors,reference);


    }
    public void name_already_exists(){
        Toast.makeText(getApplicationContext(),"Name already exists",Toast.LENGTH_LONG).show();

    }
    public void nameNotValid(){
        Toast.makeText(getApplicationContext(),"Enter a name",Toast.LENGTH_LONG).show();
    }

   public void show_sectors(List<String> sectors){


       //this.sectors =sectors;
       // mAdapter = new ViewProjectsAdapter(projects,getApplicationContext());
       LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

       mRecyclerView.setLayoutManager(mLayoutManager);
       mRecyclerView.setHasFixedSize(true);
       mAdapter = new SectorsAdapter(sectors,getApplicationContext());
       mRecyclerView.setAdapter(mAdapter);

}

}
