package com.example.android.pullnewsapi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    CoordinatorLayout mCoordinatorLayout;
    RecyclerView mRecyclerView;
    private static final String TAG = "MainActivity";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Log Started");
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_news);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (isConnectionAvailable()) {
            try {
                Log.d(TAG, "onCreate: Inside try");
                PullNews pullNews = new PullNews();
                pullNews.execute();

                progressDialog = new ProgressDialog(MainActivity.this);
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

    private class PullNews extends AsyncTask<Void, Void, Void> {
        ArrayList<News> newsArrayList = new ArrayList<>();
        NewsAdapter newsAdapter;
        String json;
        JSONObject jsonObject;
        JSONArray jsonArray;

        @Override
        protected Void doInBackground(Void... voids) {
            json = "";
            try {
                String json = readUrl("http://baatoo.com.np/php/request.php?newsdata=1");
                Log.d(TAG, "doInBackground: +" + json);
                jsonArray = new JSONArray(json);
                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        String id = String.valueOf(jsonObject.get("id"));
                        String blogTitle = String.valueOf(jsonObject.get("blogtitle"));
                        String blog = String.valueOf(jsonObject.get("blog"));
                        String publishedBy = String.valueOf(jsonObject.get("publishedby"));
                        String date = jsonObject.getString("date");
                        String imgLink = jsonObject.getString("imglink");


                        String office = String.valueOf(jsonObject.get("office"));
                        newsArrayList.add(new News(id, blogTitle, blog, publishedBy, date, imgLink, office));
                    }
                } else {
                    Toast.makeText(MainActivity.this, "No news to show", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
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
            newsAdapter = new NewsAdapter(getApplicationContext(), newsArrayList);
            mRecyclerView.setAdapter(newsAdapter);
        }
    }

}
