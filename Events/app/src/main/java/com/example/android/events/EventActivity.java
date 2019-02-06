package com.example.android.events;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

public class EventActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ImageView mIvImage;
    private TextView mTvDescription;
    private TextView mTvDateTime;
    private TextView mTvDuration;
    private TextView mTvContact;
    private TextView mTvOrganizer;
    private TextView mTvVenue;

    private FloatingActionButton mFloatingActionButton;
    private static final String TAG = "EventActivity";
    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    private String imageLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mIvImage = (ImageView) findViewById(R.id.iv_image);
        mTvDescription = (TextView) findViewById(R.id.tv_description);
        mTvDateTime = (TextView) findViewById(R.id.tv_date_time);
        mTvDuration = (TextView) findViewById(R.id.tv_duration);
        mTvContact = (TextView) findViewById(R.id.tv_contact);
        mTvOrganizer = (TextView) findViewById(R.id.tv_organiser);
        mTvVenue = (TextView) findViewById(R.id.tv_venue);

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        //mCollapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.TextAppearance_AppTheme_Title_Collapsed);
        //mCollapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.TextAppearance_AppTheme_Title_Expanded);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFloatingActionButton.getDrawable() == getDrawable(R.drawable.ic_action_notification_on)) {
                    mFloatingActionButton.setImageResource(R.drawable.ic_action_notification_off);
                } else {
                    mFloatingActionButton.setImageResource(R.drawable.ic_action_notification_on);
                }
            }
        });

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt("id");
        String title = bundle.getString("title");
        String date = bundle.getString("date");
        String time = bundle.getString("time");
        String duration = bundle.getString("duration");
        String venue = bundle.getString("venue");
        String organizer = bundle.getString("organizer");
        String contact = bundle.getString("contact");
        String type = bundle.getString("type");
        String description = bundle.getString("description");
        String photo = bundle.getString("photo");
        imageLink = getString(R.string.org_img_path) + photo;
        Log.d(TAG, "onCreate: http://192.168.1.80:81/counsel/images/" + photo);
        getSupportActionBar().setTitle(title);
        mTvDuration.setText(duration);
        mTvContact.setText(contact);
        mTvOrganizer.setText(organizer);
        mTvVenue.setText(venue);

        Glide.with(this).load(imageLink).into(mIvImage);
        mTvDescription.setText("Entry: " + type + "\n" + description);
        mTvDateTime.setText(date + " " + time);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EventActivity.this, EventsListActivity.class));
            }
        });
    }
}
