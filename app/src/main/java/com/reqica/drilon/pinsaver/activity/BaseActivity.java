package com.reqica.drilon.pinsaver.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
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
}
