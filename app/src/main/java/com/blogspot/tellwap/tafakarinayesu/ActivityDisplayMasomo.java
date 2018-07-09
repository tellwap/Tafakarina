package com.blogspot.tellwap.tafakarinayesu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ActivityDisplayMasomo extends AppCompatActivity {

    private TextView textViewDay,textViewSomo2,textViewSomo2Mstari;
    private TextView textViewEvent, textViewShangilio, textViewShangiloMstari,textViewInjili,textViewInjiliMstari;
    private TextView textViewPalsm,textViewWimboWaKatikatiMstari;
    private TextView textViewWimboWaMwanzoMstari,textViewWimboWaKatikati;
    private TextView textViewWimboWaMwanzo, textViewSomo1, textViewSomo1Line;
    private TextView textViewWimboWaMwanzoTitle,textViewSomo1Title,textViewSomo2Tittle,textViewWimboWaKatikatiTitle,textViewShangilioTitle,
    textViewInjiliTitle;
    private Toolbar toolbar;
    DatabaseReference databaseReference ;
    AlertDialog alertDialog;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_masomo);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
     //   getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        textViewDay = (TextView)findViewById(R.id.textViewDay);
        textViewEvent = (TextView)findViewById(R.id.textViewEvent);
        textViewPalsm = (TextView)findViewById(R.id.textViewPalsm);
        textViewWimboWaMwanzoMstari = (TextView)findViewById(R.id.wimboWaMwanzoMstari);
        textViewWimboWaMwanzo = (TextView)findViewById(R.id.textViewWimboWaMwanzo);
        textViewSomo1 = (TextView)findViewById(R.id.textViewSomo1);
        textViewSomo1Line = (TextView)findViewById(R.id.textViewSomo1Mstari);
        textViewWimboWaKatikati = (TextView)findViewById(R.id.textViewWimboWaKatikati);
        textViewWimboWaKatikatiMstari = (TextView)findViewById(R.id.textViewWimboWaKatikatiMstari);
        textViewSomo2 = (TextView)findViewById(R.id.textViewSomo2);
        textViewSomo2Mstari= (TextView)findViewById(R.id.textViewSomo2Mstari);
        textViewInjili = (TextView)findViewById(R.id.textViewInjili);
        textViewInjiliMstari = (TextView)findViewById(R.id.textViewInjiliMstari);
        textViewShangilio = (TextView)findViewById(R.id.textViewShangilio);
        textViewWimboWaMwanzoTitle = (TextView)findViewById(R.id.textViewWimboWaMwanzoTitle);
        textViewSomo1Title = (TextView)findViewById(R.id.textViewSomo1Tittle);
        textViewSomo2Tittle = (TextView) findViewById(R.id.textViewSomo2Tittle);
        textViewWimboWaKatikatiTitle = (TextView) findViewById(R.id.textViewWimboWaKatikatiTittle);
        textViewShangilioTitle = (TextView) findViewById(R.id.textViewShangilioTittle);
        textViewInjiliTitle = (TextView) findViewById(R.id.textViewInjiliTittle);





        Intent intent = getIntent();
        String id = intent.getStringExtra(MyAdapter.ID);
        String event = intent.getStringExtra(MyAdapter.EVENT);
        databaseReference = FirebaseDatabase.getInstance().getReference("TafakariTitle").child(id);
        alertDialog = new AlertDialog.Builder(getApplicationContext()).create();


       // textViewDay.setText(day);
        textViewEvent.setText(event);
        getRequiredData();
    }


public void getRequiredData(){

    ValueEventListener valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            AddData addData = dataSnapshot.getValue(AddData.class);
            //alertDialog.setTitle("somo la kwanza");
          //  alertDialog.setMessage(addData.getSomoLaKwanza());
          //  alertDialog.show();
          //  Log.d("somo la kwanza",addData.getSomoLaKwanza());

            //check first the day
            if(TextUtils.isEmpty(addData.getWimboWaMwanzo()) && TextUtils.isEmpty(addData.getSomoLaPili())){
                textViewWimboWaMwanzoMstari.setVisibility(View.INVISIBLE);
                textViewWimboWaMwanzo.setVisibility(View.INVISIBLE);
                textViewWimboWaMwanzoTitle.setVisibility(View.INVISIBLE);

                textViewSomo2.setText(addData.getSomoLaPili());
                textViewSomo2Mstari.setText(addData.getSomoLaPiliMstari());
                textViewSomo2Tittle.setVisibility(View.INVISIBLE);

            }else{
                textViewWimboWaMwanzoMstari.setText(addData.getWimboWaMwanzoMstari);
                textViewWimboWaMwanzo.setText(addData.getWimboWaMwanzo());
                textViewWimboWaMwanzoTitle.setText("WIMBO WA MWANZO");

                textViewSomo2.setText(addData.getSomoLaPili());
                textViewSomo2Mstari.setText(addData.getSomoLaPiliMstari());
                textViewSomo2Tittle.setText("SOMO 2");

            }

            textViewDay.setText(addData.getDay());
            textViewEvent.setText(addData.getEvent());
            textViewPalsm.setText(addData.getPalsm());



            textViewSomo1.setText(addData.getSomoLaKwanza());
            textViewSomo1Line.setText(addData.getSomoLaKwanzaMstari);
            textViewSomo1Title.setText("SOMO 1");

            textViewWimboWaKatikati.setText(addData.getWimboWaKatikati());
            textViewWimboWaKatikatiMstari.setText(addData.getWimboWaKatikatiMstari());
            textViewWimboWaKatikatiTitle.setText("WIMBO WA KATIKATI");



            textViewShangilio.setText(addData.getShangilio());
//            textViewShangiloMstari.setText(addData.getShangilioMstari());
            textViewShangilioTitle.setText("SHANGILIO");

            textViewInjili.setText(addData.getInjili());
            textViewInjiliMstari.setText(addData.getInjiliMstari());
            textViewInjiliTitle.setText("INJILI");


        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

            Toast.makeText(getApplicationContext(), databaseError.toString(), Toast.LENGTH_LONG).show();

        }
    });
}


}
