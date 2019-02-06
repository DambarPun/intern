package com.example.android.events;

import android.app.ProgressDialog;
import android.content.Context;
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

public class EventsListActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ProgressDialog progressDialog;
    private static final String TAG = "EventsListActivity";

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
                String json = readUrl("http://192.168.1.80:81/counsel/eventrequest.php?event_request=[{\"orgkey\":\""+ getString(R.string.org_key) + "\"}]");
                jsonArray = new JSONArray(json);

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

                        Log.d(TAG, "doInBackground: " + event_title);
                        eventsArrayList.add(new Event(event_id, event_title, event_date, event_time, event_duration, event_venue, event_organizer, event_contact, event_type, event_description, event_photo));
                    }
                } else {
                    Toast.makeText(EventsListActivity.this, "No events to show", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "doInBackground: No events to show");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "doInBackground: ",e);
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

   /* @Override
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
