package com.robustastudio.robustivityapp.CreateProfile;

import com.robustastudio.robustivityapp.Adapters.UserAdapter;
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
    public UserAdapter adapter;


    public CreateProfilePresenterImpl(CreateProfile mCreateProfile) {
        this.mCreateProfile = mCreateProfile;

    }


    public  List<UserProfile> filter(String text, List<UserProfile> user) {
        filteredList = new ArrayList<>();


        for ( UserProfile item : user) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);





            }

        }
        return filteredList;
    }
}
