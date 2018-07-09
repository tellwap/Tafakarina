package com.blogspot.tellwap.tafakarinayesu;

/**
 * Created by chami on 3/22/18.
 */

public class ChatModel {

    public ChatModel(){}
    public String nmessage, seen, timestamp;

    public ChatModel(String nmessage, String seen, String timestamp) {
        this.nmessage = nmessage;
        this.seen = seen;
        this.timestamp = timestamp;
    }

    public String getNmessage() {
        return nmessage;
    }

    public String getSeen() {
        return seen;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
