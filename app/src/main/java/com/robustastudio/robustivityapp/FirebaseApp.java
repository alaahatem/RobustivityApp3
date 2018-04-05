package com.robustastudio.robustivityapp;

import android.app.Application;

/**
 * Created by hp on 04/04/2018.
 */

public class FirebaseApp extends Application{
    public  void onCreate(){
        super.onCreate();
    }

    public static String EncodeString(String string) {
        return string.replace(".", ",");
    }

    public static String DecodeString(String string) {
        return string.replace(",", ".");
    }
}
