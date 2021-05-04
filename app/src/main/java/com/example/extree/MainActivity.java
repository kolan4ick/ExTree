package com.example.extree;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences preferences = this.getPreferences(MODE_PRIVATE);
        if (preferences.getBoolean("firstLaunch", false)) {
        } else {
            preferences.edit().putBoolean("firstLaunch", true).apply();
            Intent intent = new Intent(MainActivity.this, InfoActivity.class);
            startActivity(intent);
        }
    }
}