package com.blogspot.tellwap.tafakarinayesu;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class DisplayUjumbe extends AppCompatActivity {
    private TextView textViewUSer;
    private TextView textViewCurrentTime;
    private TextView textViewMessageDescription;
    private DatabaseReference databaseReference;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_ujumbe);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        textViewUSer = (TextView) findViewById(R.id.textViewUser);
        textViewCurrentTime = (TextView) findViewById(R.id.textViewTime);
        textViewMessageDescription = (TextView) findViewById(R.id.textViewMessageContent);

        //get value from previosly activities
        Intent intent = getIntent();
        String messageId = intent.getStringExtra(UjumeMyAdapter.MESSAGE_ID);
        String userSender = intent.getStringExtra("mUjumbeSenderName");
        databaseReference = FirebaseDatabase.getInstance().getReference("message").child(messageId);
        textViewUSer.setText(messageId);

       displayData(userSender);
    }

    public void displayData(final String userSender){

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Message message = dataSnapshot.getValue(Message.class);

                if(message.getTime() != null) {

                    textViewUSer.setText(userSender);
                    textViewCurrentTime.setText(message.getTime());
                    textViewMessageDescription.setText(message.getMessageContent());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"error in database",Toast.LENGTH_LONG).show();
            }
        });

    }


}
