package com.robustastudio.robustivityapp.SearchEngine;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.robustastudio.robustivityapp.Adapters.ProjectsAdapter;
import com.robustastudio.robustivityapp.Adapters.Search_Adapter;
import com.robustastudio.robustivityapp.Adapters.Search_projectsAdapter;
import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.Models.Search_Model;
import com.robustastudio.robustivityapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MALAK SHAKER on 5/2/2018.
 */

public class Activity_Search extends AppCompatActivity implements  SearchView.OnQueryTextListener {

    RecyclerView mreyclerview;
    List<String> accounts;
    List<String> projects;
    List<Search_Model> list;
    Search_Adapter sAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        list = new ArrayList<>();
        accounts = new ArrayList<>();
        projects = new ArrayList<>();


        final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "robustivity").allowMainThreadQueries().build();

        mreyclerview = findViewById(R.id.recycler_view_search);


        accounts=db.userDao().retrieve_accounts();


        for(int i=0;i<accounts.size();i++){

           projects= db.userDao().getAllProjects(accounts.get(i));

            Search_Model model = new Search_Model(accounts.get(i),projects);

            list.add(model);

        }

            for (int y =0;y<list.size();y++){
                Toast.makeText(getApplicationContext(),""+list.get(y).getName()+"-"+list.get(y).getList(),Toast.LENGTH_LONG).show();
            }

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        mreyclerview.setLayoutManager(mLayoutManager);

        mreyclerview.setHasFixedSize(true);

        sAdapter = new Search_Adapter(list,getApplicationContext());
        mreyclerview.setAdapter(sAdapter);


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
        List<Search_Model> listnew = new ArrayList<>();
        if(newText!=null&& !newText.isEmpty()){
            for(int i=0;i<list.size();i++){

                List<String> projectsname = list.get(i).getList();

                for(int x =0;x<projectsname.size();x++){

                    if(projectsname.get(x).toLowerCase().contains(newText)){
                        List<String> entry = new ArrayList<>();
                        entry.add(projectsname.get(x));
                        Search_Model m = new Search_Model(list.get(i).getName(),entry);
                        listnew.add(m);
                    }
                }


            }
            // mAdapter.setFilter(list);
            sAdapter = new Search_Adapter(listnew,getApplicationContext());
            mreyclerview.setAdapter(sAdapter);

        }else{
            sAdapter = new Search_Adapter(list,getApplicationContext());
            mreyclerview.setAdapter(sAdapter);

        }

        return true;
    }

}
