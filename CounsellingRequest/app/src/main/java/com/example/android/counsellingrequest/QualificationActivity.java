package com.example.android.counsellingrequest;

import android.content.Intent;
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
    private TextView mTvcountry;

    private String name;
    private String address;
    private String occupation;
    private String email;
    private String tel;
    private String mobile;
    private String dob;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qualification);
        mAppCompatSpinner = (AppCompatSpinner) findViewById(R.id.appCompatSpinner);
        mTvcountry = (TextView) findViewById(R.id.tv_country);
        cbGmat = (CheckBox) findViewById(R.id.cb_gmat);
        cbGmat2 = (CheckBox) findViewById(R.id.cb_gmat2);

        cbGre = (CheckBox) findViewById(R.id.cb_gre);
        cbGre2 = (CheckBox) findViewById(R.id.cb_gre2);

        cbIelts = (CheckBox) findViewById(R.id.cb_ielts);
        cbIelts2 = (CheckBox) findViewById(R.id.cb_ielts2);

        cbToefl = (CheckBox) findViewById(R.id.cb_toefl);
        cbToefl2 = (CheckBox) findViewById(R.id.cb_toefl2);
        setSpinner();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            name = bundle.getString("name");
            address = bundle.getString("address");
            occupation = bundle.getString("occupation");
            email = bundle.getString("email");
            tel = bundle.getString("tel");
            mobile = bundle.getString("mobile");
            dob = bundle.getString("dob");

            Log.d(TAG, "onCreate: Bundle data received");
            Log.d(TAG, "onCreate: name: " + name);
            Log.d(TAG, "onCreate: address: " + address);
            Log.d(TAG, "onCreate: occupation: " + occupation);
            Log.d(TAG, "onCreate: email: " + email);
            Log.d(TAG, "onCreate: tel: " + tel);
            Log.d(TAG, "onCreate: mobile: " + mobile);
            Log.d(TAG, "onCreate: dob: " + dob);
        } else {
            Toast.makeText(this, "Did not receive", Toast.LENGTH_SHORT).show();
        }
    }

    private void setSpinner() {
        ArrayList<String> qualifications = new ArrayList<>(Arrays.asList("Secondary", "Higher Secondary", "Under Graduate", "Graduate"));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, qualifications);
        mAppCompatSpinner.setAdapter(adapter);
    }

    public void onExaminationChecked(View view) {
    }

    public void onCourseChecked(View view) {
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
            Log.d(TAG, "goToNextActivity: start");
            String interestedCourses = "";
            for (String str : courses) {
                interestedCourses = interestedCourses + " " + str;
            }

            String examinationsAppeared = "";
            for (String str : examinations) {
                examinationsAppeared = examinationsAppeared + " " + str;
            }

            Intent intent = new Intent(this, SpecialRequestActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("name", name);
            bundle.putString("address", address);
            bundle.putString("occupation", occupation);
            bundle.putString("email", email);
            bundle.putString("tel", tel);
            bundle.putString("mobile", mobile);
            bundle.putString("dob", dob);
            bundle.putString("country", mTvcountry.getText().toString());
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
        startActivity(new Intent(this, CountryActivity.class));
    }
}
