package com.robustastudio.robustivityapp.ViewAllTodos;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.robustastudio.robustivityapp.CreateTodo.CreateTodoView;
import com.robustastudio.robustivityapp.Database.AppDatabase;
import com.robustastudio.robustivityapp.Models.Todo;
import com.robustastudio.robustivityapp.R;

import java.util.List;

/**
 * Created by MALAK SHAKER on 5/3/2018.
 */

public class Activity_view_Todo extends AppCompatActivity implements Todo_view{

    Button add_todo ;
    private RecyclerView mRecyclerView;
    private Todos_Adapter mAdapter;
    public View_Todo_Presenter presenter;
    public List<Todo> todos;
    FirebaseAuth mAuth;
    DatabaseReference ref;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_my_todos);
        mAuth= FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference().child("Todos");

        presenter = new View_Todo_Presenter(Activity_view_Todo.this);
        mRecyclerView = findViewById(R.id.view_todo_rec);

        add_todo = findViewById(R.id.create_todo_button);

        final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "robustivity").allowMainThreadQueries().build();

        todos = db.todoDao().getTodos_details(mAuth.getCurrentUser().getEmail());

        presenter.get_all_todos(db,mAuth.getCurrentUser().getEmail());


        add_todo.setOnClickListener(new View.OnClickListener() {
            //  @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_view_Todo.this, CreateTodoView.class);
                startActivity(intent);

                //db.userDao().insertAllProjects(proj);
                // Toast.makeText(getApplicationContext(),"Inserted"+proj.getName()+proj.getEngagement().get(1)+proj.getStartDate(),Toast.LENGTH_LONG).show();
            }
        });

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                    final int fromPos = viewHolder.getAdapterPosition();
//                    final int toPos = viewHolder.getAdapterPosition();
//                    // move item in `fromPos` to `toPos` in adapter.
                return true;// true if moved, false otherwise
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //Remove swiped item from list and notify the RecyclerView

                Todo deleted_todo = todos.get(viewHolder.getLayoutPosition());

                todos.remove(viewHolder.getLayoutPosition());
                ref.child(deleted_todo.getId()).removeValue();
                db.todoDao().delete_todo(deleted_todo.getId());

                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setHasFixedSize(true);
                mAdapter = new Todos_Adapter(todos,getApplicationContext());
                mRecyclerView.setAdapter(mAdapter);

               /* todos.remove(viewHolder.getLayoutPosition());
                mRecyclerView.removeViewAt(viewHolder.getLayoutPosition());
                mAdapter.notifyItemRemoved(viewHolder.getLayoutPosition());
                mAdapter.notifyItemRangeChanged(viewHolder.getLayoutPosition(), todos.size());*/
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);


    }

    public void get_details(List<Todo> todos) {

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        Toast.makeText(getApplicationContext(),"DONE",Toast.LENGTH_LONG).show();


        mAdapter = new Todos_Adapter(todos,getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);

    }

    public void List_Empty(){

        Toast.makeText(getApplicationContext(),"No Items",Toast.LENGTH_LONG).show();

    }

}
