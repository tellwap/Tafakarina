package com.blogspot.tellwap.tafakarinayesu;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class UserProfile extends AppCompatActivity {

    private TextView user_profile_name;
    private TextView user_profile_phonenumber;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        user_profile_name = (TextView) findViewById(R.id.user_account);
        user_profile_phonenumber = (TextView) findViewById(R.id.user_profile_phonenumber);

        sharedPreferences = getSharedPreferences("Tafakari", MODE_PRIVATE);
        String username = sharedPreferences.getString("username",null);
        String phonenumber = sharedPreferences.getString("firstToken",null);

        user_profile_name.setText(username);
        user_profile_phonenumber.setText(phonenumber);
    }
}
