package com.example.android.counsellingrequest;

import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class CountryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private String name;
    private String address;
    private String occupation;
    private String email;
    private String tels;
    private String mobile;
    private String dob;
    private String country;
    private String qualification;
    private String interestedCourses;
    private String examinationsAppeared;

    private Bundle mBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        recyclerView = (RecyclerView) findViewById(R.id.country_recycler);
        myToolbar.setTitleTextColor(Color.WHITE);
        myToolbar.setSubtitleTextColor(Color.WHITE);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        //mBundle = getIntent().getExtras();
        //if (mBundle != null) {
            /*
            String name = bundle.getString("name");
            String address = bundle.getString("address");
            String occupation = bundle.getString("occupation");
            String email = bundle.getString("email");
            String tel = bundle.getString("tel");
            String mobile = bundle.getString("mobile");
            String dob = bundle.getString("dob");
            String country = bundle.getString("country");
            String qualification = bundle.getString("qualification");
            String interestedCourses = bundle.getString("interestedCourses");
            String examinationsAppeared = bundle.getString("examinationsAppeared");*/
       // }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CountryAdapter(this,getIntent().getExtras()));


        // Get the intent, verify the action and get the query
        /*Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }*/
    }

    private void doMySearch(String query) {
    }
}
