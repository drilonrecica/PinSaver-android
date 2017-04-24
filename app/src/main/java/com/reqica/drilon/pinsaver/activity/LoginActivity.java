package com.reqica.drilon.pinsaver.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.ajalt.reprint.core.AuthenticationFailureReason;
import com.github.ajalt.reprint.core.AuthenticationListener;
import com.github.ajalt.reprint.core.Reprint;
import com.reqica.drilon.pinsaver.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final RelativeLayout loginLayout = (RelativeLayout) findViewById(R.id.login_layout);
        final ImageView fingerprintIcon = (ImageView) findViewById(R.id.fingerprint_icon);
        final ImageView lockIcon = (ImageView) findViewById(R.id.lock_icon);
        final TextView messageTextView = (TextView) findViewById(R.id.message_text_view);

        if (Reprint.isHardwarePresent()) {

            if (Reprint.hasFingerprintRegistered()) {

                fingerprintIcon.setVisibility(View.VISIBLE);
                messageTextView.setText(R.string.message_authenticate);

                fingerprintIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog dialog = new Dialog(LoginActivity.this, android.R.style.Theme_Light);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_fingerprint);
                        dialog.show();

                        final TextView messageTextView = (TextView) dialog.findViewById(R.id.message_text_view);
                        final Button restartButton = (Button) dialog.findViewById(R.id.restart_button);
                        messageTextView.setVisibility(View.GONE);
                        restartButton.setVisibility(View.VISIBLE);

                        restartButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                startActivity(new Intent(LoginActivity.this, LoginActivity.class));
                                finish();
                            }
                        });
                    }
                });

                Reprint.authenticate(new AuthenticationListener() {
                    @Override
                    public void onSuccess(int moduleTag) {
                        messageTextView.setText(R.string.message_success_authentication);
                        lockIcon.setImageResource(R.drawable.ic_unlocked);

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //Do something after 1000ms
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            }
                        }, 1000);
                    }

                    @Override
                    public void onFailure(AuthenticationFailureReason failureReason, boolean fatal, CharSequence errorMessage, int moduleTag, int errorCode) {
                        loginLayout.setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.warningRed));
                        messageTextView.setText(getString(R.string.message_failure_authenticating, errorMessage));

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //Do something after 1000ms
                                loginLayout.setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.secureGreen));
                                messageTextView.setText(R.string.message_authenticate);
                            }
                        }, 1000);
                    }
                });
            } else {
                messageTextView.setText(R.string.message_no_fingerprint);
            }
        } else {
            messageTextView.setText(R.string.message_no_hardware);
        }
    }
}
