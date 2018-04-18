package com.robustastudio.robustivityapp.View_Sectors;

import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.Models.Sectors;

import java.util.List;

/**
 * Created by MALAK SHAKER on 4/13/2018.
 */

public class View_sectors_Presenter {

    All_sectors_View mview;

    View_sectors_Presenter(All_sectors_View mview){
        this.mview=mview;
    }

    public void get_sectors(AppDatabase db){
       List<String> sectors  = db.userDao().getAllSectors();
       mview.show_sectors(sectors);

    }

    public void name_isValid(AppDatabase db,String name,List<String> Accounts,List<String> sectors){
        List<String> names = db.userDao().sector_exists(name);


        if(names.isEmpty()){
                 Sectors s = new Sectors(name, Accounts);
                 db.userDao().insertSector(s);
                 get_sectors(db);
                 //sectors.add(name);
                // mview.show_sectors(sectors);
            //    Intent myIntent = new Intent(Activity_add_sector.this, viewSectors.class);
            //    Activity_add_sector.this.startActivity(myIntent);

        }else {
            mview.name_already_exists();

        }
    }


}
