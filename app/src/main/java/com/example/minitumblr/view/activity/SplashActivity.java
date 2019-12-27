package com.example.minitumblr.view.activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.minitumblr.controller.SplashController;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private SplashController mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mController = new SplashController(this);
        Intent intent;
        if (mController.getUserId() > 0) {
            /* User is logged in, go to Main activity */
            intent = new Intent(this, MainActivity.class);
        } else {
            /* User is not logged in, go to Login activity */
            intent = new Intent(this, LoginActivity.class);
        }
        startActivity(intent);
        finish();
    }
}
