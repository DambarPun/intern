package com.example.android.pullnewsapi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private static final String TAG = "NewsAdapter";
    private Context mContext;
    private ArrayList<News> mNews;

    public NewsAdapter(Context context, ArrayList<News> news) {
        mContext = context;
        mNews = news;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_news, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        viewHolder.mTvPublishedBy.setText(mNews.get(i).getPublisdedby());
        viewHolder.mTvDate.setText(mNews.get(i).getDate());

        Log.d(TAG, "onBindViewHolder: Number of child of main layout" + viewHolder.mMainParent.getChildCount());
        Log.d(TAG, "onBindViewHolder: Number of child of secondary layout" + viewHolder.mSecondaryParent.getChildCount());
        Log.d(TAG, "onBindViewHolder: " + mNews.get(i).getImglink());
        if (mNews.get(i).getImglink().equals("null")) {
            viewHolder.mMainParent.removeAllViews();
            viewHolder.mMainParent.setVisibility(View.GONE);
            viewHolder.mTvSecondaryBlogTitle.setText(mNews.get(i).getBlogtitle());

        } else {
            viewHolder.mSecondaryParent.removeAllViews();
            viewHolder.mSecondaryParent.setVisibility(View.GONE);

            viewHolder.mTvMainBlogTitle.setText(mNews.get(i).getBlogtitle());
            Glide.with(mContext).load(mNews.get(i).getImglink()).into(viewHolder.mImgNewsImage);
        }

        viewHolder.mMainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NewsDetail.class);
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);

                Bundle bundle = new Bundle();
                bundle.putString("blogtitle", mNews.get(i).getBlogtitle());
                bundle.putString("blog", mNews.get(i).getBlog());
                bundle.putString("date", mNews.get(i).getDate());
                bundle.putString("imglink", mNews.get(i).getImglink());
                bundle.putString("office", mNews.get(i).getOffice());
                bundle.putString("publishedby", mNews.get(i).getPublisdedby());
                intent.putExtras(bundle);
                mContext.startActivity(intent);

                Log.d(TAG, "onClick: Clicked on Recycler");
                Log.d(TAG, "onClick: " + mNews.get(i).getBlogtitle());
                Log.d(TAG, "onClick: " + mNews.get(i).getBlog());
                Log.d(TAG, "onClick: " + mNews.get(i).getDate());
                Log.d(TAG, "onClick: " + mNews.get(i).getImglink());
                Log.d(TAG, "onClick: " + mNews.get(i).getOffice());
                Log.d(TAG, "onClick: " + mNews.get(i).getPublisdedby());
            }
        });


        //viewHolder.mTvBlog.setText(mNews.get(i).getBlog());
        //viewHolder.mTvOffice.setText(mNews.get(i).getOffice());
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvMainBlogTitle;
        private TextView mTvSecondaryBlogTitle;
        private TextView mTvPublishedBy;
        private TextView mTvDate;
        private ImageView mImgNewsImage;

        private LinearLayout mMainLayout;

        private ViewGroup mMainParent;
        private ViewGroup mSecondaryParent;

        //private TextView mTvBlog;
        //private TextView mTvOffice;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvMainBlogTitle = (TextView) itemView.findViewById(R.id.tv_main_blog_title);
            mTvSecondaryBlogTitle = (TextView) itemView.findViewById(R.id.tv_secondary_blog_title);
            mTvPublishedBy = (TextView) itemView.findViewById(R.id.tv_nd_published_by_nd);
            mTvDate = (TextView) itemView.findViewById(R.id.tv_date_nd);
            mImgNewsImage = (ImageView) itemView.findViewById(R.id.img_news_image_nd);

            mMainLayout = (LinearLayout) itemView.findViewById(R.id.layout_main);

            mMainParent = (ViewGroup) itemView.findViewById(R.id.parent_layout_main);
            mSecondaryParent = (ViewGroup) itemView.findViewById(R.id.parent_layout_secondary);


            //mTvBlog = (TextView) itemView.findViewById(R.id.tv_blog);
            //mTvOffice = (TextView) itemView.findViewById(R.id.tv_office);


        }
    }
}
