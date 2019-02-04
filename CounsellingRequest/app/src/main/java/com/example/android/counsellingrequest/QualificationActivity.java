package com.example.android.counsellingrequest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class QualificationActivity extends AppCompatActivity {
    private static final String TAG = "QualificationActivity";

    private AppCompatSpinner mAppCompatSpinner;
    private ArrayAdapter<String> adapter;
    private TextView mTvCountry;

    private String name;
    private String address;
    private String occupation;
    private String email;
    private String tel;
    private String mobile;
    private String dob;
    private String interestedCourses;
    private String examinationsAppeared;

    private CheckBox cbIelts;
    private CheckBox cbIelts2;

    private CheckBox cbToefl;
    private CheckBox cbToefl2;

    private CheckBox cbGmat;
    private CheckBox cbGmat2;

    private CheckBox cbGre;
    private CheckBox cbGre2;

    private ArrayList<String> courses = new ArrayList<>();
    private ArrayList<String> examinations = new ArrayList<>();

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qualification);
        mAppCompatSpinner = (AppCompatSpinner) findViewById(R.id.appCompatSpinner);
        mTvCountry = (TextView) findViewById(R.id.tv_country);
        cbGmat = (CheckBox) findViewById(R.id.cb_gmat);
        cbGmat2 = (CheckBox) findViewById(R.id.cb_gmat2);

        cbGre = (CheckBox) findViewById(R.id.cb_gre);
        cbGre2 = (CheckBox) findViewById(R.id.cb_gre2);

        cbIelts = (CheckBox) findViewById(R.id.cb_ielts);
        cbIelts2 = (CheckBox) findViewById(R.id.cb_ielts2);

        cbToefl = (CheckBox) findViewById(R.id.cb_toefl);
        cbToefl2 = (CheckBox) findViewById(R.id.cb_toefl2);
        setSpinner();


        /*Bundle countryBundle = getIntent().getBundleExtra("country");
        if (countryBundle != null) {
            Log.d(TAG, "onCreate: Country Bundle Not Empty");
            cbIelts.setChecked(countryBundle.getBoolean("cbIeltsState"));
            cbIelts2.setChecked(countryBundle.getBoolean("cbIelts2State"));
            cbGre.setChecked(countryBundle.getBoolean("cbGreState"));
            cbGre2.setChecked(countryBundle.getBoolean("cbGre2State"));
            cbGmat.setChecked(countryBundle.getBoolean("cbGmatState"));
            cbGmat2.setChecked(countryBundle.getBoolean("cbGmatState"));
            cbToefl.setChecked(countryBundle.getBoolean("cbToeflState"));
            cbToefl2.setChecked(countryBundle.getBoolean("cbToefl2State"));
            mAppCompatSpinner.setSelection(adapter.getPosition(countryBundle.getString("qualification")));


            mTvCountry.setText(countryBundle.getString("country"));
        }else {
            Log.d(TAG, "onCreate: Country Bundle Empty");
        }*/
        bundle = getIntent().getExtras();
        if (bundle != null) {
            Log.d(TAG, "onCreate: Normal bundle receive");
            name = bundle.getString("name");
            address = bundle.getString("address");
            occupation = bundle.getString("occupation");
            email = bundle.getString("email");
            tel = bundle.getString("tel");
            mobile = bundle.getString("mobile");
            dob = bundle.getString("dob");

            Log.d(TAG, "onCreate: " + bundle.getBoolean("cbIeltsState"));
            mAppCompatSpinner.setSelection(adapter.getPosition(bundle.getString("qualifications")));
            mTvCountry.setText(bundle.getString("country"));
            cbIelts.setChecked(bundle.getBoolean("cbIeltsState"));
            cbIelts2.setChecked(bundle.getBoolean("cbIelts2State"));
            cbGmat.setChecked(bundle.getBoolean("cbGmatState"));
            cbGmat2.setChecked(bundle.getBoolean("cbGmat2State"));
            cbGre.setChecked(bundle.getBoolean("cbGreState"));
            cbGre2.setChecked(bundle.getBoolean("cbGre2State"));
            cbToefl.setChecked(bundle.getBoolean("cbToeflState"));
            cbToefl2.setChecked(bundle.getBoolean("cbToefl2State"));

        } else {
            Toast.makeText(this, "Did not receive", Toast.LENGTH_SHORT).show();
        }
    }

    private Bundle getBundle() {
        return bundle;
    }

    private void setSpinner() {
        ArrayList<String> qualifications = new ArrayList<>(Arrays.asList("Secondary", "Higher Secondary", "Under Graduate", "Graduate"));
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, qualifications);
        mAppCompatSpinner.setAdapter(adapter);
    }

    public void checkExaminationsAppeared() {
        examinationsAppeared = "";
        for (String str : examinations) {
            examinationsAppeared = examinationsAppeared + " " + str;
        }
    }

    public void checkInterestedCourses() {
        interestedCourses = "";
        for (String str : courses) {
            interestedCourses = interestedCourses + " " + str;
        }
    }

    public void checkCheckedItems() {
        Log.d(TAG, "checkCheckedItems: ");
        if (cbIelts.isChecked())
            courses.add("IELTS");
        if (cbToefl.isChecked())
            courses.add("TOEFL");
        if (cbGre.isChecked())
            courses.add("GRE");
        if (cbGmat.isChecked())
            courses.add("GMAT");
        if (cbIelts2.isChecked())
            examinations.add("IELTS");
        if (cbToefl2.isChecked())
            examinations.add("TOEFL");
        if (cbGre2.isChecked())
            examinations.add("GRE");
        if (cbGmat2.isChecked())
            examinations.add("GMAT");
    }

    public void goToNextActivity(View view) {
        try {
            checkCheckedItems();
            checkInterestedCourses();
            checkExaminationsAppeared();
            Intent intent = new Intent(this, SpecialRequestActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("name", name);
            bundle.putString("address", address);
            bundle.putString("occupation", occupation);
            bundle.putString("email", email);
            bundle.putString("tel", tel);
            bundle.putString("mobile", mobile);
            bundle.putString("dob", dob);
            bundle.putString("country", mTvCountry.getText().toString());
            bundle.putString("qualification", mAppCompatSpinner.getSelectedItem().toString());
            bundle.putString("interestedCourses", interestedCourses);
            bundle.putString("examinationsAppeared", examinationsAppeared);
            intent.putExtras(bundle);
            startActivity(intent);
            Log.d(TAG, "goToNextActivity: end");
        } catch (Exception e) {
            Log.e(TAG, "goToNextActivity: ", e);
        }
    }

    public void goToPreviousActivity(View view) {
        super.onBackPressed();
    }

    public void openCountryActivity(View view) {
        Intent intent = new Intent(this, CountryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putString("address", address);
        bundle.putString("occupation", occupation);
        bundle.putString("email", email);
        bundle.putString("tel", tel);
        bundle.putString("mobile", mobile);
        bundle.putString("dob", dob);
        bundle.putString("country", mTvCountry.getText().toString());
        bundle.putString("qualification", mAppCompatSpinner.getSelectedItem().toString());
        bundle.putBoolean("cbIeltsState", cbIelts.isChecked());
        bundle.putBoolean("cbIelts2State", cbIelts2.isChecked());
        bundle.putBoolean("cbGmatState", cbGmat.isChecked());
        bundle.putBoolean("cbGmat2State", cbGmat2.isChecked());
        bundle.putBoolean("cbGreState", cbGre.isChecked());
        bundle.putBoolean("cbGre2State", cbGre2.isChecked());
        bundle.putBoolean("cbToeflState", cbToefl.isChecked());
        bundle.putBoolean("cbToefl2State", cbToefl2.isChecked());
        intent.putExtras(bundle);
        save();
        startActivity(intent);
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: ");
        save();
    }

    private void save() {
        checkCheckedItems();
        checkInterestedCourses();
        checkExaminationsAppeared();
        String country = mTvCountry.getText().toString();
        String qualification = mAppCompatSpinner.getSelectedItem().toString();
        boolean cbIeltsState = cbIelts.isChecked();
        boolean cbIelts2State = cbIelts2.isChecked();
        boolean cbToeflState = cbToefl.isChecked();
        boolean cbToefl2State = cbToefl2.isChecked();
        boolean cbGmatState = cbGmat.isChecked();
        boolean cbGmat2State = cbGmat2.isChecked();
        boolean cbGreState = cbGre.isChecked();
        boolean cbGre2State = cbGre2.isChecked();

        SharedPreferences sharedPreferences = this.getSharedPreferences("qualification_activity", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("country", country);
        editor.putString("qualification", qualification);
        editor.putBoolean("cbIeltsState", cbIeltsState);
        editor.putBoolean("cbIelts2State", cbIelts2State);
        editor.putBoolean("cbToeflState", cbToeflState);
        editor.putBoolean("cbToefl2State", cbToefl2State);
        editor.putBoolean("cbGreState", cbGreState);
        editor.putBoolean("cbGre2State", cbGre2State);
        editor.putBoolean("cbGmatState", cbGmatState);
        editor.putBoolean("cbGmat2State", cbGmat2State);
        editor.apply();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.d(TAG, "onPostResume: ");
        sharedPreferences = this.getSharedPreferences("qualification_activity", Context.MODE_PRIVATE);
        mTvCountry.setText(sharedPreferences.getString("country", "Nepal"));
        int pos = adapter.getPosition(sharedPreferences.getString("qualification", "Secondary"));
        mAppCompatSpinner.setSelection(pos);
        Log.d(TAG, "onPostResume: Ielts" + sharedPreferences.getBoolean("cbIeltsState", false));
        cbIelts.setChecked(sharedPreferences.getBoolean("cbIeltsState", false));
        cbIelts2.setChecked(sharedPreferences.getBoolean("cbIeltsState", false));
        cbGmat.setChecked(sharedPreferences.getBoolean("cbGmatState", false));
        cbGmat2.setChecked(sharedPreferences.getBoolean("cbGmat2State", false));
        cbGre.setChecked(sharedPreferences.getBoolean("cbGreState", false));
        cbGre2.setChecked(sharedPreferences.getBoolean("cbGre2State", false));
        cbToefl.setChecked(sharedPreferences.getBoolean("cbToeflState", false));
        cbToefl2.setChecked(sharedPreferences.getBoolean("cbToefl2State", false));
    }

    public void onExaminationChecked(View view) {
    }

    public void onCourseChecked(View view) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState: ");
    }
}
