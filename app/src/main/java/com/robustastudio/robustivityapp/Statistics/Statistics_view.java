package com.robustastudio.robustivityapp.Statistics;

import java.io.FileOutputStream;

/**
 * Created by MALAK SHAKER on 4/14/2018.
 */

public interface Statistics_view {


    public void set_project_progress(float progress,String y,float stoppage,String cost,float costflow,float profit,float expect);
    public void fileNotFound();
    public void castclass();
    public void createimage(FileOutputStream out);
    public void createExcel(FileOutputStream out);


}
