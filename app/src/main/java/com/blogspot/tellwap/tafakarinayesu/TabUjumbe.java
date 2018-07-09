package com.blogspot.tellwap.tafakarinayesu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chami on 3/2/18.
 */

public class TabUjumbe extends Fragment {
    private TextView username;
    private LinearLayout linearLayout;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItemUjumbe> listItems;
    private SharedPreferences sharedPreferences;
    private FirebaseAuth mRef;

    DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_ujumbe, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView)getActivity().findViewById(R.id.recyclerViewUjumbe);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        listItems = new ArrayList<>();
        username = (TextView) getActivity().findViewById(R.id.username);
        linearLayout = (LinearLayout) getActivity().findViewById(R.id.linearLayoutId);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        mRef = FirebaseAuth.getInstance();

        sharedPreferences = getActivity().getSharedPreferences("Tafakari", Context.MODE_PRIVATE);
        String userName  = sharedPreferences.getString("username",null);

        username.setText(userName);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), Ujumbe.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

        databaseReference.child("message").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listItems.clear();
                for (DataSnapshot dataSnapshotMessage : dataSnapshot.getChildren()){

                    Message message = dataSnapshotMessage.getValue(Message.class);


                    ListItemUjumbe listItemUjumbe = new ListItemUjumbe(
                            message.getMessageId(),
                            message.getTime(),
                            message.getUserId()
                    );

                    listItems.add(listItemUjumbe);
                }

                adapter = new UjumeMyAdapter(listItems,getContext());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(getContext(),"error occured",Toast.LENGTH_LONG).show();
            }
        });

        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference().child("user").child(mRef.getCurrentUser().getUid());

        dbReference.child("online").setValue("true");
    }



}
