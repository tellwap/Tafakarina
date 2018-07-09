package com.blogspot.tellwap.tafakarinayesu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by chami o 3/2/18.
 */

public class TabMasomo extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItem> listItems;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    ProgressDialog progressDialog;
    DatabaseHelper myDb;

    //TextView
    private TextView day;
    private TextView event;
    private TextView palsm;

    //initialize linearLayout
    private LinearLayout linearLayout1;

    //Constants
    public static final String EVENT = "event";
    public static final String ID = "id";
    public static final String ZABURI ="zaburi" ;
    public static final String DATA_ID = "dataId";
    public static final String DAY = "day";


    @Nullable
    @Override


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_masomo, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Tafakari", MODE_PRIVATE);
        String username = sharedPreferences.getString("username",null);
        //now check the sharedpreferences
        if (username == null){
            startActivity(new Intent(getContext(), RegisterUser.class));
        }

        //cast the TextView
       // day = (TextView) getActivity().findViewById(R.id.day1);
        //event = (TextView) getActivity().findViewById(R.id.event1);
        //palsm = (TextView) getActivity().findViewById(R.id.zaburi1);

        //cast linearlayout
       // linearLayout1 = (LinearLayout) getActivity().findViewById(R.id.linearLayout11);

        progressDialog = new ProgressDialog(getContext());
        myDb = new DatabaseHelper(getContext());

        recyclerView = (RecyclerView)getActivity().findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        listItems = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("TafakariTitle");
        databaseReference2 = database.getReference();
        databaseReference.keepSynced(true);
        //addData();
       // displayData();

    }

    private void addData(){
        String day = "Jumatatu, Machi 26, 2018.";
        String event = "Juma kuu";
        String palsm = "Zab. 27:1-3, 13-14 (K) 1";

        //somo la kwanza-----------------------------
        String somo1Mstari = "Isa. 42:1-7";
        String somo1 = "Tazama mtumishi wangu nimtegemezaye; mteule wangu, ambaye nafsi yangu imependezwa naye; nimetia roho yangu juu yake; naye atawatolea mataifa hukumu.\n" +
                "Hatalia, wala hatapaza sauti yake, wala kuifanya isikiwe katika njia kuu. Mwanzi uliopondeka hatauvunja, wala utambi utokao moshi hatauzima; atatokeza hukumu kwa kweli.\n" +
                "Hatazimia, wala hatakata tamaa, hata atakapoweka hukumu duniani; na visiwa vitaingojea sheria yake.\n" +
                "Mungu Bwana anena, yeye aliyeziumba mbingu, na kuzitanda; yeye aliyeitandaza nchi na mazao yake; yeye awapaye pumzi watu walio juu yake; yeye awapaye roho wao waendao ndani yake.\n" +
                "Mimi, Bwana nimekuita katika haki, nami nitakushika mkono, na kukulinda, na kukutoa uwe agano la watu, na nuru ya mataifa; kuyafunua macho ya vipofu, kuwatoa gerezani waliofungwa, kuwatoa wale walioketi gizani katika nyumba ya kufungwa.\n" +
                "\n" +
                "Neno la Bwana… Tumshukuru Mungu.";
        //-----------------------------------------

        //somo la pili---------------------------
       String somo2Mstari = "";
        String somo2 = "";
        //-------------------------------------

        //------wimbo wa mwanzo----------------

        String wimboWaMwanzo = "";
        String wimboWaMwanzoMstari = "";

        //--------------------------------------------


        // wimbo wa katikati-----------------------------
        String wimboWaKatikatiMstari = "Zab. 27:1-3, 13-14 (K) 1";
        String wimboWaKatikati = "(K) Bwana ni nuru yangu na wokovu wangu.\n" +
                "\n" +
                "Bwana ni nuru yangu na wokovu wangu,\n" +
                "Nimwogope nani?\n" +
                "Bwana ni ngome ya uzima wangu,\n" +
                "Nimhofu nani? (K)\n" +
                "\n" +
                "Watenda mabaya waliponikaribia,\n" +
                "Wanile nyama yangu,\n" +
                "Watesi wangu na adui zangu,\n" +
                "Walijikwaa wakaanguka. (K)\n" +
                "\n" +
                "Jeshi lijapojipanga kupigana nami,\n" +
                "Moyo wangu hautaogopa.\n" +
                "Vita vijaponitokea,\n" +
                "Hata japo nitatumaini. (K)\n" +
                "\n" +
                "Naamini ya kuwa nitauona wema wa Bwana\n" +
                "Katika nchi ya walio hai.\n" +
                "Umngoje Bwana, uwe hodari,\n" +
                "Upige moyo konde, naam, umngoje Bwana. (K)";
        //-------------------------------------------------------------------------

        //shangilio
        String shangilioMstari = "Eze. 18:31";
        String shangilio = "Tupilieni mbali nanyi makossa yenu yote mliyoyakosa, asema Bwana, jifanyieni moyo mpya na roho mpya.";
        //-----------------------------------------------------------

       //------------------somo la injili--------------
        String somoLaInjiliMstari = "Yn. 12:1-11";
        String somoLaInjili = "\n" +
                "Siku sita kabla ya Pasaka, Yesu alifika Bethania, alipokuwapo Lazaro, yeye ambaye Yesu alimfufua katika wafu. Basi wakamwandalia karamu huko; naye Martha akatumika; na Lazaro alikuwa mmojawapo wa wale walioketi chakulani pamoja naye. \n" +
                "Basi Mariamu akatwaa ratli ya marhamu ya nardo safi yenye thamani nyingi, akampaka Yesu miguu, akamfuta miguu kwa nywele zake. Nayo nyumba pia ikajaa harufu ya marhamu.\n" +
                "Basi Yuda Iskariote, mmojawapo wa wanafunzi wake, ambaye ndiye atakaye kumsaliti, akasema, Mbona marhamu hii haikuuzwa kwa dinari mia tatu, wakapewa maskini? Naye aliyasema hayo, si kwa kuwahurumia maskini; bali kwa kuwa ni mwivi, naye ndiye aliyeshika mfuko, akavichukua vilivyotiwa humo.\n" +
                "Basi yesu alisema, Mwache aiweke kwa siku za maziko yangu. Kwa maana maskini mnao sikuzote pamoja nanyi; bali mimi hamnami sikuzote.\n" +
                "Basi watu wengi katika Wayahudi walipata kujua ya kuwa yeye yuko huko; nao wakaja, si kwa ajili yake Yesu tu, ila wamwone na Lazaro, ambaye Yesu alimfufua katika wafu. Nao wakuu wa makuhani wakafanya shauri la kumwua Lazaro naye; maana kwa ajili yake wengi katika Wayahudi walijitenga, wakamwamini Yesu.\n" +
                "\n" +
                "Neno la Bwana… Sifa kwako Ee Kristu.";
        //---------------------------------------------------

        String id = databaseReference.push().getKey();
        AddData addData = new AddData(id,day, event, palsm,somo1,somo1Mstari, somo2, somo2Mstari, wimboWaKatikati,
                wimboWaKatikatiMstari, shangilio, shangilioMstari, somoLaInjili, somoLaInjiliMstari, wimboWaMwanzo, wimboWaMwanzoMstari);
        databaseReference.child(id).setValue(addData);




    }

    @Override
    public void onStart() {
        super.onStart();

//        progressDialog.show();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                progressDialog.dismiss();

                //Cursor res = myDb.getAllData();
//                String id2 = res.getString(0);
                listItems.clear();

                for (DataSnapshot tafakariSnapshot : dataSnapshot.getChildren()) {

                    final AddData addData = tafakariSnapshot.getValue(AddData.class);
                    ListItem listItem = new ListItem(
                            addData.getDataId(),
                            addData.getDay(),
                            addData.getEvent(),
                            addData.getPalsm()
                    );

                    //day.setText(addData.getDay());
                   // event.setText(addData.getEvent());
                   // palsm.setText(addData.getPalsm());
//                    linearLayout1.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                          //  Toast.makeText(getContext(), "you clicked "+addData.getDay(),Toast.LENGTH_LONG).show();
//
//                            Intent intent = new Intent(view.getContext(), ActivityDisplayMasomo.class);
//                            intent.putExtra(ID,addData.getDataId());
//                            intent.putExtra(DAY, addData.getDay());
//                            intent.putExtra(EVENT, addData.getEvent());
//                            intent.putExtra(ZABURI,addData.getPalsm());
//                            startActivity(intent);
//                        }
//                    });

                    listItems.add(listItem);

                    }

                adapter = new MyAdapter(listItems,getContext());
                recyclerView.setAdapter(adapter);


                    }



            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Tafadhali washa data", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
