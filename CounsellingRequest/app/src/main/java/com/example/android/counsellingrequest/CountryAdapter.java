package com.example.android.counsellingrequest;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {
    private ArrayList<CountryList.Country> countries;

    public CountryAdapter() {
        countries = CountryList.getCountryList();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mTvCountry.setText(countries.get(position).getName());
        holder.mTvIso.setText(countries.get(position).getIso());
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvCountry = itemView.findViewById(R.id.country_name);
            mTvIso = itemView.findViewById(R.id.country_iso);
        }
    }
}
