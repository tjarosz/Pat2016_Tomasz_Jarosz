package com.example.tomek.tomaszjarosz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    private boolean isBackPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final boolean isLogged = PreferenceData.getUserLogged(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if(isLogged) {
            startMainActivity();
        }
        else {
            startWaitingThread();
        }
    }
    private void startMainActivity() {
        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
        startActivity(intent);
    }
    private void startWaitingThread() {
        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (!isBackPressed) {
                        startLoginActivity();
                    }
                }
            }
        };
        timerThread.start();
    }

    private void startLoginActivity() {
        Intent intent = new Intent(SplashScreen.this, LoginScreen.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        isBackPressed = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
