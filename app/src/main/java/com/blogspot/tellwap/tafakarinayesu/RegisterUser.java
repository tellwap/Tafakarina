package com.blogspot.tellwap.tafakarinayesu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tapadoo.alerter.Alerter;

public class RegisterUser extends AppCompatActivity {
    private EditText Username;
    private Button register;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.bible_icon);
        actionBar.setTitle(R.string.app_name);
        actionBar.show();

        SharedPreferences sharedPreferences = getSharedPreferences("Tafakari" , MODE_PRIVATE);
        String username = sharedPreferences.getString("username",null);
        //check the shared preferences
        if (username != null){
            startActivity(new Intent(this, RegisterUser.class));
        }


        Username = (EditText) findViewById(R.id.editTextUsername);
        register = (Button) findViewById(R.id.buttonRegister);
        databaseReference = FirebaseDatabase.getInstance().getReference("user");
        mAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }
    public void registerUser(){
        SharedPreferences sharedPreferences = getSharedPreferences("Tafakari", MODE_PRIVATE);
        String firstAttemp = sharedPreferences.getString("firstToken",null);
        //validate the form
        String username = Username.getText().toString().trim();
        if(!TextUtils.isEmpty(username)){

            String UserId = mAuth.getCurrentUser().getUid();
            String deviceToken = databaseReference.push().getKey();
            String image = "default";
            String online = "false";
            String thumbleImage = "default";

            User user = new User(UserId, deviceToken,username,firstAttemp,image,online, thumbleImage);
            databaseReference.child(UserId).setValue(user);

            //create shared preference to username
            sharedPreferences.edit().putString("username",username).apply();
            //Start new Activity

            Intent intent = new Intent(RegisterUser.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();


        }else{
            //return false
           // Toast.makeText(this,"Tafadhali andika jina lako kamili",Toast.LENGTH_LONG).show();
            Alerter.create(RegisterUser.this)
                    .setTitle("Fail")
                    .setIcon(R.mipmap.bible_icon)
                    .setBackgroundColorRes(R.color.colorPrimary)
                    .setText("Tafadhali andika Majina yako kamili")
                    .setDuration(10000)
                    .enableSwipeToDismiss()
                    .show();
        }


    }

}
