package com.blogspot.tellwap.tafakarinayesu;

/**
 * Created by chami on 3/18/18.
 */

public class MessageModel {


    public String chatMessage, seen , type, time, from;

    public MessageModel(){

    }
    public MessageModel(String chatMessage, String seen, String type, String time, String from) {
        this.chatMessage = chatMessage;
        this.seen = seen;
        this.type = type;
        this.time = time;
        this.from = from;
    }

    public String getChatMessage() {
        return chatMessage;
    }

    public String getSeen() {
        return seen;
    }

    public String getType() {
        return type;
    }

    public String getTime() {
        return time;
    }

    public String getFrom(){
        return from;
    }
}
