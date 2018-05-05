package com.robustastudio.robustivityapp.ViewProjects;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.robustastudio.robustivityapp.CreateProject.CreateProjectView;
import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.Models.Projects;
import com.robustastudio.robustivityapp.Adapters.ProjectsAdapter;
import com.robustastudio.robustivityapp.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by MALAK SHAKER on 3/28/2018.
 */


public class Activity_View_Projects extends AppCompatActivity implements All_Projects_View, SearchView.OnQueryTextListener {
    Button addproj ;
    public List<String> projects;



    String accountname ;
    Projects proj ;
    public View_Projects_Presenter presenter;



    private RecyclerView mRecyclerView;
    private ProjectsAdapter mAdapter;
    SearchView sv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_projects);
        presenter = new View_Projects_Presenter(Activity_View_Projects.this);
        mRecyclerView = findViewById(R.id.projectsList);

        addproj = findViewById(R.id.btnAddProject);
        accountname="";

        //Toolbar toolbar = findViewById(R.id.toolbar_search);
        //setSupportActionBar(toolbar);

       // sv = findViewById(R.id.search_view);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            accountname = extras.getString("name");
        }






        final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "robustivity").allowMainThreadQueries().build();

        projects = db.userDao().getAllProjects(accountname);

        presenter.get_all_projects(db,accountname);



         List<String> m = new ArrayList<>();
         m.add("Design");
         m.add("Front End");
         m.add("Web development");

         //proj = new Projects("Gazef Mobile App", "WebApp",m, new Date(18,3,10), new Date(18,2,10), "Branding", "BTECH", 20.0f,30.0f,25.0f);


        addproj.setOnClickListener(new View.OnClickListener() {
        //  @Override
         public void onClick(View view) {
             Intent intent = new Intent(Activity_View_Projects.this, CreateProjectView.class);
             intent.putExtra("accountName",accountname);
                startActivity(intent);

           //db.userDao().insertAllProjects(proj);
            // Toast.makeText(getApplicationContext(),"Inserted"+proj.getName()+proj.getEngagement().get(1)+proj.getStartDate(),Toast.LENGTH_LONG).show();
          }
         });


    }


    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu_search_items,menu);
        MenuItem menuItem = menu.findItem(R.id.search_option);
        SearchView sv = (SearchView) menuItem.getActionView();
        sv.setOnQueryTextListener(this);

    return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {


        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        newText = newText.toLowerCase();
        List<String> list = new ArrayList<>();
        if(newText!=null&& !newText.isEmpty()){
            for(int i=0;i<projects.size();i++){
                String p = projects.get(i).toLowerCase();
                if(p.contains(newText)){
                    list.add(projects.get(i));
                }
            }
            // mAdapter.setFilter(list);
            mAdapter = new ProjectsAdapter(list,getApplicationContext());
            mRecyclerView.setAdapter(mAdapter);

        }else{
            mAdapter = new ProjectsAdapter(projects,getApplicationContext());
            mRecyclerView.setAdapter(mAdapter);

        }

        return true;
    }




    public void get_details(List<String> projects) {

        //mAdapter = new ProjectsAdapter(projects,getApplicationContext());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());


        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        Toast.makeText(getApplicationContext(),"DONE",Toast.LENGTH_LONG).show();


         mAdapter = new ProjectsAdapter(projects,getApplicationContext());
         mRecyclerView.setAdapter(mAdapter);

    }

    public void List_Empty(){

          Toast.makeText(getApplicationContext(),"No Items",Toast.LENGTH_LONG).show();

    }

}
