package com.blogspot.tellwap.tafakarinayesu;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by chami on 3/23/18.
 */

public class Tafakari extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
