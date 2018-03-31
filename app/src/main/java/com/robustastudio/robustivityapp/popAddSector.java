package com.robustastudio.robustivityapp;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.robustastudio.robustivityapp.Models.Sectors;

import java.util.List;

public class popAddSector extends AppCompatActivity {

public List<String> Accounts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_add_sector);

        Button done = (Button) findViewById(R.id.DoneBtn);
        final EditText mEdit = (EditText) findViewById(R.id.name);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height =dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.8));

        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity")
                .allowMainThreadQueries().build();

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mEdit.getText().toString();
                Sectors s = new Sectors(name,Accounts);
                db.userDao().insertSector(s);
            }
        });

    }
}
