package com.example.tomek.tomaszjarosz;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AsyncResponse{

    private GridView gridView;
    private boolean isLoggedIn = false;
    public static List<Bitmap> bitmaps;
    public static List<Item> items;
    public static CustomAdapter adapter;
    public static int globalImgIterator;
    public static boolean isLastPage;
    public static final List<AsyncTask> asyncTasks = new ArrayList<>();
    public final static String BASE_SERVER_URL = "http://192.168.1.10:8080/zmienione";
    //final static String BASE_SERVER_URL = "http://10.0.2.2:8080";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        init();
        downloadFiles();
    }
    private void init() {
        globalImgIterator = 0;
        isLastPage = false;
        isLoggedIn = true;
        items = new ArrayList<>();
        bitmaps = new ArrayList<>();
        adapter = new CustomAdapter(this,items,bitmaps);
        gridView=(GridView)findViewById(R.id.gridView);
        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void downloadFiles(){
        HttpAsyncTask downloader = new HttpAsyncTask();
        downloader.delegate = this;
        downloader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "/page_0.json");
        asyncTasks.add(downloader);
    }

    @SuppressWarnings({"UnusedParameters", "unused"})
    public void onLogOutButtonClick(View view) {
        PreferenceData.setUserLogged(getApplicationContext(), false);
        isLoggedIn = false;
        Intent intent = new Intent(MainActivity.this, LoginScreen.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(!isLoggedIn)
        {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        asyncTasksCancel();
        backToHome();
        finish();
    }

    private void asyncTasksCancel() {
        for(AsyncTask at : asyncTasks)
            at.cancel(true);
    }
    private void backToHome() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    @Override
    public void processFinish() {
        startDownloadImgTasks();
        adapter.addItems(items);
        adapter.addBitmaps(bitmaps);
        gridView.setOnScrollListener(new CustomScrollListener());
    }

    private void startDownloadImgTasks() {
        for(int i = 0; i<items.size(); i++) {
            DownloadImageTask downImgTask =
                    new DownloadImageTask(items.get(i).getUrl(),bitmaps,adapter);
            downImgTask.execute();
            asyncTasks.add(downImgTask);
            globalImgIterator++;
        }
    }
}
