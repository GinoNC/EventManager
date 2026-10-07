package com.gino.eventmanager;

public class EventModel {
    String name;
    String date;
    String location;
    String organiser;
    String type;

    public EventModel(String name, String date, String location, String organiser, String type) {
        this.name = name;
        this.date = date;
        this.location = location;
        this.organiser = organiser;
        this.type = type;
    }

    public EventModel() {

    }
}
