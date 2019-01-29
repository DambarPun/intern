package com.example.android.counsellingrequest;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private JSONArray jsonArray;

    private static String JSON_URL = "http://192.168.1.114:81/counsel/";

    private static final String TAG = "Imonfire";
    private ScrollView mainScrollview;
    private TextView mTvDate;
    private Calendar now;
    private int year;
    private int month;
    private int dayOfMonth;

    private RadioGroup rbGroup;
    private RadioButton rbSpecial;

    private RadioButton rbAdvertisement;

    private String remark;
    /*
    private RadioButton rbPreviousStudentFriend;
    private RadioButton rbOrganisation;
    private RadioButton rbOthers;*/

    private EditText etPrevStudentFriend;
    private EditText etOrganisation;
    private EditText etOthers;
    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutAddress;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutOccupation;

    private EditText etCountry;
    private EditText ettel;
    private EditText etMobileNumber;

    private Spinner spinnerQualification;

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
        setContentView(R.layout.activity_main);
        mTvDate = (TextView) findViewById(R.id.tv_date);
        mainScrollview = (ScrollView) findViewById(R.id.mainScrollView);

        cbGmat = (CheckBox) findViewById(R.id.cb_gmat);
        cbGmat2 = (CheckBox) findViewById(R.id.cb_gmat2);

        cbGre = (CheckBox) findViewById(R.id.cb_gre);
        cbGre2 = (CheckBox) findViewById(R.id.cb_gre2);

        cbIelts = (CheckBox) findViewById(R.id.cb_ielts);
        cbIelts2 = (CheckBox) findViewById(R.id.cb_ielts2);

        cbToefl = (CheckBox) findViewById(R.id.cb_toefl);
        cbToefl2 = (CheckBox) findViewById(R.id.cb_toefl2);

        rbGroup = (RadioGroup) findViewById(R.id.radioGroup);
        rbAdvertisement = (RadioButton) findViewById(R.id.rb_advertisement);

        textInputLayoutName = (TextInputLayout) findViewById(R.id.textInputLayoutName);
        textInputLayoutAddress = (TextInputLayout) findViewById(R.id.textInputLayoutAddress);
        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutOccupation = (TextInputLayout) findViewById(R.id.textInputLayoutOccupation);

        etCountry = (EditText) findViewById(R.id.et_country);
        ettel = (EditText) findViewById(R.id.et_tel);
        etMobileNumber = (EditText) findViewById(R.id.et_mobile_number);

        /*rbAdvertisement = (RadioButton) findViewById(R.id.rb_advertisement);
        rbPreviousStudentFriend = (RadioButton) findViewById(R.id.rb_prev_student_friend);
        rbOrganisation = (RadioButton) findViewById(R.id.rb_organisation);
        rbOthers = (RadioButton) findViewById(R.id.rb_others);*/

        etPrevStudentFriend = (EditText) findViewById(R.id.et_prev_student_friend);
        etOrganisation = (EditText) findViewById(R.id.et_organisation);
        etOthers = (EditText) findViewById(R.id.et_others);
        etOthers.setVisibility(View.GONE);
        etPrevStudentFriend.setVisibility(View.GONE);
        etOrganisation.setVisibility(View.GONE);

        spinnerQualification = (Spinner) findViewById(R.id.spinner_qualification);
        setSpinner();

        remark = "";

        now = Calendar.getInstance();
        int currentYear = now.get(Calendar.YEAR);
        int currentMonth = now.get(Calendar.MONTH);
        int currentDayOfMonth = now.get(Calendar.DAY_OF_MONTH);

        setCalendar(currentYear, currentMonth, currentDayOfMonth);

    }

    private void setSpinner() {
        ArrayList<String> qualifications = new ArrayList<>(Arrays.asList("Secondary", "Higher Secondary", "Under Graduate", "Graduate"));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, qualifications);
        spinnerQualification.setAdapter(adapter);
    }

    private void setCalendar(int y, int m, int d) {
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        dayOfMonth = d;
        month = m;
        year = y;
        mTvDate.setText(dayOfMonth + " " + months[month] + " " + year);
    }

    public void openDatePicker(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                setCalendar(year, month, dayOfMonth);
                Toast.makeText(MainActivity.this, "onDateSet", Toast.LENGTH_SHORT).show();
            }
        }, year, month, dayOfMonth);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();
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

    public void sendData(View view) {
        sendJSON();
        //validate();
    }

    private void sendJSON() {
        try {
            if (validate()) {
                checkCheckedItems();

                String name = textInputLayoutName.getEditText().getText().toString();
                String address = textInputLayoutAddress.getEditText().getText().toString();
                String email = textInputLayoutEmail.getEditText().getText().toString();
                String occupation = textInputLayoutOccupation.getEditText().getText().toString();

                String interestedCourses = "";
                for (String str : courses) {
                    interestedCourses = interestedCourses + " " + str;
                }

                String exams = "";
                for (String str : examinations) {
                    exams = exams + " " + str;
                }

                String country = etCountry.getText().toString();
                String tel = ettel.getText().toString();
                String mobileNum = etMobileNumber.getText().toString();

                String qualification = spinnerQualification.getSelectedItem().toString();

                String dob = mTvDate.getText().toString();

                Log.d(TAG, "sendJSON: name" + name);
                Log.d(TAG, "sendJSON: address" + address);
                Log.d(TAG, "sendJSON: date of birth" + dob);
                Log.d(TAG, "sendJSON: qualification" + qualification);
                Log.d(TAG, "sendJSON: interested course" + interestedCourses);
                Log.d(TAG, "sendJSON: exams" + exams);
                Log.d(TAG, "sendJSON: country" + country);
                Log.d(TAG, "sendJSON: tel" + tel);
                Log.d(TAG, "sendJSON: mobile" + mobileNum);
                Log.d(TAG, "sendJSON: email" + email);
                Log.d(TAG, "sendJSON: answer" + rbSpecial.getText().toString());
                Log.d(TAG, "sendJSON: remark" + remark);


                final JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", name);
                jsonObject.put("address", address);
                jsonObject.put("dob", getFormattedDate());
                jsonObject.put("qualification", qualification);
                jsonObject.put("course", interestedCourses);
                jsonObject.put("country", country);
                jsonObject.put("occupation", occupation);
                jsonObject.put("tel", tel);
                jsonObject.put("mob", mobileNum);
                jsonObject.put("email", email);
                jsonObject.put("exam", exams);
                jsonObject.put("answer", rbSpecial.getText().toString());
                jsonObject.put("remark", remark);
                jsonArray = new JSONArray();
                jsonArray.put(jsonObject);

                Handler handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        String respond = msg.obj.toString();
                        Log.d(TAG, "handleMessage: " + respond);
                        ;
                        if (respond.equals("1")) {
                            jsonArray = new JSONArray();

                            Toast.makeText(MainActivity.this, "Data Saved", Toast.LENGTH_SHORT).show();
                        } else if (respond.equals("0")) {
                            jsonArray = new JSONArray();
                            Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                        Log.d(TAG, "handleMessage: JSON ARRAY " + jsonArray.toString());
                        super.handleMessage(msg);
                    }
                };
                HttpSourceRequest httpSourceRequest = new HttpSourceRequest(handler, JSON_URL + "androidjson.php?userdata=" + jsonArray.toString());
                reset();
            } else {
            }
        } catch (Exception e) {
            Log.e(TAG, "sendJSON: ", e);
        } finally {
            courses.removeAll(courses);
            examinations.removeAll(examinations);

        }
    }

    private void reset() {
        textInputLayoutName.getEditText().setText("");
        textInputLayoutEmail.getEditText().setText("");
        textInputLayoutOccupation.getEditText().setText("");
        textInputLayoutAddress.getEditText().setText("");
        etPrevStudentFriend.setText("");
        etOthers.setText("");
        etOrganisation.setText("");
        etMobileNumber.setText("");
        ettel.setText("");
        etCountry.setText("");
        rbAdvertisement.setChecked(true);
        cbIelts.setChecked(false);
        cbIelts2.setChecked(false);
        cbGmat.setChecked(false);
        cbGmat2.setChecked(false);
        cbGre.setChecked(false);
        cbGre2.setChecked(false);
        cbToefl.setChecked(false);
        cbToefl2.setChecked(false);
        mainScrollview.fullScroll(ScrollView.FOCUS_UP);
        now = Calendar.getInstance();
        int currentYear = now.get(Calendar.YEAR);
        int currentMonth = now.get(Calendar.MONTH);
        int currentDayOfMonth = now.get(Calendar.DAY_OF_MONTH);
        setCalendar(currentYear, currentMonth, currentDayOfMonth);
        remark = "";

    }

    public void onExaminationChecked(View view) {
    }

    public void onCourseChecked(View view) {
    }

    public void checkCheckedItems() {
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

    private boolean validate() {
        if (validateUsername() && validateAddress() && validateOccupation() && validateEmail() && validateTel() && validateMobile() && validateSpecial()) {
            Toast.makeText(this, "All Valid", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private boolean validateUsername() {
        String usernameInput = textInputLayoutName.getEditText().getText().toString().trim();
        if (usernameInput.isEmpty()) {
            textInputLayoutName.setError("Field can't be empty");
            return false;
        } else {
            textInputLayoutName.setError(null);
            return true;
        }
    }

    private boolean validateAddress() {
        String addressInput = textInputLayoutAddress.getEditText().getText().toString().trim();
        if (addressInput.isEmpty()) {
            textInputLayoutAddress.setError("Field can't be empty");
            return false;
        } else {
            textInputLayoutAddress.setError(null);
            return true;
        }
    }

    private boolean validateOccupation() {
        String occupationInput = textInputLayoutOccupation.getEditText().getText().toString().trim();
        if (occupationInput.isEmpty()) {
            textInputLayoutOccupation.setError("Field can't be empty");
            return false;
        } else {
            textInputLayoutOccupation.setError(null);
            return true;
        }
    }

    private boolean validateTel() {
        if (ettel.getText().toString().trim().equalsIgnoreCase("")) {
            ettel.setError("This field can not be blank");
            return false;
        } else if (ettel.getText().toString().trim().length() > 13 || ettel.getText().toString().trim().length() < 6) {
            ettel.setError("Please recheck your number");
            return false;
        } else {
            ettel.setError(null);
            return true;
        }
    }

    private boolean validateMobile() {
        if (etMobileNumber.getText().toString().trim().equalsIgnoreCase("")) {
            etMobileNumber.setError("This field can not be blank");
            return false;
        } else if (etMobileNumber.getText().toString().trim().length() > 14 || etMobileNumber.getText().toString().trim().length() < 10) {
            etMobileNumber.setError("Please recheck your number");
            return false;
        } else {
            etMobileNumber.setError(null);
            return true;
        }

    }

    private boolean validateEmail() {
        String emailInput = textInputLayoutEmail.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()) {
            textInputLayoutEmail.setError("Field can't be empty");
            return false;
        } else {
            textInputLayoutEmail.setError(null);
            return true;
        }
    }

    private String getFormattedDate() {
        return year + "-" + (month + 1) + "-" + dayOfMonth;
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

}
