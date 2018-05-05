package com.robustastudio.robustivityapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.firebase.auth.FirebaseAuth;
import com.robustastudio.robustivityapp.CreateProfile.CreateProfileActivity;
import com.robustastudio.robustivityapp.CreateTask.CreateTaskView;
import com.robustastudio.robustivityapp.SearchEngine.Activity_Search;
import com.robustastudio.robustivityapp.ViewProfile.ViewProfileActivity;
import com.robustastudio.robustivityapp.ViewAllTodos.Activity_view_Todo;
import com.robustastudio.robustivityapp.View_Sectors.viewSectors;



/**
 * Created by MALAK SHAKER on 4/28/2018.
 */
// Instances of this class are fragments representing a single
// object in our collection.
public class Shortcuts_fragment extends Fragment {

    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.shortcuts, container, false);
      mAuth = FirebaseAuth.getInstance();

        addListenerOnButton(rootView,mAuth);
        return rootView;
    }

    public void addListenerOnButton(View v,FirebaseAuth mAuth) {

        final FirebaseAuth mm = mAuth;


       CardView todo =  v.findViewById(R.id.create_todo_card);
       CardView task =  v.findViewById(R.id.create_task_Card);
        CardView profile =  v.findViewById(R.id.myprofile_card);
        CardView sectors =  v.findViewById(R.id.sector_card);
        CardView users =  v.findViewById(R.id.users_cards);
        CardView search =  v.findViewById(R.id.search_card);
        CardView logout =  v.findViewById(R.id.logout_card);



        sectors.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent browserIntent =
                        new Intent(getActivity(), viewSectors.class);
                startActivity(browserIntent);

            }

        });

        search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent browserIntent =
                        new Intent(getActivity(), Activity_Search.class);
                startActivity(browserIntent);

            }

        });

        profile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent browserIntent =
                        new Intent(getActivity(), ViewProfileActivity.class);
                startActivity(browserIntent);

            }

        });
        todo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent browserIntent =
                        new Intent(getActivity(), Activity_view_Todo.class);
                startActivity(browserIntent);

            }

        });

        task.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent browserIntent =
                        new Intent(getActivity(), CreateTaskView.class);
                startActivity(browserIntent);

            }

        });

        users.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent browserIntent =
                        new Intent(getActivity(), CreateProfileActivity.class);
                startActivity(browserIntent);

            }

        });

        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                mm.signOut();

            }

        });




    }
}