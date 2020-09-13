package com.example.myfirstandroidapp.Classes;

import java.io.Serializable;

public class Event implements Serializable {

    private int eventID = 0;
    private String eventName = "";
    private String eventLocation = "";
    private String eventDateTime  = "";


    public Event(int eventID , String eventName, String eventLocation, String eventDateTime ) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventDateTime = eventDateTime;
    }


    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventDateTime() {
        return eventDateTime;
    }

    public void setEventDateTime(String eventDateTime) {
        this.eventDateTime = eventDateTime;
    }
}

