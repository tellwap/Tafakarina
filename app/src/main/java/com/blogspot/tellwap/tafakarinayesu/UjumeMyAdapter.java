package com.blogspot.tellwap.tafakarinayesu;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by chami on 3/16/18.
 */

public class UjumeMyAdapter extends RecyclerView.Adapter<UjumeMyAdapter.ViewHolder> {

    private List<ListItemUjumbe> listItems;
    private Context context;
    public static final String MESSAGE_ID = "messageId";

    public UjumeMyAdapter(List<ListItemUjumbe> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_jumbe,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

       final ListItemUjumbe listItemUjumbe = listItems.get(position);

       //check the one who send the ujumbe
        final String mUsumbeSenderId = listItemUjumbe.getMessageUser();

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("user").child(mUsumbeSenderId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String mUjumbeSenderName = dataSnapshot.child("username").getValue().toString();

                holder.textViewUser.setText(mUjumbeSenderName);
                holder.textViewMessageContent.setText(listItemUjumbe.getMessageContent());

                holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(view.getContext(),DisplayUjumbe.class);
                        intent.putExtra(MESSAGE_ID,listItemUjumbe.getMessageId());
                        intent.putExtra("mUjumbeSenderName", mUjumbeSenderName);
                        context.startActivity(intent);

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




//        holder.cvProfile.setText(listItemUjumbe.getMessageUser().substring(0, 1).toUpperCase());



        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {

                //check if the status is created by the user need to delete
                FirebaseAuth mRef = FirebaseAuth.getInstance();
                String mCurrentUserId = mRef.getCurrentUser().getUid();
                String mCurrentUserMessageId = listItemUjumbe.getMessageUser();

                if (mCurrentUserId.equals(mCurrentUserMessageId)){

                    //  holder.linearLayout.setBackgroundColor(Color.GRAY);
                    CharSequence charOption[] = new CharSequence[]{"Futa Ujumbe", "Ghairi"};
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext());
                    alertDialog.setTitle("Ujumbe wako");
                    alertDialog.setItems(charOption, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            if (i == 0){

                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                                databaseReference.child("message").child(listItemUjumbe.getMessageId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isComplete()){
                                            Toast.makeText(view.getContext(),"Ujumbe wako umefutwa ", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }



                        }
                    });
                    alertDialog.show();
                }else{

                    Toast.makeText(view.getContext(), "Samahani huwezi kufanya chochote kuhusu ujumbe huu", Toast.LENGTH_LONG).show();

                }


                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewUser;
        public TextView textViewMessageContent;
        public LinearLayout linearLayout;
        public TextView cvProfile;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewUser = (TextView)itemView.findViewById(R.id.messageUser);
            textViewMessageContent = (TextView) itemView.findViewById(R.id.messageContent);
            linearLayout  = (LinearLayout)itemView.findViewById(R.id.linearLayoutId);
            cvProfile = (TextView) itemView.findViewById(R.id.cv_profile1);
        }
    }
}
