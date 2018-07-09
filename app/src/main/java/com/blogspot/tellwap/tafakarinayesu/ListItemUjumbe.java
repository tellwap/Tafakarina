package com.blogspot.tellwap.tafakarinayesu;

/**
 * Created by chami on 3/16/18.
 */

public class ListItemUjumbe {

String messageId;
String messageContent;
String messageUser;
 public ListItemUjumbe(){

 }

    public ListItemUjumbe(String messageId, String messageContent, String messageUser) {
        this.messageId = messageId;
        this.messageContent = messageContent;
        this.messageUser = messageUser;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public String getMessageUser() {
        return messageUser;
    }
}
