package com.robustastudio.robustivityapp.View_Sectors;

import android.database.sqlite.SQLiteConstraintException;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.Models.Sectors;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MALAK SHAKER on 4/13/2018.
 */

public class View_sectors_Presenter {

    All_sectors_View mview;

    View_sectors_Presenter(All_sectors_View mview){
        this.mview=mview;
    }

    public List<String> get_sectors(AppDatabase db){
        return db.userDao().getAllSectors();

    }


    public void name_isValid(AppDatabase db, String name, List<String> sectors,DatabaseReference reference){
        List<String> names = db.userDao().sector_exists(name);
        List<String> show;
        List<Sectors> list = db.userDao().sectors_list();
        int id=0;


        if(names.isEmpty()){

           /* for (int i=0;i<list.size();i++){
                if(list.get(i).id >id ){
                    id =list.get(i).id;
                }
            }
            id = id+1;*/

                 Sectors s = new Sectors(name,0);
                 //s.setId(id);

                 db.userDao().insertSector(s);

                 try {
                     reference.child(name).setValue(s);
                 }catch (SQLiteConstraintException x){
                     mview.name_already_exists();

                 }

                 show = get_sectors(db);
                 mview.show_sectors(show);

                 //sectors.add(name);
                // mview.show_sectors(sectors);
            //    Intent myIntent = new Intent(Activity_add_sector.this, viewSectors.class);
            //    Activity_add_sector.this.startActivity(myIntent);

        }else {
            mview.name_already_exists();

        }
    }


}
