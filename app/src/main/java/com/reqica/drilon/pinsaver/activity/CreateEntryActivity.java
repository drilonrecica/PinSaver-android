package com.reqica.drilon.pinsaver.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.reqica.drilon.pinsaver.EntrySaverTool;
import com.reqica.drilon.pinsaver.R;

public class CreateEntryActivity extends BaseActivity {
    private static final String KEY_DIVIDER = "±";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_entry);

        final EditText cardEditText = (EditText) findViewById(R.id.card_new_edit_text);
        final EditText pinEditText = (EditText) findViewById(R.id.pin_new_edit_text);
        Button saveButton = (Button) findViewById(R.id.save_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(cardEditText.getText())
                        && !TextUtils.isEmpty(pinEditText.getText())) {
                    saveEntry(cardEditText.getText().toString(),
                            pinEditText.getText().toString());
                } else {
                    Toast.makeText(CreateEntryActivity.this, getString(R.string.fields_cannot_be_empty), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveEntry(@NonNull String username, @NonNull String password) {
        String entryObject = username + KEY_DIVIDER + password;
        EntrySaverTool.saveEntry(entryObject, this);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        screenSession();
    }

    private void screenSession() {
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