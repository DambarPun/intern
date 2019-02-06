package com.example.android.tictactoe;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.ConditionVariable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    private void saveSharedPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.main_activity_shared_preferences), Context.MODE_PRIVATE);


    }
}
