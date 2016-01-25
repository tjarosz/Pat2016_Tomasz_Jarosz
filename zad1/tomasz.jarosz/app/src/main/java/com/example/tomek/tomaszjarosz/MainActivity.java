package com.example.tomek.tomaszjarosz;


import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

@SuppressWarnings({"UnusedParameters", "unused"})
public class MainActivity extends AppCompatActivity {
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

    }


    public void onBtn1Click(View view) {
        linearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.red));
    }
    public void onBtn2Click(View view) {
        linearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.blue));
    }
    public void onBtn3Click(View view) {
        linearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.green));
    }
    public void onBtn4Click(View view) {
        linearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.orange));
    }
    public void onBtn5Click(View view) {
        linearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.purple));
    }
    public void onBtn6Click(View view) {
        linearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.pink));
    }
    public void onBtn7Click(View view) {
        linearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow));
    }
    public void onBtn8Click(View view) {
        linearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.black));
    }
    public void onBtn9Click(View view) {
        linearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
    }
}
