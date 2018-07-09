package com.blogspot.tellwap.tafakarinayesu;

/**
 * Created by chami on 3/16/18.
 */

public class Message {
    String time;
    String messageId;
    String messageContent;
    String userId;

    public Message(){

    }

    public Message(String time, String messageId, String messageContent, String userId) {
        this.messageId = messageId;
        this.messageContent = messageContent;
        this.userId = userId;
        this.time = time;
    }
public String getTime(){
        return time;
}
    public String getMessageId() {
        return messageId;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public String getUserId() {
        return userId;
    }
}
