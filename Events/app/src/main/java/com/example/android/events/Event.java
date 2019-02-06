package com.example.android.events;

public class Event {
    private int event_id;
    private String event_title;
    private String event_date;
    private String event_time;
    private String event_duration;
    private String event_venue;
    private String event_organizer;
    private String event_contact;
    private String event_type;
    private String event_description;
    private String event_photo;

    public Event(int event_id, String event_title, String event_date, String event_time, String event_duration, String event_venue, String event_organizer, String event_contact, String event_type, String event_description, String event_photo) {
        this.event_id = event_id;
        this.event_title = event_title;
        this.event_date = event_date;
        this.event_time = event_time;
        this.event_duration = event_duration;
        this.event_venue = event_venue;
        this.event_organizer = event_organizer;
        this.event_contact = event_contact;
        this.event_type = event_type;
        this.event_description = event_description;
        this.event_photo = event_photo;
    }

    public int getEvent_id() {
        return event_id;
    }

    public String getEvent_title() {
        return event_title;
    }

    public String getEvent_date() {
        return event_date;
    }

    public String getEvent_duration() {
        return event_duration;
    }

    public String getEvent_venue() {
        return event_venue;
    }

    public String getEvent_organizer() {
        return event_organizer;
    }

    public String getEvent_contact() {
        return event_contact;
    }

    public String getEvent_type() {
        return event_type;
    }

    public String getEvent_description() {
        return event_description;
    }

    public String getEvent_photo() {
        return event_photo;
    }

    public String getEvent_time() {
        return event_time;
    }
}
