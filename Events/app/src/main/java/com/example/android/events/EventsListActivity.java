package com.example.android.events;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class EventsListActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ProgressDialog progressDialog;
    private static final String TAG = "EventsListActivity";
    SQLiteDatabase sqLiteDatabase;
    public static final String CHANNEL_ID = "0x00001";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_events_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (isConnectionAvailable()) {
            try {
                Log.d(TAG, "onCreate: Inside try");
                PullEvents pullNews = new PullEvents();
                pullNews.execute();

                progressDialog = new ProgressDialog(EventsListActivity.this);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Please Wait");
                progressDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "onCreate: " + e);
            }
        } else {
            Toast.makeText(this, "No network available", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isConnectionAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    private class PullEvents extends AsyncTask<Void, Void, Void> {
        ArrayList<Event> eventsArrayList = new ArrayList<>();
        EventAdapter eventsAdapter;
        String json;
        JSONObject jsonObject;
        JSONArray jsonArray;

        @Override
        protected Void doInBackground(Void... voids) {
            json = "";
            try {
                //list to store the ids of all events
                ArrayList<Integer> ids = new ArrayList<>();
                //opening the database connection
                openReadableDatabaseConnection();
                String selectIdQuery = "SELECT * FROM event_reminder";
                Log.d(TAG, "doInBackground: selectIdQuery: " + selectIdQuery);
                Cursor cursor = sqLiteDatabase.rawQuery(selectIdQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        ids.add(cursor.getInt(0));
                        Log.d(TAG, "doInBackground: saved id" + cursor.getInt(0));
                    } while (cursor.moveToNext());
                }
                cursor.close();

                Log.d(TAG, "doInBackground: Arrays Size: " + ids.size());
                for (Integer id : ids) {
                    Log.d(TAG, id + "");
                }

                String json = readUrl("http://192.168.1.80:81/counsel/eventrequest.php?event_request=[{\"orgkey\":\"" + getString(R.string.org_key) + "\"}]");
                jsonArray = new JSONArray(json);

                openWritableDatabaseConnection();
                if (jsonArray != null) {
                    Log.d(TAG, "doInBackground: Json not empty");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        int event_id = Integer.valueOf(String.valueOf(jsonObject.get("event_id")));
                        String event_title = String.valueOf(jsonObject.get("event_title"));
                        String event_date = String.valueOf(jsonObject.get("event_date"));
                        String event_time = String.valueOf(jsonObject.get("event_time"));
                        String event_duration = jsonObject.getString("event_duration");
                        String event_venue = jsonObject.getString("event_venue");
                        String event_organizer = jsonObject.getString("event_organizer");
                        String event_contact = jsonObject.getString("event_contact");
                        String event_type = jsonObject.getString("event_type");
                        String event_description = jsonObject.getString("event_description");
                        String event_photo = jsonObject.getString("event_photo");
                        Calendar cal  = new GregorianCalendar(Integer.parseInt(event_date.substring(0,4)),(Integer.parseInt(event_date.substring(5,7))-1),Integer.parseInt(event_date.substring(8,10)),Integer.parseInt(event_time.substring(0,2)),Integer.parseInt(event_time.substring(3,5)));
                        createAlarm(cal,event_description);

                        boolean unique = true;
                        for (int i1 = 0; i1 < ids.size(); i++) {
                            if (ids.get(i) == event_id) {
                                unique = false;
                                break;
                            }
                        }
                        if (unique) {
                            String insertQuery = "INSERT into event_reminder VALUES('" + event_id + "','" + event_title + "','" + event_date + "','" + event_time + "','" + event_duration + "','" + event_venue + "','" + event_organizer + "','" + event_contact + "','" + event_type + "','" + event_description + "','" + getString(R.string.org_img_path)+event_photo + "')";
                            Log.d(TAG, "doInBackground: insert query: " + insertQuery);
                            sqLiteDatabase.execSQL(insertQuery);

                        } else {
                            //old event
                            String updateQuery = "UPDATE event_reminder SET event_title = '" + event_title + "', event_date = '" + event_date + "', event_time = '" + event_time + "', event_duration = '" + event_duration + "', event_venue = '" + event_venue + "', event_organizer = '" + event_organizer + "', event_contact = '" + event_contact + "', event_type = '" + event_type + "', event_description = '" + event_description + "', event_photo = '" + getString(R.string.org_img_path)+event_photo + "'";
                            Log.d(TAG, "doInBackground: update query: " + updateQuery);
                            sqLiteDatabase.execSQL(updateQuery);
                        }
                        //Log.d(TAG, "doInBackground: " + event_title);
                        eventsArrayList.add(new Event(event_id, event_title, event_date, event_time, event_duration, event_venue, event_organizer, event_contact, event_type, event_description, event_photo));
                    }
                } else {
                    Toast.makeText(EventsListActivity.this, "No events to show", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "doInBackground: No events to show");
                }

            } catch (
                    Exception e) {
                e.printStackTrace();
                Log.e(TAG, "doInBackground: ", e);
            }
            return null;
        }

        private String readUrl(String urlString) throws Exception {
            BufferedReader reader = null;
            try {
                URL url = new URL(urlString);
                reader = new BufferedReader(new InputStreamReader(url.openStream()));
                StringBuffer buffer = new StringBuffer();
                int read;
                char[] chars = new char[1024];
                while ((read = reader.read(chars)) != -1)
                    buffer.append(chars, 0, read);
                return buffer.toString();
            } finally {
                if (reader != null)
                    reader.close();
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            eventsAdapter = new EventAdapter(getApplicationContext(), eventsArrayList);
            mRecyclerView.setAdapter(eventsAdapter);
        }

    }

    private void openReadableDatabaseConnection() {
        Log.d(TAG, "openReadableDatabaseConnection: ");
        EventSchedularDbHelper eventSchedularDbHelper = new EventSchedularDbHelper(this);
        sqLiteDatabase = eventSchedularDbHelper.getReadableDatabase();
    }

    private void closeDatabaseConnection() {
        Log.d(TAG, "closeDatabaseConnection: ");
        sqLiteDatabase.close();
    }

    private void openWritableDatabaseConnection() {
        Log.d(TAG, "openWritableDatabaseConnection: ");
        EventSchedularDbHelper eventSchedularDbHelper = new EventSchedularDbHelper(this);
        sqLiteDatabase = eventSchedularDbHelper.getWritableDatabase();
    }
    private void createAlarm(Calendar cal, String task) {
        Log.d(TAG, "createAlarm: Setting alarm for notification");
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        intent.putExtra("task", task);
        try {
            openReadableDatabaseConnection();
            String mSelectQuery = "SELECT id FROM reminder";
            //getting the last value
            Integer i = 0;
            Cursor cursor = sqLiteDatabase.rawQuery(mSelectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    i = cursor.getInt(0);
                } while (cursor.moveToNext());
            }
            cursor.close();
            Log.d(TAG, "createAlarm: Value of i: " + i);

            openWritableDatabaseConnection();
            String mInsertQuery = "INSERT INTO permission VALUES (null, " + i + ")";
            sqLiteDatabase.execSQL(mInsertQuery);

            Integer requestCode = 0;
            openReadableDatabaseConnection();
            cursor = sqLiteDatabase.rawQuery("SELECT permission_id FROM permission", null);
            if (cursor.moveToFirst()) {
                do {
                    requestCode = cursor.getInt(0);
                } while (cursor.moveToNext());
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, requestCode, intent, 0);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
            }

        } catch (Exception e) {
            Log.e(TAG, "createAlarm: Exception Caught", e);
        } finally {
            closeDatabaseConnection();
        }
    }

/*
    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (isConnectionAvailable()) {
            try {
                Log.d(TAG, "onCreate: Inside try");
                PullEvents pullNews = new PullEvents();
                pullNews.execute();

                progressDialog = new ProgressDialog(EventsListActivity.this);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Please Wait");
                progressDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "onCreate: " + e);
            }
        } else {
            Toast.makeText(this, "No network available", Toast.LENGTH_LONG).show();
        }
    }*/
}
