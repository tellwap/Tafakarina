package com.blogspot.tellwap.tafakarinayesu;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChartingActivity extends AppCompatActivity {

   private RecyclerView recyclerView;
   private RecyclerView.Adapter adapter;
   private List<MessageModel> listItems;
   private TextView userName;
   private TextView lastSeen;
   private DatabaseReference databaseReference;
   private FirebaseAuth mRef;
   private EditText messageContent;
   private ImageButton sendMessage;
   public String mChatUserId;
   public Toolbar toolbar;
   private TextView toolbarChatName, toolbarSeen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charting);

        mRef = FirebaseAuth.getInstance();
       final String mCurentUserId = mRef.getCurrentUser().getUid();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewCharting);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        toolbar = (Toolbar) findViewById(R.id.toolbarChat);
        setSupportActionBar(toolbar);



        listItems = new ArrayList<>();

        Intent intent = getIntent();
        String contactName = intent.getStringExtra(ContactAdapter.CONTACT_NAME);
        String contactNumber = intent.getStringExtra(ContactAdapter.PHONE_NUMBER);
        mChatUserId = intent.getStringExtra(ContactAdapter.CONTACT_CHAT_ID);

        if (mChatUserId == null){
            mChatUserId = intent.getStringExtra(MessageDisplayAdapter.CONTACT_CHAT_ID);
            contactName = intent.getStringExtra(MessageDisplayAdapter.PHONE_NUMBER);
        }

        //set it into an Action Bar

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setTitle(contactName);
//        actionBar.setDisplayShowCustomEnabled(true);
      //  actionBar.setDisplayShowHomeEnabled(true);

     //   LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
     //   View action_bar_service = layoutInflater.inflate(R.layout.chat_action_bar, null);
       // actionBar.setCustomView(action_bar_service);

        toolbarChatName = (TextView) findViewById(R.id.toolbarChatName);
        toolbarSeen = (TextView) findViewById(R.id.toolbarSeen);
        messageContent = (EditText) findViewById(R.id.editTextMessage);
        sendMessage = (ImageButton) findViewById(R.id.buttonSendMessage);

        toolbarChatName.setText(contactName);

        databaseReference = FirebaseDatabase.getInstance().getReference();


        databaseReference.child("user").child(mChatUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String online = (String) dataSnapshot.child("online").getValue();



                if (online.equals("true")) {

                    toolbarSeen.setText("online");

                } else {
                   GetTimeAgo getTimeAgo = new GetTimeAgo();
                   long lastTime = Long.parseLong(online);
                    String lastSeenTime =getTimeAgo.getTimeAgo(lastTime,getApplication());
                    toolbarSeen.setText(lastSeenTime);
                }




    }

     @Override
     public void onCancelled(DatabaseError databaseError) {

     }
 });


        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

        loadMessage();


    }

    public void sendMessage(){

        final String mCurentUserId = mRef.getCurrentUser().getUid();

        final String messageConten = messageContent.getText().toString().trim();

        String current_user_ref = "chatMessage/" + mCurentUserId  +"/" +mChatUserId;
        String chat_user_ref = "chatMessage/" +mChatUserId + "/" + mCurentUserId;
        DatabaseReference message_push = databaseReference.child("chatMessage").child(mCurentUserId).child(mChatUserId).push();

        String pust_id = message_push.getKey();

        if (!TextUtils.isEmpty(messageConten)){

            Map messageMap = new HashMap();
            messageMap.put("chatMessage", messageConten);
            messageMap.put("seen", "false");
            messageMap.put("type", "text");
            messageMap.put("time", "time");
            messageMap.put("from", mCurentUserId);

            Map messageUserMap = new HashMap();
            messageUserMap.put(current_user_ref +"/"+ pust_id, messageMap);
            messageUserMap.put(chat_user_ref +"/"+ pust_id, messageMap);

            messageContent.setText("");


            databaseReference.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                    Log.d("CHAT_ERROR", "database Error");

                }
            });



            databaseReference.child("chat").child(mChatUserId).child(messageConten).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (!dataSnapshot.hasChild(mCurentUserId)){

                        Toast.makeText(getApplicationContext(), mCurentUserId,Toast.LENGTH_LONG).show();

                        Map chatMap = new HashMap();
                        chatMap.put("nmessage", messageConten);
                        chatMap.put("seen", "false");
                        chatMap.put("timestamp", "time");

                        Map chatUserMap = new HashMap();
                        chatUserMap.put("chat/" + mCurentUserId + "/" +mChatUserId ,chatMap);
                        chatUserMap.put("chat/"+ mChatUserId + "/" +mCurentUserId, chatMap);

                        databaseReference.updateChildren(chatUserMap, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                                if (databaseError != null){

                                    Log.d("DATABASE ERROR", databaseError.getMessage().toString());
                                }

                            }
                        });
                    }
                    Toast.makeText(getApplicationContext(), "null",Toast.LENGTH_LONG).show();

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {


                    Log.d("ONLINE ERROR", databaseError.getMessage().toString());
                }
            });

        }else{
            Toast.makeText(this,"empty",Toast.LENGTH_LONG).show();
        }
    }

    public void loadMessage(){

        final String mCurentUserId = mRef.getCurrentUser().getUid();
        databaseReference.child("chatMessage").child(mCurentUserId).child(mChatUserId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                MessageModel messageModel = dataSnapshot.getValue(MessageModel.class);

                listItems.add(messageModel);

                int lay;
                if (mCurentUserId.equals(messageModel.getFrom())){
                    lay=R.layout.list_sender_activity;
                    Log.e("*******CHAT1111111", String.valueOf(lay));
                    adapter = new SenderAdapter(listItems, getApplicationContext(),lay);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }else {
                    lay=R.layout.receiver_layout;
                Log.e("*******CHAT2222222", String.valueOf(lay));
                    adapter = new SenderAdapter(listItems, getApplicationContext(),lay);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
