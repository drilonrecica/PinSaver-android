package com.reqica.drilon.pinsaver.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ajalt.reprint.core.AuthenticationFailureReason;
import com.github.ajalt.reprint.core.AuthenticationListener;
import com.github.ajalt.reprint.core.Reprint;
import com.reqica.drilon.pinsaver.adapter.EntryAdapter;
import com.reqica.drilon.pinsaver.model.EntryModel;
import com.reqica.drilon.pinsaver.EntrySaverTool;
import com.reqica.drilon.pinsaver.R;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    public static final String KEY_ENTRY = "key_entry";
    private static final String KEY_DIVIDER = "±";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView entryListView = (ListView) findViewById(R.id.entry_list_view);
        FloatingActionButton addButton = (FloatingActionButton) findViewById(R.id.add_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CreateEntryActivity.class));
                finish();
            }
        });

        String[] entries = EntrySaverTool.getEntries(getApplicationContext());

        if (entries.length > 0) {
            ArrayList<EntryModel> articleList = new ArrayList<>();

            for (String article1 : entries) {
                String[] article = article1.split(KEY_DIVIDER);

                EntryModel favorite = null;
                try {
                    favorite = new EntryModel(article[0], article[1]);
                } catch (Exception e) {
                    Toast.makeText(this, getString(R.string.no_entries), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, CreateEntryActivity.class));
                    finish();
                }
                articleList.add(favorite);
            }

            EntryAdapter entryAdapter = new EntryAdapter(getApplicationContext(), articleList);
            entryListView.setAdapter(entryAdapter);
        }

        entryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                final Dialog dialog = new Dialog(MainActivity.this, android.R.style.Theme_Light);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_fingerprint);
                dialog.show();

                final TextView messageTextView = (TextView) dialog.findViewById(R.id.message_text_view);
                messageTextView.setText(R.string.message_authenticate);

                Reprint.authenticate(new AuthenticationListener() {
                    @Override
                    public void onSuccess(int moduleTag) {
                        EntryModel entryModel = (EntryModel) parent.getItemAtPosition(position);
                        Intent intent = new Intent(MainActivity.this, PinActivity.class);
                        intent.putExtra(MainActivity.KEY_ENTRY, entryModel);
                        dialog.dismiss();
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(AuthenticationFailureReason failureReason, boolean fatal, CharSequence errorMessage, int moduleTag, int errorCode) {
                        messageTextView.setText(R.string.error);
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        screenSession();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
