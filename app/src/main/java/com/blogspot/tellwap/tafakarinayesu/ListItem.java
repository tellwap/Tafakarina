package com.blogspot.tellwap.tafakarinayesu;

/**
 * Created by chami epimark on 2017-12-27.
 */

public class ListItem {

    public  String id;
    public  String day;
    public  String event;
    public  String zaburi;

public ListItem(){

    }

    public ListItem(String id,String day, String event, String zaburi) {
        this.day = day;
        this.event = event;
        this.zaburi = zaburi;
        this.id = id;
    }

    public String getDay() {
        return day;
    }
    public String getId(){
    return id;
    }

    public String getEvent() {
        return event;
    }

    public String getZaburi() {
        return zaburi;
    }
}
