package com.blogspot.tellwap.tafakarinayesu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeScreen extends AppCompatActivity {
    private Button btn_continue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        SharedPreferences sharedPreferences = getSharedPreferences("Tafakari" , MODE_PRIVATE);
        String firstAttemp = sharedPreferences.getString("firstToken",null);
        //check the shared preferences
        if (firstAttemp != null){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

       // btn_continue = (Button) findViewById(R.id.button_continue);
        //on button clicked then lets open the authetication activities

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),PhoneAuthActivity.class));
            }
        });
    }
}
