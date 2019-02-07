package com.example.android.events;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Event> mEvents;
    public EventAdapter(Context context, ArrayList<Event> events) {
        this.mContext = context;
        this.mEvents = events;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_events, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        String datetime  = mEvents.get(i).getEvent_date()+" "+mEvents.get(i).getEvent_time();
        viewHolder.mTvTitle.setText(mEvents.get(i).getEvent_title());
        viewHolder.mTvDateTime.setText(datetime);
        viewHolder.mTvDays.setText(mEvents.get(i).formattedDate());

        viewHolder.mCvLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EventActivity.class);
                Bundle bundle = new Bundle();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                bundle.putInt("id",mEvents.get(i).getEvent_id());
                bundle.putString("title", mEvents.get(i).getEvent_title());
                bundle.putString("date", mEvents.get(i).getEvent_date());
                bundle.putString("time", mEvents.get(i).getEvent_time());
                bundle.putString("duration", mEvents.get(i).getEvent_duration());
                bundle.putString("venue", mEvents.get(i).getEvent_venue());
                bundle.putString("organizer", mEvents.get(i).getEvent_organizer());
                bundle.putString("contact", mEvents.get(i).getEvent_contact());
                bundle.putString("type", mEvents.get(i).getEvent_type());
                bundle.putString("description", mEvents.get(i).getEvent_description());
                bundle.putString("photo", mEvents.get(i).getEvent_photo());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView mCvLayout;
        TextView mTvDateTime;
        TextView mTvDays;
        TextView mTvTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCvLayout = (CardView) itemView.findViewById(R.id.cv_layout);
            mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mTvDateTime = (TextView) itemView.findViewById(R.id.tv_date_time);
            mTvDays = (TextView) itemView.findViewById(R.id.tv_days);
        }
    }
}
