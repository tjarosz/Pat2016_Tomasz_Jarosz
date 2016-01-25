package com.example.tomek.tomaszjarosz;


import android.os.AsyncTask;
import android.widget.AbsListView;

public class CustomScrollListener implements AbsListView.OnScrollListener, AsyncResponse {

    private int currentPage = 0;
    private int previousTotal = 0;
    public static boolean getNextPage;

    public CustomScrollListener() {
        getNextPage = true;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {

        if (getNextPage){
            if (totalItemCount > previousTotal) {
                previousTotal = totalItemCount;
                currentPage++;
            }
            if((firstVisibleItem + visibleItemCount) ==  totalItemCount) {
                downloadNextPage();
                getNextPage = false;
            }
        }
    }

    private void downloadNextPage() {
        HttpAsyncTask downloader = new HttpAsyncTask();
        downloader.delegate = this;
        downloader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "/page_"
                + Integer.toString(currentPage) + ".json");

        MainActivity.asyncTasks.add(downloader);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void processFinish() {
        if(MainActivity.isLastPage) {
            MainActivity.adapter.notifyDataSetChanged();
            return;
        }
        startDownloadImgTasks();

        MainActivity.adapter.addItems(MainActivity.items);
        MainActivity.adapter.addBitmaps(MainActivity.bitmaps);

        getNextPage = true;
    }

    private void startDownloadImgTasks() {
        for(int i = MainActivity.globalImgIterator; i<MainActivity.items.size(); i++) {
            DownloadImageTask downImgTask =
                    new DownloadImageTask(MainActivity.items.get(i).getUrl(),
                            MainActivity.bitmaps,MainActivity.adapter);
            downImgTask.execute();
            MainActivity.asyncTasks.add(downImgTask);
            MainActivity.globalImgIterator++;
        }
    }
}
