package com.robustastudio.robustivityapp.Accounts;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.robustastudio.robustivityapp.Adapters.AccountAdapter;
import com.robustastudio.robustivityapp.CreateAccounts.CreateActivity;
import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.Models.Accounts;
import com.robustastudio.robustivityapp.R;

import java.util.ArrayList;
import java.util.List;

public class AccountActivity extends AppCompatActivity {
    Button create_account;
    RecyclerView recyclerView;
    List<Accounts> accounts;
    List<Accounts> filteredaccounts;
    public AccountAdapter adapter;
    public AppDatabase db = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        filteredaccounts = new ArrayList<>();
        create_account = findViewById(R.id.createAccount);
        recyclerView =findViewById(R.id.recycler_view_account);
        String sector_name = getIntent().getStringExtra("sectorName");
        db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity").allowMainThreadQueries().build();

        accounts= db.userDao().getAllAccounts();
        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(AccountActivity.this, CreateActivity.class);
                AccountActivity.this.startActivity(myIntent);
            }
        });



        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if(filteredaccounts!=null)
            for (int i = 0; i <accounts.size() ; i++) {
                if(accounts.get(i).getSector().equals(sector_name)){
                    filteredaccounts.add(accounts.get(i));
                }
            }
        adapter = new AccountAdapter(filteredaccounts);
        recyclerView.setAdapter(adapter);
    }
}
