package com.blogspot.tellwap.tafakarinayesu;

/**
 * Created by chami on 3/17/18.
 */

public class User {

    public User(){

    }




    String userId;
    String deviceToken;
    String username;
    String userPhonenumber;
    String image;
    String online;
    String thumbleImage;

    public User(String userId, String deviceToken, String username, String userPhonenumber, String image, String online, String thumbleImage) {
        this.userId = userId;
        this.deviceToken = deviceToken;
        this.username = username;
        this.userPhonenumber = userPhonenumber;
        this.image = image;
        this.online = online;
        this.thumbleImage = thumbleImage;
    }

    public String getUserId() {
        return userId;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public String getUsername() {
        return username;
    }

    public String getUserPhonenumber() {
        return userPhonenumber;
    }

    public String getImage() {
        return image;
    }

    public String getOnline() {
        return online;
    }

    public String getThumbleImage() {
        return thumbleImage;
    }
}
