package com.blogspot.tellwap.tafakarinayesu;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by chami on 3/18/18.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    public Context context;
    public List<ListDisplayContact> listItems;
    public static final String PHONE_NUMBER = "phonenumber";
    public static final String CONTACT_NAME = "contactnumber";
    public static final String CONTACT_CHAT_ID = "chamiepimark";

    public ContactAdapter(Context context, List<ListDisplayContact> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_display_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final ListDisplayContact listDisplayContact = listItems.get(position);
        holder.contactName.setText(listDisplayContact.getContactName());
        holder.contactNumber.setText(listDisplayContact.getContactNumber());
        holder.cvProfile.setText(listDisplayContact.getContactName().substring(0, 1));
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send a message area
                Intent intent = new Intent(view.getContext(), ChartingActivity.class);

                intent.putExtra(PHONE_NUMBER, listDisplayContact.getContactNumber());
                intent.putExtra(CONTACT_NAME,listDisplayContact.getContactName());
                intent.putExtra(CONTACT_CHAT_ID, listDisplayContact.getChatUserId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView contactName;
        public TextView contactNumber;
        public TextView cvProfile;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            contactName = (TextView) itemView.findViewById(R.id.contactName);
            contactNumber = (TextView) itemView.findViewById(R.id.contactNumber);
            cvProfile = (TextView) itemView.findViewById(R.id.cv_profile2);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayoutContact);

        }
    }
}
