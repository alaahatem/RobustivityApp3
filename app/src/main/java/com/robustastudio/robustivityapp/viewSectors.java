package com.robustastudio.robustivityapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by MALAK SHAKER on 3/29/2018.
 */

public class viewSectors extends AppCompatActivity{

    Button AddSector ;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_sectors);

        AddSector = (Button) findViewById(R.id.buttonSector);

        AddSector.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                startActivity(new Intent(viewSectors.this,popAddSector.class));
            }
        });

    }

}
