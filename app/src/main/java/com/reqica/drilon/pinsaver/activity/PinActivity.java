package com.reqica.drilon.pinsaver.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.reqica.drilon.pinsaver.EntrySaverTool;
import com.reqica.drilon.pinsaver.R;
import com.reqica.drilon.pinsaver.model.EntryModel;

public class PinActivity extends BaseActivity {

    private static final String KEY_NULL = "null";
    private static final String KEY_EMPTY = "";
    private static final String KEY_DIVIDER = "±";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        final EntryModel entryModel = getIntent().getParcelableExtra(MainActivity.KEY_ENTRY);

        final EditText cardEditText = (EditText) findViewById(R.id.card_new_edit_text);
        final TextInputEditText pinEditText = (TextInputEditText) findViewById(R.id.pin_new_edit_text);
        Button saveButton = (Button) findViewById(R.id.save_button);
        Button deleteButton = (Button) findViewById(R.id.delete_button);

        String cardName = entryModel != null ? entryModel.getCard() : KEY_EMPTY;

        if (cardName.startsWith(KEY_NULL)) {
            cardName = cardName.replace(KEY_NULL, KEY_EMPTY);
        }

        setTitle(cardName);

        cardEditText.setText(cardName);
        pinEditText.setText(entryModel.getPin());

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(cardEditText.getText())
                        && !TextUtils.isEmpty(pinEditText.getText())) {
                    String entryObject = entryModel.getCard() + KEY_DIVIDER + entryModel.getPin();
                    EntrySaverTool.removeEntry(entryObject, PinActivity.this);
                    saveEntry(cardEditText.getText().toString(),
                            pinEditText.getText().toString());
                } else {
                    Toast.makeText(PinActivity.this, getString(R.string.fields_cannot_be_empty), Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String entryObject = entryModel.getCard() + KEY_DIVIDER + entryModel.getPin();
                EntrySaverTool.removeEntry(entryObject, PinActivity.this);
                startActivity(new Intent(PinActivity.this, MainActivity.class));
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void saveEntry(@NonNull String card, @NonNull String pin) {
        String entryObject = card + KEY_DIVIDER + pin;
        EntrySaverTool.saveEntry(entryObject, this);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        screenSession();
    }
}
