package com.blogspot.tellwap.tafakarinayesu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by chami on 3/2/18.
 */

public class TabJadili extends Fragment {

    private RecyclerView messageList;
    private RecyclerView.Adapter adapter;
    private List<MessageModel> messageListItem;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReference1;
    private FirebaseAuth mRef;

    private FloatingActionButton fbContact;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_jadili, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("CONTACT_INFORMATION",MODE_PRIVATE);
//        String data = sharedPreferences.getString("phone_information", null);
//
//        if (data!=null){
//            try {
//                JSONArray jsonArray=new JSONArray(data);
//
//                for (int i=0;i<jsonArray.length();i++) {
//                   // Log.e("PHONE_NAME", jsonArray.getJSONObject(i).getString("0754645593"));
//                    Log.e("ERROR", "NO INFORMATION");
//                }
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//                Log.e("ERROR", "NO INFORMATION");
//            }
//        }else{
//
//            Log.e("ERROR", "NO INFORMATION");
//        }

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.keepSynced(true);

        mRef = FirebaseAuth.getInstance();

        messageList = (RecyclerView) getActivity().findViewById(R.id.recyclerViewMessageList);
        messageList.setLayoutManager(new LinearLayoutManager(getContext()));
        messageList.setHasFixedSize(true);

        messageListItem = new ArrayList<>();

        fbContact = (FloatingActionButton) getActivity().findViewById(R.id.fabContact);

       fbContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),DisplayContact.class);
                startActivity(intent);
            }
        });

        loadMessageContent();
    }

    public void loadMessageContent(){


        final String mCurentUserId = mRef.getCurrentUser().getUid();

        databaseReference.child("chat").child(mCurentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                messageListItem.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                    //get children first
                   // final String childrenId ="3ol8Bb8F8sPJVEsYNM3cldwuWoz1"; //dataSnapshot.getChildren().toString();
                    //int childrenId = (int) dataSnapshot1.getChildrenCount();
                    final String childrenId = dataSnapshot1.getKey().toString();

                    //query to get the other content
                     databaseReference1 = FirebaseDatabase.getInstance().getReference().child("chat");
                    databaseReference1.child(mCurentUserId).child(childrenId).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            String chatMessage = dataSnapshot.child("nmessage").getValue().toString();
                            String lastSeen = dataSnapshot.child("seen").getValue().toString();
                            String timestamp = dataSnapshot.child("timestamp").getValue().toString();

                            Log.e("DATA",chatMessage);

                            MessageModel messageModel = new MessageModel(
                                    chatMessage,
                                    lastSeen,
                                    "text",
                                    timestamp,
                                    childrenId
                            );
                            messageListItem.add(messageModel);
                            adapter = new MessageDisplayAdapter(getContext(),messageListItem);
                            messageList.setAdapter(adapter);

                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }

                    });


                    }



                }




     //       }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
