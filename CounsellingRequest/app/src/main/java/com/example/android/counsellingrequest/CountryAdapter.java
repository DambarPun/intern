package com.example.android.counsellingrequest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {
    private ArrayList<CountryList.Country> countries;
    private Context mContext;
    private Bundle mBundle;

    private static final String TAG = "CountryAdapter";
    public CountryAdapter(Context context,Bundle bundle) {
        this.countries = CountryList.getCountryList();
        this.mContext = context;
        this.mBundle = bundle;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.mTvCountry.setText(countries.get(position).getName());
        holder.mTvIso.setText(countries.get(position).getIso());
        holder.mRecyclerContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,QualificationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                intent.putExtra("country",countries.get(position).getName());

                mBundle.putString("country",countries.get(position).getName());
                intent.putExtras(mBundle);
                SharedPreferences sharedPreferences = mContext.getSharedPreferences("qualification_activity",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("country",countries.get(position).getName());
                editor.apply();

                Log.d(TAG, "onClick: "+mBundle.getString("cbIeltsState"));

                mContext.startActivity(intent);
                ((Activity)mContext).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        //Name of the country
        private TextView mTvCountry;
        //Initials of the country
        private TextView mTvIso;

        private LinearLayout mRecyclerContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvCountry = itemView.findViewById(R.id.country_name);
            mTvIso = itemView.findViewById(R.id.country_iso);
            mRecyclerContainer = itemView.findViewById(R.id.recycler_container);
        }
    }
}
