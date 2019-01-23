package com.example.android.counsellingrequest;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private TextView mTvDate;
    private Calendar now;
    private int year;
    private int month;
    private int dayOfMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTvDate = (TextView) findViewById(R.id.tv_date);

        now = Calendar.getInstance();
        int currentYear = now.get(Calendar.YEAR);
        int currentMonth = now.get(Calendar.MONTH);
        int currentDayOfMonth = now.get(Calendar.DAY_OF_MONTH);

        setCalendar(currentYear, currentMonth, currentDayOfMonth);

    }

    private void setCalendar(int y, int m, int d) {
        String[] months = {"January","February","March","April","May","June","July","August","September","October","November","December"};
        dayOfMonth = d;
        month = m;
        year = y;
        mTvDate.setText(dayOfMonth + " " + months[month] + " " + year);
    }

    public void openDatePicker(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                setCalendar(year, month, dayOfMonth);
                Toast.makeText(MainActivity.this, "onDateSet", Toast.LENGTH_SHORT).show();
            }
        }, year, month, dayOfMonth);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();
    }
}
