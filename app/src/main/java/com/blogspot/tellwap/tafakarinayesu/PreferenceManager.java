package com.blogspot.tellwap.tafakarinayesu;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by chami on 7/3/18.
 */

public class PreferenceManager {

    private Context context;
    private SharedPreferences sharedPreferences;

    public PreferenceManager(Context context) {
        this.context = context;
        getSharedPreference();
    }

    private void getSharedPreference(){
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name),context.MODE_PRIVATE);

    }

    public void writePreference(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("key","ok");
        editor.commit();
    }

    public boolean checkPreference() {
        boolean status = false;
        if (sharedPreferences.getString("key", "null").equals("null")) {
            status = false;
        } else {
            status = true;
        }
        return status;
    }

    public void clearPreference(){
        sharedPreferences.edit().clear().commit();
    }
}
