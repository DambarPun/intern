package com.example.android.tictactoe;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class GameConfigurationActivity extends AppCompatActivity {
    private String mDifficultLevel = "Easy";
    private String mHuman = "X";
    private static final String TAG = "GameConfiguration";
    private TextView mTvEasy;
    private TextView mTvMedium;
    private TextView mTvHard;
    private TextView mTvNought;
    private TextView mTvCross;

    public static final String DIFFICULT_LEVEL = "difficultLevel";
    public static final String HUMAN = "human";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_configuration);
        init();
        restoreSharedPreferences();
    }

    private void restoreSharedPreferences() {
        Log.d(TAG, "restoreSharedPreferences: ");
        SharedPreferences sharedPreferences = this.getSharedPreferences(getString(R.string.game_configuration_activity_shared_preferences), Context.MODE_PRIVATE);
        setDifficultLevel(sharedPreferences.getString(DIFFICULT_LEVEL, "Easy"));
        setHuman(sharedPreferences.getString(HUMAN,"X"));
        Log.d(TAG, "restoreSharedPreferences: " + sharedPreferences.getString(DIFFICULT_LEVEL, "Easy"));


    }

    private void saveSharedPreferences() {
        Log.d(TAG, "saveSharedPreferences: ");
        SharedPreferences sharedPreferences = this.getSharedPreferences(getString(R.string.game_configuration_activity_shared_preferences), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(DIFFICULT_LEVEL, mDifficultLevel);
        Log.d(TAG, "saveSharedPreferences: " + mDifficultLevel);
        editor.putString(HUMAN, mHuman);
        editor.apply();
    }

    private void setDifficultLevel(String str) {
        if (mTvEasy.getText().equals(str)) {
            setDifficultLevelToEasy();
        } else if (mTvMedium.getText().equals(str)) {
            setDifficultLevelToMedium();
        } else {
            setDifficultLevelToHard();
        }
    }

    private void setHuman(String str) {
        if (mTvCross.getText().equals(str)) {
            setHumanToCross();
        } else {
            setHumanToNought();
        }
    }

    private void init() {
        mTvEasy = (TextView) findViewById(R.id.tv_easy);
        mTvMedium = (TextView) findViewById(R.id.tv_medium);
        mTvHard = (TextView) findViewById(R.id.tv_hard);
        mTvCross = (TextView) findViewById(R.id.tv_cross);
        mTvNought = (TextView) findViewById(R.id.tv_nought);
    }

    public void setDifficultLevelToEasy(View view) {
        setDifficultLevelToEasy();
    }

    private void setDifficultLevelToEasy() {
        Log.d(TAG, "setDifficultLevelToEasy: ");
        mDifficultLevel = "Easy";
        mTvEasy.setBackground(getDrawable(R.drawable.selected_easy_button));
        mTvEasy.setTextColor(getResources().getColor(R.color.colorOnSelected));
        mTvEasy.setTypeface(null, Typeface.BOLD);

        mTvMedium.setBackground(getDrawable(R.drawable.unselected_medium_button));
        mTvMedium.setTypeface(null, Typeface.NORMAL);
        mTvMedium.setTextColor(getResources().getColor(R.color.colorOnUnSelected));

        mTvHard.setBackground(getDrawable(R.drawable.unselected_hard_button));
        mTvHard.setTypeface(null, Typeface.NORMAL);
        mTvHard.setTextColor(getResources().getColor(R.color.colorOnUnSelected));
    }

    public void setDifficultLevelToMedium(View view) {
        setDifficultLevelToMedium();
    }

    private void setDifficultLevelToMedium() {
        Log.d(TAG, "setDifficultLevelToMedium: ");
        mDifficultLevel = "Medium";
        mTvMedium.setBackground(getDrawable(R.drawable.selected_medium_button));
        mTvMedium.setTypeface(null, Typeface.BOLD);
        mTvMedium.setTextColor(getResources().getColor(R.color.colorOnSelected));

        mTvEasy.setBackground(getDrawable(R.drawable.unselected_easy_button));
        mTvEasy.setTextColor(getResources().getColor(R.color.colorOnUnSelected));
        mTvEasy.setTypeface(null, Typeface.NORMAL);

        mTvHard.setBackground(getDrawable(R.drawable.unselected_hard_button));
        mTvHard.setTypeface(null, Typeface.NORMAL);
        mTvHard.setTextColor(getResources().getColor(R.color.colorOnUnSelected));
    }


    public void setDifficultLevelToHard(View view) {
        setDifficultLevelToHard();
    }

    private void setDifficultLevelToHard() {
        Log.d(TAG, "setDifficultLevelToHard: ");

        mDifficultLevel = "Hard";
        mTvHard.setBackground(getDrawable(R.drawable.selected_hard_button));
        mTvHard.setTypeface(null, Typeface.BOLD);
        mTvHard.setTextColor(getResources().getColor(R.color.colorOnSelected));

        mTvEasy.setBackground(getDrawable(R.drawable.unselected_easy_button));
        mTvEasy.setTextColor(getResources().getColor(R.color.colorOnUnSelected));
        mTvEasy.setTypeface(null, Typeface.NORMAL);

        mTvMedium.setBackground(getDrawable(R.drawable.unselected_medium_button));
        mTvMedium.setTypeface(null, Typeface.NORMAL);
        mTvMedium.setTextColor(getResources().getColor(R.color.colorOnUnSelected));


    }

    public void setHumanToCross(View view) {
        setHumanToCross();
    }

    private void setHumanToCross() {
        Log.d(TAG, "setHumanToCross: ");
        mHuman = "X";
        mTvCross.setTypeface(null, Typeface.BOLD);
        mTvCross.setBackground(getDrawable(R.drawable.selected_x_button));
        mTvCross.setTextColor(getResources().getColor(R.color.colorOnSelected));

        mTvNought.setTypeface(null, Typeface.NORMAL);
        mTvNought.setBackground(getDrawable(R.drawable.unselected_o_button));
        mTvNought.setTextColor(getResources().getColor(R.color.colorOnUnSelected));
    }

    public void setHumanToNought(View view) {
        setHumanToNought();
    }

    private void setHumanToNought() {
        Log.d(TAG, "setHumanToNought: ");
        mHuman = "O";
        mTvNought.setTypeface(null, Typeface.BOLD);
        mTvNought.setBackground(getDrawable(R.drawable.selected_o_button));
        mTvNought.setTextColor(getResources().getColor(R.color.colorOnSelected));

        mTvCross.setTypeface(null, Typeface.NORMAL);
        mTvCross.setBackground(getDrawable(R.drawable.unselected_x_button));
        mTvCross.setTextColor(getResources().getColor(R.color.colorOnUnSelected));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveSharedPreferences();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");

    }
}
