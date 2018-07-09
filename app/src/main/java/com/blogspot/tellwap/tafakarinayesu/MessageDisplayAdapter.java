package com.blogspot.tellwap.tafakarinayesu;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by chami on 3/20/18.
 */

public class MessageDisplayAdapter extends RecyclerView.Adapter<MessageDisplayAdapter.MessageViewHolder> {

    public Context context;
    public List<MessageModel> messageListItems;
    public static final String CONTACT_CHAT_ID = "contact";
    public static final String CONTACT_NAME = "contact_name";
    public static final String PHONE_NUMBER = "contact_number";
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private String senderPhone;

    public MessageDisplayAdapter(Context context, List<MessageModel> messageListItems) {
        this.context = context;
        this.messageListItems = messageListItems;
    }

    @Override

    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_message_sended, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {

        final MessageModel  messageModel = messageListItems.get(position);
        firebaseAuth = FirebaseAuth.getInstance();

        holder.messageUser.setText(messageModel.getFrom());
        holder.messageContent.setText(messageModel.getChatMessage());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send a message area


                //first check the sender
                String senderId = messageModel.getFrom();
                //now check the sender phone number
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("user").child(senderId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        senderPhone = dataSnapshot.child("userPhonenumber").getValue().toString();
                        Log.e("USERPHONENE",senderPhone);


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                Intent intent = new Intent(view.getContext(), ChartingActivity.class);
                intent.putExtra(PHONE_NUMBER, senderPhone);
                intent.putExtra(CONTACT_NAME,senderPhone);
                intent.putExtra(CONTACT_CHAT_ID, messageModel.getFrom());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return messageListItems.size() ;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{

        public TextView messageUser;
        public TextView messageContent;
        public LinearLayout linearLayout;

        public MessageViewHolder(View itemView) {
            super(itemView);

            messageUser = (TextView) itemView.findViewById(R.id.messageFrom);
            messageContent = (TextView) itemView.findViewById(R.id.messageContent);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayoutmessage);
        }
    }
}
