package com.example.android.counsellingrequest;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

public class DateOfBirthActivity extends AppCompatActivity {
    private AppCompatTextView mAppCompatTextViewDOB;
    private AppCompatTextView mAppCompatTextViewError;
    private Calendar now;


    private static final String TAG = "DateOfBirthActivity";

    //Set values of date
    private int mYear;
    private int mMonth;
    private int mDayOfMonth;

    //do not modify
    private String name;
    private String address;
    private String occupation;
    private String email;
    private String tel;
    private String mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_of_birth);
        mAppCompatTextViewDOB = findViewById(R.id.appCompatTextViewDOB);
        mAppCompatTextViewError = findViewById(R.id.appCompatTextViewError);
        mAppCompatTextViewError.setVisibility(View.GONE);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            name = bundle.getString("name");
            address = bundle.getString("address");
            occupation = bundle.getString("occupation");
            email = bundle.getString("email");
            tel = bundle.getString("tel");
            mobile = bundle.getString("mobile");
            Log.d(TAG, "onCreate: Bundle data received");
            Log.d(TAG, "onCreate: name: " + name);
            Log.d(TAG, "onCreate: address: " + address);
            Log.d(TAG, "onCreate: occupation: " + occupation);
            Log.d(TAG, "onCreate: email: " + email);
            Log.d(TAG, "onCreate: tel: " + tel);
            Log.d(TAG, "onCreate: mobile: " + mobile);
        } else {
            Toast.makeText(this, "Did not receive", Toast.LENGTH_SHORT).show();
        }


        now = Calendar.getInstance();
        int currentYear = now.get(Calendar.YEAR);
        int currentMonth = now.get(Calendar.MONTH);
        int currentDayOfMonth = now.get(Calendar.DAY_OF_MONTH);
        setCalendar(currentYear, currentMonth, currentDayOfMonth);

    }

    private String getFormattedDate() {
        return mYear + "-" + (mMonth + 1) + "-" + mDayOfMonth;
    }

    public void openDatePicker(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                setCalendar(year, month, dayOfMonth);
            }
        }, mYear, mMonth, mDayOfMonth);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();
    }

    private void setCalendar(int y, int m, int d) {
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        mDayOfMonth = d;
        mMonth = m;
        mYear = y;
        mAppCompatTextViewDOB.setText(mDayOfMonth + " " + months[mMonth] + " " + mYear);
    }

    public void goToPreviousActivity(View view) {
        super.onBackPressed();
    }

    public void goToNextActivity(View view) {
        if (isDOBValid()) {
            Intent intent = new Intent(this, QualificationActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("name", name);
            bundle.putString("address", address);
            bundle.putString("occupation", occupation);
            bundle.putString("email", email);
            bundle.putString("tel", tel);
            bundle.putString("mobile", mobile);
            bundle.putString("dob", getFormattedDate());
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    private boolean isDOBValid() {
        Calendar now = Calendar.getInstance();
        if (now.get(Calendar.YEAR) <= mYear) //checking if the set year is greater than current year
        {
            mAppCompatTextViewError.setText("Invalid Date of Birth");
            mAppCompatTextViewError.setVisibility(View.VISIBLE);
        } else if ((now.get(Calendar.YEAR) - mYear) < 10)//checking if there is at least 10 year gap
        {
            mAppCompatTextViewError.setText("You are too young to register");
            mAppCompatTextViewError.setVisibility(View.VISIBLE);
        } else {
            mAppCompatTextViewError.setVisibility(View.GONE);
            return true;
        }
        return false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: ");
        String dob = mAppCompatTextViewDOB.getText().toString();
        SharedPreferences sharedPreferences = this.getSharedPreferences("date_of_birth_activity",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("month", mMonth);
        editor.putInt("day", mDayOfMonth);
        editor.putInt("year", mYear);
        editor.apply();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        SharedPreferences sharedPreferences = this.getSharedPreferences("date_of_birth_activity",Context.MODE_PRIVATE);
        int year = sharedPreferences.getInt("year",now.get(Calendar.YEAR));
        int month = sharedPreferences.getInt("month",now.get(Calendar.MONTH));
        int day = sharedPreferences.getInt("day",now.get(Calendar.DAY_OF_MONTH));
        setCalendar(year,month,day);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
