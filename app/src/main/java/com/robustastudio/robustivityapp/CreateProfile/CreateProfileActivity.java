package com.robustastudio.robustivityapp.CreateProfile;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.robustastudio.robustivityapp.Adapters.UserAdapter;
import com.robustastudio.robustivityapp.CreateUserProfile.CreateUserProfActivity;
import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.Models.UserProfile;
import com.robustastudio.robustivityapp.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CreateProfileActivity extends AppCompatActivity implements CreateProfile {
    List<UserProfile> filteredList;
    List<UserProfile> userprofiles;
    private CreateProfilePresenter mCreateProfilePresenter;
    private static final String TAG = "CreateProfileActivity";
    RecyclerView recyclerView;
    public UserAdapter adapter;
    FloatingActionButton fab;
    private DatabaseReference mDatabase;
    public AppDatabase db = null;

//    ArrayList<UserProfile> user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mCreateProfilePresenter = new CreateProfilePresenterImpl(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView =findViewById(R.id.recycler_view);
        EditText search = (EditText)findViewById(R.id.search);
         db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"robustivity").allowMainThreadQueries().build();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        userprofiles= db.userDao().getAllprofiles();
        DatabaseReference ref = mDatabase.child("user_profile");
        final List<UserProfile> userProfs = new ArrayList<>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    UserProfile userprof = postSnapshot.getValue(UserProfile.class);
                   userProfs.add(userprof);
                    List<UserProfile>Sync= union(userProfs,userprofiles);
                    for (int i = 0; i <Sync.size() ; i++) {
//                       Toast.makeText(getApplicationContext(),Sync.get(i).getEmail(),Toast.LENGTH_LONG).show();
                    }



                    // here you can access to name property like university.name

                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
               filteredList= mCreateProfilePresenter.filter(editable.toString(),userprofiles);
                adapter.filterlist(filteredList);
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdapter(userprofiles);
        recyclerView.setAdapter(adapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(CreateProfileActivity.this, CreateUserProfActivity.class);
                CreateProfileActivity.this.startActivity(myIntent);

            }
        });
    }
    public <T> List<T> union(List<T> list1, List<T> list2) {
        Set<T> set = new HashSet<T>();

        set.addAll(list1);
        set.addAll(list2);

        return new ArrayList<T>(set);
    }



}
