package com.example.android.counsellingrequest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import java.util.regex.Pattern;

public class ContactActivity extends AppCompatActivity {
    private TextInputLayout mTextInputLayoutEmail;
    private TextInputLayout mTextInputLayoutTel;
    private TextInputLayout mTextInputLayoutMobile;
    private static final String TAG = "ContactActivity";

    //do not modify
    private String name;
    private String address;
    private String occupation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        mTextInputLayoutEmail = findViewById(R.id.textInputLayoutEmail);
        mTextInputLayoutTel = findViewById(R.id.textInputLayoutTel);
        mTextInputLayoutMobile = findViewById(R.id.textInputLayoutMobile);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            name = bundle.getString("name");
            address = bundle.getString("address");
            occupation = bundle.getString("occupation");
        }

    }


    private boolean isEmailValid() {
        CharSequence target = mTextInputLayoutEmail.getEditText().getText();
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (mTextInputLayoutEmail.getEditText().getText().toString().trim().equalsIgnoreCase("")) {
            mTextInputLayoutEmail.setError("This field can not be blank");
        } else if (!pattern.matcher(mTextInputLayoutEmail.getEditText().getText()).matches()) {
            mTextInputLayoutEmail.setError("Invalid Email Address");
        } else {
            mTextInputLayoutEmail.setError(null);
            return true;
        }
        return false;

    }


    private boolean isTelValid() {
        if (mTextInputLayoutTel.getEditText().getText().toString().trim().equalsIgnoreCase("")) {
            mTextInputLayoutTel.setError("This field can not be blank");
        } else if (mTextInputLayoutTel.getEditText().getText().toString().trim().length() > 13 || mTextInputLayoutTel.getEditText().getText().toString().trim().length() < 6) {
            mTextInputLayoutTel.setError("Please recheck your number");
        } else if (!mTextInputLayoutTel.getEditText().getText().toString().matches("[0-9]+")) {
            mTextInputLayoutTel.setError("Please recheck your number");
        } else {
            mTextInputLayoutTel.setError(null);
            return true;
        }
        return false;
    }

    private boolean isMobileValid() {
        if (mTextInputLayoutMobile.getEditText().getText().toString().trim().equalsIgnoreCase("")) {
            mTextInputLayoutMobile.setError("This field can not be blank");
        } else if (mTextInputLayoutMobile.getEditText().getText().toString().trim().length() > 14 || mTextInputLayoutMobile.getEditText().getText().toString().trim().length() < 10) {
            mTextInputLayoutMobile.setError("Please recheck your number");
        } else if (!mTextInputLayoutMobile.getEditText().getText().toString().matches("[0-9]+")) {
            mTextInputLayoutMobile.setError("Please recheck your number");
        } else {
            mTextInputLayoutMobile.setError(null);
            return true;
        }
        return false;
    }

    private boolean isInputValid() {
        return isTelValid() & isMobileValid() & isEmailValid();

    }

    public void goToNextActivity(View view) {
        if (isInputValid()) {
            Intent intent = new Intent(this, DateOfBirthActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("name", name);
            bundle.putString("address", address);
            bundle.putString("occupation", occupation);

            String email = mTextInputLayoutEmail.getEditText().getText().toString();
            String tel = mTextInputLayoutTel.getEditText().getText().toString();
            String mobile = mTextInputLayoutMobile.getEditText().getText().toString();

            bundle.putString("email", email);
            bundle.putString("tel", tel);
            bundle.putString("mobile", mobile);

            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    public void goToPreviousActivity(View view) {
        super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: ");
        String email = mTextInputLayoutEmail.getEditText().getText().toString();
        String tel = mTextInputLayoutTel.getEditText().getText().toString();
        String mobile = mTextInputLayoutMobile.getEditText().getText().toString();
        SharedPreferences sharedPreferences = this.getSharedPreferences("contact_activity",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.putString("tel", tel);
        editor.putString("mobile", mobile);
        editor.apply();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        SharedPreferences sharedPreferences = this.getSharedPreferences("contact_activity",Context.MODE_PRIVATE);
        mTextInputLayoutEmail.getEditText().setText(sharedPreferences.getString("email", ""));
        mTextInputLayoutTel.getEditText().setText(sharedPreferences.getString("tel", ""));
        mTextInputLayoutMobile.getEditText().setText(sharedPreferences.getString("mobile", ""));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
