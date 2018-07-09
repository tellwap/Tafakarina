package com.blogspot.tellwap.tafakarinayesu;

import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DisplayContact extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListDisplayContact> listItems;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewContact);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        listItems = new ArrayList<>();
        fp_get_Android_Contacts();

    }


    //=============================< Functions: Android.Contacts >=============================
    public class Android_Contact {
        //----------------< fritzbox_Contacts() >----------------
        public String android_contact_Name = "";
        public String android_contact_TelefonNr = "";
        public int android_contact_ID=0;
//----------------</ fritzbox_Contacts() >----------------
    }

    public void fp_get_Android_Contacts() {


//--< get all Contacts >--
        Cursor cursor_Android_Contacts = null;
        ContentResolver contentResolver = getContentResolver();
        try {
            cursor_Android_Contacts = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        } catch (Exception ex) {
            Log.e("Error on contact", ex.getMessage());
        }
//--</ get all Contacts >--

//----< Check: hasContacts >----
        if (cursor_Android_Contacts.getCount() > 0) {
//----< has Contacts >----
//----< @Loop: all Contacts >---

            while (cursor_Android_Contacts.moveToNext()) {
//< init >
                final Android_Contact android_contact = new Android_Contact();
                String contact_id = cursor_Android_Contacts.getString(cursor_Android_Contacts.getColumnIndex(ContactsContract.Contacts._ID));
                String contact_name = cursor_Android_Contacts.getString(cursor_Android_Contacts.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//</ init >

//----< set >----
                android_contact.android_contact_Name = contact_name;


//----< get phone number >----
                int hasPhoneNumber = Integer.parseInt(cursor_Android_Contacts.getString(cursor_Android_Contacts.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {

                    Cursor phoneCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                            , null
                            , ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?"
                            , new String[]{contact_id}
                            , null);

                    while (phoneCursor.moveToNext()) {
                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//< set >
                        android_contact.android_contact_TelefonNr = phoneNumber;

//</ set >
                    }

                    phoneCursor.close();
                }

//----</ set >----
//----</ get phone number >----

// Add the contact to the ArrayList
//----</ @Loop: all Contacts >----


                //check the contact friend use the app or otherwise
                databaseReference  = FirebaseDatabase.getInstance().getReference("user");
               // databaseReference.keepSynced(true);

                databaseReference.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        JSONArray jsonArray = new JSONArray();
                        SharedPreferences sharedPreferences = getSharedPreferences("CONTACT_INFORMATION", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        for (DataSnapshot userDataSnapshot : dataSnapshot.getChildren()){

                        User user = userDataSnapshot.getValue(User.class);

                        String userPhoneNumber = user.getUserPhonenumber();

                        if (userPhoneNumber.equals(android_contact.android_contact_TelefonNr)){

                            //get Contact Id then save it into json
                            try {
                                jsonArray.put(new JSONObject().put(android_contact.android_contact_TelefonNr, android_contact.android_contact_Name));
                                Log.e("CONTACT_INFORMATION",jsonArray.toString());
                                //Assign to the shared preference
                                editor.putString("phone_information", jsonArray.toString());
                                editor.apply();
                                editor.commit();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            String chatUserId = user.getUserId();
                            ListDisplayContact listItem = new ListDisplayContact(
                                    chatUserId,
                                    android_contact.android_contact_Name,
                                    android_contact.android_contact_TelefonNr
                            );
                            listItems.add(listItem);


                        }

                            }


                        adapter = new ContactAdapter(getApplication(), listItems);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();


                    }





                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
//< show results >
                // adapter = new MyAdapter( getApplication(), arrayList_Android_Contacts);
//            Adapter_for_Android_Contacts adapter = new Adapter_for_Android_Contacts(this, arrayList_Android_Contacts);
//            listView_Android_Contacts.setAdapter(adapter);

            }


//</ show results >


        }
//----</ Check: hasContacts >----

// ----------------</ fp_get_Android_Contacts() >----------------
    }
}
