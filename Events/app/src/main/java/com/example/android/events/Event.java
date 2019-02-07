package com.example.android.events;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class Event {
    private static final String TAG = "Event";
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

    public String formattedDate() {
        float daysBetween=0f;
        try {
            String y = event_date.substring(0, 4);
            //Log.d(TAG, "formattedDate: year" + y);
            String m = event_date.substring(5, 7);
            //Log.d(TAG, "formattedDate: month" + m);
            String d = event_date.substring(8, 10);
            //Log.d(TAG, "formattedDate: day" + d);

            Calendar end = new GregorianCalendar(Integer.parseInt(y), Integer.parseInt(m), Integer.parseInt(d));

            Calendar now = new GregorianCalendar();
            String ny = String.valueOf(now.get(Calendar.YEAR));
            String nm = String.valueOf(now.get(Calendar.MONTH));
            String nd = String.valueOf(now.get(Calendar.DAY_OF_MONTH));

            SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
            String dateBeforeString = nd + " " + nm + " " + ny;
            String dateAfterString = d + " " + m + " " + y;


            Date dateBefore = myFormat.parse(dateBeforeString);
            Date dateAfter = myFormat.parse(dateAfterString);
            long difference = dateAfter.getTime() - dateBefore.getTime();
            daysBetween = (difference / (1000 * 60 * 60 * 24));
            /* You can also convert the milliseconds to days using this method
             * float daysBetween =
             *         TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS)
             */
            return String.valueOf(daysBetween);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  String.valueOf(daysBetween);
    }
}
