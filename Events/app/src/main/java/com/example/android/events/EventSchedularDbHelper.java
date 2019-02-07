package com.example.android.events;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class EventSchedularDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "EventScheldular";
    private static final int DATABASE_VERSION = 2;
    private static final String TAG = "EventSchedularDbHelper";
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE event_reminder (event_id INTEGER,event_title TEXT(50), event_date TEXT(10),event_time TEXT(10), event_duration TEXT(10), event_venue TEXT(50),event_organizer TEXT(30), event_contact TEXT(15),event_type TYPR(5), event_description TEXT(255),event_photo TEXT(100))";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS event_reminder";

    public EventSchedularDbHelper(Context context) {
        //have to pass context, database name, cursor factory and database version
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Invoked when the database is created, this is where we can create tables and add columns to them, create views or triggers
     * The date follows ISO 8691 format YYYY-MM-DD
     * The time follows ISO 8691 format
     *
     * @param db the name of the database where we want to create the table
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(SQL_CREATE_ENTRIES);
        } catch (Exception e) {
            Log.e(TAG, "onCreate: Exception Caught");
            e.printStackTrace();
        }
        Log.d(TAG, "onCreate: Database: " + db + " created");
    }

    /**
     * Invoked when we make a modification to the database such as altering, dropping , creating new tables.
     *
     * @param db         the name of the database where we want to make changes
     * @param oldVersion the previous version number
     * @param newVersion the new version number
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        Log.d(TAG, "onUpgrade: Deleted database");
        onCreate(db);
        Log.d(TAG, "onUpgrade:\nDatabase name: " + db + "\nOld version: " + oldVersion + "\nNew version: " + newVersion);
    }
}
