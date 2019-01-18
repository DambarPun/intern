package com.example.android.pullnewsapi;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class NewsDetail extends AppCompatActivity {
    private ImageView mImg;
    private TextView mTvBlogTitle;
    private TextView mTvBlog;
    private TextView mPublishedBy;
    private TextView mDate;
    private TextView mOffice;

    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private static final String TAG = "NewsDetail";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        setSupportActionBar(mToolbar);
        init();

        mCollapsingToolbar.setCollapsedTitleTextAppearance(R.style.TextAppearance_AppTheme_Title_Collapsed);
        mCollapsingToolbar.setExpandedTitleTextAppearance(R.style.TextAppearance_AppTheme_Title_Expanded);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        String blogTitle = bundle.getString("blogtitle");
        String blog = bundle.getString("blog");
        String date = bundle.getString("date");
        String imgLink = bundle.getString("imglink");
        String office = bundle.getString("office");
        String publishedBy = bundle.getString("publishedby");

        mCollapsingToolbar.setTitle(blogTitle);

        if(imgLink.equals("null")){
            mImg.setVisibility(View.GONE);
        }

        Log.d(TAG, "onCreate: blogTitle"+blogTitle);
        Log.d(TAG, "onCreate: blog"+blog);
        Log.d(TAG, "onCreate: date"+date);
        Log.d(TAG, "onCreate: imgLink"+imgLink);
        Log.d(TAG, "onCreate: office"+office);
        Log.d(TAG, "onCreate: publishedBy"+publishedBy);

        blog +="orem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus. Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. Duis leo. Sed fringilla mauris sit amet nibh. Donec sodales sagittis magna. Sed consequat, leo eget bibendum sodales, augue velit cursus nunc,";
       // mTvBlogTitle.setText(blogTitle);
        mTvBlog.setText(blog);
        mDate.setText(date);
        mOffice.setText(office);
        mPublishedBy.setText(publishedBy);

        Glide.with(this).load(imgLink).into(mImg);

        mCollapsingToolbar.setTitle(blogTitle);
    }

    private void init() {
        mImg = (ImageView) findViewById(R.id.img_news_image_nd);
        //mTvBlogTitle = (TextView) findViewById(R.id.tv_blog_title_nd);
        mTvBlog = (TextView) findViewById(R.id.tv_blog_nd);
        mDate = (TextView) findViewById(R.id.tv_date_nd);
        mPublishedBy = (TextView) findViewById(R.id.tv_nd_published_by_nd);
        mOffice = (TextView) findViewById(R.id.tv_office_nd);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
