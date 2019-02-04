package com.example.android.counsellingrequest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class BasicActivity extends AppCompatActivity {
    private TextInputLayout mTextInputLayoutAddress;
    private TextInputLayout mTextInputLayoutName;
    private TextInputLayout mTextInputLayoutOccupation;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private static final String TAG = "BasicActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_basic);
        mTextInputLayoutName = (TextInputLayout) findViewById(R.id.textInputLayoutName);
        mTextInputLayoutAddress = (TextInputLayout) findViewById(R.id.textInputLayoutAddress);
        mTextInputLayoutOccupation = (TextInputLayout) findViewById(R.id.textInputLayoutOccupation);

    }

    private boolean isNameValid() {
        String nameInput = mTextInputLayoutName.getEditText().getText().toString().trim();
        if (nameInput.isEmpty()) {
            mTextInputLayoutName.setError("Field can't be empty");
        } else if (!nameInput.matches("^[a-zA-Z ]*$")) {
            mTextInputLayoutName.setError("Name can contain only alphabets");
        } else if (nameInput.length() < 2) {
            mTextInputLayoutName.setError("Name must have at least two characters");
        } else {
            mTextInputLayoutName.setError(null);
            return true;
        }
        return false;
    }

    private boolean isAddressValid() {
        String addressInput = mTextInputLayoutAddress.getEditText().getText().toString().trim();
        if (addressInput.isEmpty()) {
            mTextInputLayoutAddress.setError("Field can't be empty");
        } else {
            mTextInputLayoutAddress.setError(null);
            return true;
        }
        return false;
    }

    private boolean isOccupationValid() {
        String occupationInput = mTextInputLayoutOccupation.getEditText().getText().toString().trim();
        if (occupationInput.isEmpty()) {
            mTextInputLayoutOccupation.setError("Field can't be empty");
        } else if (!occupationInput.matches("^[a-zA-Z ]*$")) {
            mTextInputLayoutName.setError("This field can contain only alphabets");
        } else if (occupationInput.length() < 2) {
            mTextInputLayoutName.setError("This field must have at least two characters");
        } else {
            mTextInputLayoutOccupation.setError(null);
            return true;
        }
        return false;
    }

    private boolean isInputValid() {
        return isNameValid() & isAddressValid() & isOccupationValid();
    }

    public void goToNextActivity(View view) {
        if (isInputValid()) {
            try {
                String name = mTextInputLayoutName.getEditText().getText().toString();
                String address = mTextInputLayoutAddress.getEditText().getText().toString();
                String occupation = mTextInputLayoutOccupation.getEditText().getText().toString();
                Log.d(TAG, "goToNextActivity: All inputs are valid");
                Intent intent = new Intent(this, ContactActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putString("address", address);
                bundle.putString("occupation", occupation);
                intent.putExtras(bundle);
                startActivity(intent);
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: ");
        String name = mTextInputLayoutName.getEditText().getText().toString();
        String address = mTextInputLayoutAddress.getEditText().getText().toString();
        String occupation = mTextInputLayoutOccupation.getEditText().getText().toString();
        sharedPreferences = this.getSharedPreferences("basic_activity",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.putString("address", address);
        editor.putString("occupation", occupation);
        editor.apply();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        sharedPreferences = getSharedPreferences("basic_activity",Context.MODE_PRIVATE);
        mTextInputLayoutName.getEditText().setText(sharedPreferences.getString("name", ""));
        mTextInputLayoutAddress.getEditText().setText(sharedPreferences.getString("address", ""));
        mTextInputLayoutOccupation.getEditText().setText(sharedPreferences.getString("occupation", null));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
