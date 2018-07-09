package com.blogspot.tellwap.tafakarinayesu;

import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Ujumbe extends AppCompatActivity {

    private EditText editTextMessage;
    private FloatingActionButton buttonSendMessage;
    DatabaseReference databaseReference ;
    private FirebaseAuth mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ujumbe);

        editTextMessage = (EditText) findViewById(R.id.editTextMessageUjumbe);
        buttonSendMessage = (FloatingActionButton) findViewById(R.id.buttonTuma);
        databaseReference = FirebaseDatabase.getInstance().getReference("message");
        mRef = FirebaseAuth.getInstance();

        buttonSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMessage();
            }
        });


    }

    public void addMessage(){

        //validate the input
        String messageContent = editTextMessage.getText().toString().trim();

        if(!TextUtils.isEmpty(messageContent)){

          FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
          String mCurrentUserId = firebaseAuth.getCurrentUser().getUid();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
            String currentDateandTime = sdf.format(new Date());

            String id = databaseReference.push().getKey();
            Message message = new Message(currentDateandTime,id,messageContent,mCurrentUserId);
             databaseReference.child(id).setValue(message);

             Toast.makeText(this,"ujumbe wako umetumwa",Toast.LENGTH_LONG).show();
             editTextMessage.setText("");

        }
        else{
            //Show the error message
            Toast.makeText(this, "Tafadhali ingiza ujumbe wako kabla ya kutuma",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference().child("user").child(mRef.getCurrentUser().getUid());
        dbReference.child("online").setValue("true");

    }

    @Override
    public void onStop() {
        super.onStop();
        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference().child("user").child(mRef.getCurrentUser().getUid());
        long time= System.currentTimeMillis();
        String timeStamp = Long.toString(time);

        dbReference.child("online").setValue(timeStamp);
        // databaseReference.child("lastseen").setValue(ServerValue.TIMESTAMP);
    }
}
