package com.reqica.drilon.pinsaver.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    private CountDownTimer inactivityCountDownTimer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createInactivityCountDownTimer();
    }

    private void createInactivityCountDownTimer() {
        // After 90 seconds either the LoginActivity or the PinActivity will be active
        inactivityCountDownTimer = new CountDownTimer(12000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                finish();
            }
        };
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        resetInactivityCounter();
    }

    public void resetInactivityCounter() {
        inactivityCountDownTimer.cancel();
        inactivityCountDownTimer.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        resetInactivityCounter();
    }

    @Override
    protected void onPause() {
        super.onPause();
        resetInactivityCounter();
    }

    protected void screenSession() {
        // If the screen is off then the device has been locked
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        boolean isScreenOn;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            isScreenOn = powerManager.isInteractive();
        } else {
            isScreenOn = powerManager.isScreenOn();
        }

        if (!isScreenOn) {
            // The screen has been locked
            // do stuff...
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }
}
