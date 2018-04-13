package com.robustastudio.robustivityapp.CreateProfile;

import android.content.Context;

import com.robustastudio.robustivityapp.Models.UserProfile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 12/04/2018.
 */

public class CreateProfilePresenterImpl implements CreateProfilePresenter{
CreateProfile mCreateProfile;
   public static  List<UserProfile> filteredList;
    List<UserProfile> userprofiles;


    public CreateProfilePresenterImpl(CreateProfile mCreateProfile) {
        this.mCreateProfile = mCreateProfile;

    }


    public void filter(String text,List<UserProfile> user) {
        filteredList = new ArrayList<>();


        for ( UserProfile item : user) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);



            }

        }

//        adapter.filterlist(filteredList);
    }
}
