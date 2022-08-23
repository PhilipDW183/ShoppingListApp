package com.example.myshoppinglist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // hide the support activity bar
        getSupportActionBar().hide();

        // this will finally load the main activity
        final Intent i = new Intent(SplashActivity.this, MainActivity.class);
        //but after a delay of 100
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(i);
                finish();
            }
        }, 1000);
    }
}