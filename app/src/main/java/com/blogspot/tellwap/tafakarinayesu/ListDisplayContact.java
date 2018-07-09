package com.blogspot.tellwap.tafakarinayesu;

/**
 * Created by chami on 3/18/18.
 */

public class ListDisplayContact {

    public String contactName;
    public String contactNumber;
    public String chatUserId;

    public ListDisplayContact(){

    }
    public ListDisplayContact(String chatUserId, String contactName, String contactNumber) {
        this.chatUserId = chatUserId;
        this.contactName = contactName;
        this.contactNumber = contactNumber;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }
    public String getChatUserId(){
        return chatUserId;
    }
}
