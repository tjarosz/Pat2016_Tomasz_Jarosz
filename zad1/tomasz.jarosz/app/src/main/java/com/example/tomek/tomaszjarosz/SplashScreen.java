package com.example.tomek.tomaszjarosz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends Activity {

    private boolean isBackPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        startWaitingThread();
    }

    private void startWaitingThread() {
        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(5000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    if(!isBackPressed)
                    {
                        startMainActivity();
                    }

                }
            }
        };
        timerThread.start();
    }

    private void startMainActivity() {
        Intent intent = new Intent(SplashScreen.this,MainActivity.class);
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
