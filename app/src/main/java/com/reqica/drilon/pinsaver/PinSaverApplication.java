package com.reqica.drilon.pinsaver;

import android.app.Application;

import com.github.ajalt.reprint.core.Reprint;

public class PinSaverApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Reprint.initialize(this);
    }
}
