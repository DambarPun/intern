package com.example.android.counsellingrequest;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SpecialRequestActivity extends AppCompatActivity {
    private static String JSON_URL = "http://192.168.1.80:81/counsel/";
    private JSONArray jsonArray;
    private static final String KEY = "KEY1";

    private RadioButton rbPreviousStudentFriend;
    private RadioButton rbOrganisation;
    private RadioButton rbOthers;
    private RadioButton rbAdvertisement;
    private RadioGroup rbGroup;
    private RadioButton rbSpecial;

    private EditText etPrevStudentFriend;
    private EditText etOrganisation;
    private EditText etOthers;

    private String name;
    private String address;
    private String occupation;
    private String email;
    private String tel;
    private String mobile;
    private String dob;
    private String interestedCourses;
    private String country;
    private String examinationsAppeared;
    private String qualification;

    private String remark;

    private static final String TAG = "SpecialRequestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_request);
        rbPreviousStudentFriend = (RadioButton) findViewById(R.id.rb_prev_student_friend);
        rbAdvertisement = (RadioButton) findViewById(R.id.rb_advertisement);
        rbOthers = (RadioButton) findViewById(R.id.rb_others);
        rbOrganisation = (RadioButton) findViewById(R.id.rb_organisation);
        rbGroup = (RadioGroup) findViewById(R.id.radioGroup);
        etOrganisation = (EditText) findViewById(R.id.et_organisation);
        etOthers = (EditText) findViewById(R.id.et_others);
        etPrevStudentFriend = (EditText) findViewById(R.id.et_prev_student_friend);

        etPrevStudentFriend.setVisibility(View.GONE);
        etOthers.setVisibility(View.GONE);
        etOrganisation.setVisibility(View.GONE);


        Bundle bundle = getIntent().getExtras();
        {
            if (bundle != null) {

                name = bundle.getString("name");
                address = bundle.getString("address");
                occupation = bundle.getString("occupation");
                email = bundle.getString("email");
                tel = bundle.getString("tel");
                mobile = bundle.getString("mobile");
                dob = bundle.getString("dob");
                qualification = bundle.getString("qualification");
                country = bundle.getString("country");
                interestedCourses = bundle.getString("interestedCourses");
                examinationsAppeared = bundle.getString("examinationsAppeared");

                Log.d(TAG, "onCreate: Bundle data received");
                Log.d(TAG, "onCreate: name: " + name);
                Log.d(TAG, "onCreate: address: " + address);
                Log.d(TAG, "onCreate: occupation: " + occupation);
                Log.d(TAG, "onCreate: email: " + email);
                Log.d(TAG, "onCreate: tel: " + tel);
                Log.d(TAG, "onCreate: mobile: " + mobile);
                Log.d(TAG, "onCreate: dob: " + dob);
                Log.d(TAG, "onCreate: qualification: " + qualification);
                Log.d(TAG, "onCreate: country: " + country);
                Log.d(TAG, "onCreate: interestedCourses: " + interestedCourses);
                Log.d(TAG, "onCreate: examinationsAppeared: " + examinationsAppeared);
            }
        }
    }

    public void opRadioButtonClicked(View view) {

        int selected = rbGroup.getCheckedRadioButtonId();
        rbSpecial = (RadioButton) findViewById(selected);

        switch (view.getId()) {
            case R.id.rb_advertisement:
                etOthers.setVisibility(View.GONE);
                etPrevStudentFriend.setVisibility(View.GONE);
                etOrganisation.setVisibility(View.GONE);
                break;
            case R.id.rb_prev_student_friend:
                etOthers.setVisibility(View.GONE);
                etPrevStudentFriend.setVisibility(View.VISIBLE);
                etOrganisation.setVisibility(View.GONE);
                break;
            case R.id.rb_organisation:
                etOthers.setVisibility(View.GONE);
                etPrevStudentFriend.setVisibility(View.GONE);
                etOrganisation.setVisibility(View.VISIBLE);
                break;
            case R.id.rb_others:
                etPrevStudentFriend.setVisibility(View.GONE);
                etOrganisation.setVisibility(View.GONE);
                etOthers.setVisibility(View.VISIBLE);
                break;
        }
    }

    private boolean validateSpecial() {
        int selectedRbId = rbGroup.getCheckedRadioButtonId();
        if (selectedRbId == R.id.rb_organisation) {
            if (etOrganisation.getText().toString().trim().equalsIgnoreCase("")) {
                etOrganisation.setError("Field cannot be empty");
                return false;
            } else {
                etOrganisation.setError(null);
                remark = etOrganisation.getText().toString();
                return true;
            }

        } else if (selectedRbId == R.id.rb_others) {
            if (etOthers.getText().toString().trim().equalsIgnoreCase("")) {
                etOthers.setError("Field cannot be empty");
                return false;
            } else {
                etOthers.setError(null);
                remark = etOthers.getText().toString();
                return true;
            }

        } else if (selectedRbId == R.id.rb_prev_student_friend) {
            if (etPrevStudentFriend.getText().toString().trim().equalsIgnoreCase("")) {
                etPrevStudentFriend.setError("Field cannot be empty");
                return false;
            } else {
                etPrevStudentFriend.setError(null);
                remark = etPrevStudentFriend.getText().toString();
                return true;
            }
        } else {
            remark = "null";
            return true;
        }
    }

    public void submitForm(View view) {
        if (validateSpecial()) {
            try {
                sendJSON();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendJSON() throws JSONException {

        Log.d(TAG, "onCreate: Bundle data received");
        Log.d(TAG, "onCreate: name: " + name);
        Log.d(TAG, "onCreate: address: " + address);
        Log.d(TAG, "onCreate: occupation: " + occupation);
        Log.d(TAG, "onCreate: email: " + email);
        Log.d(TAG, "onCreate: tel: " + tel);
        Log.d(TAG, "onCreate: mobile: " + mobile);
        Log.d(TAG, "onCreate: dob: " + dob);
        Log.d(TAG, "onCreate: qualification: " + qualification);
        Log.d(TAG, "onCreate: country: " + country);
        Log.d(TAG, "onCreate: interestedCourses: " + interestedCourses);
        Log.d(TAG, "onCreate: examinationsAppeared: " + examinationsAppeared);

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("address", address);
        jsonObject.put("dob", dob);
        jsonObject.put("qualification", qualification);
        jsonObject.put("course", interestedCourses);
        jsonObject.put("country", country);
        jsonObject.put("occupation", occupation);
        jsonObject.put("tel", tel);
        jsonObject.put("mob", mobile);
        jsonObject.put("email", email);
        jsonObject.put("exam", examinationsAppeared);
        jsonObject.put("answer", rbSpecial.getText().toString());
        jsonObject.put("remark", remark);
        jsonObject.put("orgkey","012six");
        jsonArray = new JSONArray();
        jsonArray.put(jsonObject);



        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String respond = msg.obj.toString();
                Log.d(TAG, "handleMessage: " + respond);
                if (respond.equals("1")) {
                    jsonArray = new JSONArray();

                    Toast.makeText(SpecialRequestActivity.this, "Data Saved", Toast.LENGTH_SHORT).show();
                } else if (respond.equals("0")) {
                    jsonArray = new JSONArray();
                    Toast.makeText(SpecialRequestActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
                Log.d(TAG, "handleMessage: JSON ARRAY " + jsonArray.toString());
                super.handleMessage(msg);
            }
        };
        HttpSourceRequest httpSourceRequest = new HttpSourceRequest(handler, JSON_URL + "androidjson.php?userdata=" + jsonArray.toString());
    }

    public void goToPreviousActivity(View view) {
        super.onBackPressed();
    }
}
