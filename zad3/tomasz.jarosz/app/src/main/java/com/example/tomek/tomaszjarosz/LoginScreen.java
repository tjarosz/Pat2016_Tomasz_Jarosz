package com.example.tomek.tomaszjarosz;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;

import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginScreen extends Activity {

    private EditText loginText;
    private EditText passText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginText = (EditText) findViewById(R.id.loginText);
        passText = (EditText) findViewById(R.id.passText);
    }

    @SuppressWarnings({"UnusedParameters", "unused"})
    public void onLoginClick(View view) {
        String login = loginText.getText().toString().trim();
        String password = passText.getText().toString().trim();
        Boolean isEmailValid = checkEmail(login);
        Boolean isPassValid = checkPassword(password);
        if(!isEmailValid) {
            showInvalidMailToast();
        }
        if(!isPassValid) {
            showInvalidPassToast();
        }
        if(isEmailValid && isPassValid) {
            PreferenceData.setUserLogged(getApplicationContext(), true);
            startMainActivity();
        }

    }
    private void showInvalidMailToast() {
        Toast mailToast = Toast.makeText(getApplicationContext(), "Invalid email address",
                Toast.LENGTH_SHORT);
        mailToast.setGravity(Gravity.TOP|Gravity.START, loginText.getRight(),
                loginText.getTop());
        mailToast.show();
    }

    private void showInvalidPassToast() {
        Toast passToast = Toast.makeText(getApplicationContext(), "Invalid password",
                Toast.LENGTH_SHORT);
        passToast.setGravity(Gravity.TOP|Gravity.START, passText.getRight(),
                passText.getBottom());
        passToast.show();
    }
    private void startMainActivity() {
        Intent intent = new Intent(LoginScreen.this, MainActivity.class);
        startActivity(intent);
    }

    private boolean checkEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    private boolean checkPassword(CharSequence target) {
        String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,})";
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(target);
        return matcher.matches();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }


}
