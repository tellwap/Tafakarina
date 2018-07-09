package com.blogspot.tellwap.tafakarinayesu;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by chami epimark on 2017-12-27.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    public static final String EVENT = "event";
    public static final String ID = "id";
    public static final String ZABURI ="zaburi" ;

    private List<ListItem> ListItems;
    private Context context;
    public static final String DATA_ID = "dataId";
    public static final String DAY = "day";


    public MyAdapter(List<ListItem> listItems, Context context) {
        ListItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final ListItem listItem = ListItems.get(position);
        holder.textViewHead.setText(listItem.getDay());
        holder.textViewDesc.setText(listItem.getEvent());
        holder.textViewZaburi.setText(listItem.getZaburi());
        holder.cvProfile.setText(listItem.getDay().substring(1, 2).toUpperCase());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do thing when the recyclerview list clicked
               // Toast.makeText(context,"you click "+listItem.getDay(),Toast.LENGTH_LONG).show();
                //context.startActivity(new Intent(context,MainActivity2.class));

                Intent intent = new Intent(view.getContext(), ActivityDisplayMasomo.class);
                intent.putExtra(ID,listItem.getId());
                intent.putExtra(DAY,listItem.getDay());
                intent.putExtra(EVENT, listItem.getEvent());
                intent.putExtra(ZABURI,listItem.getZaburi());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return ListItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewHead ;
        public TextView textViewDesc;
        public TextView textViewZaburi;
      public   LinearLayout linearLayout;
      public TextView cvProfile;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewHead = (TextView)itemView.findViewById(R.id.day);
            textViewDesc = (TextView)itemView.findViewById(R.id.event);
            textViewZaburi = (TextView)itemView.findViewById(R.id.zaburi);
            linearLayout  = (LinearLayout)itemView.findViewById(R.id.linearLayoutId);
            cvProfile = (TextView) itemView.findViewById(R.id.cv_profile);
        }
    }
}

