package com.blogspot.tellwap.tafakarinayesu;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

/**
 * Created by chami on 3/18/18.
 */

public class SenderAdapter extends RecyclerView.Adapter<SenderAdapter.ViewHolder> {

    private FirebaseAuth mRef;
    @LayoutRes int lay;

    public SenderAdapter(List<MessageModel> listItems, Context context,int lay) {
        this.listItems = listItems;
        this.context = context;
        this.lay=lay;
    }

    public List<MessageModel> listItems;
    public Context context;
    public View view;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.e("*******ADA", String.valueOf(this.lay));
        view = LayoutInflater.from(parent.getContext()).inflate(this.lay,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

       mRef = FirebaseAuth.getInstance();
       // String current_user = "LpmK2cKGHDOtraePV6VjP4sjuLn2";
        String current_user = mRef.getCurrentUser().getUid();

        MessageModel listSenderItem = listItems.get(position);

       // holder.timeSended.setText(listSenderItem.getSenderTime());
        String user_from = listSenderItem.getFrom();

        if (user_from.equals(current_user)){
            holder.senderMessage.setBackgroundResource(R.drawable.sent_by_other);
            holder.senderMessage.setTextColor(Color.BLACK);

        }else{
            holder.senderMessage.setGravity(Gravity.END);

            holder.senderMessage.setBackgroundResource(R.drawable.message_chat_background);
            holder.senderMessage.setTextColor(Color.WHITE);

        }

        holder.senderMessage.setText(listSenderItem.getChatMessage());

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView senderMessage;
        public TextView timeSended;
        //public RelativeLayout parent;


        public ViewHolder(View itemView) {
            super(itemView);

            senderMessage = (TextView) itemView.findViewById(R.id.message_text_layout);
            //this.parent=(RelativeLayout) itemView;
            //timeSended = (TextView) itemView.findViewById(R.id.timeSended);

        }
    }
}
