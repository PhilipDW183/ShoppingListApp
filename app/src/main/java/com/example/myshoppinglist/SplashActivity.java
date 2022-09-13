package com.example.myshoppinglist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.myshoppinglist.Model.ShoppingModel;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // hide the support activity bar
        getSupportActionBar().hide();

        SharedPreferences onBoarding = getSharedPreferences("onBoardingScreen", MODE_PRIVATE);
        boolean isFirstTime = onBoarding.getBoolean("firstTime", true);


        // load the introductory page
        Intent onboard = new Intent(SplashActivity.this, OnboardActivity.class);
        //load tge main activity page
        Intent mainActivity = new Intent(SplashActivity.this, MainActivity.class);


        //but after a delay of 100
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isFirstTime){
                    SharedPreferences.Editor editor = onBoarding.edit();
                    editor.putBoolean("firstTime", false);
                    editor.commit();

                    startActivity(onboard);
                } else{
                    startActivity(mainActivity);
                }

//                startActivity(i);
                finish();
            }
        }, 1000);
    }
}