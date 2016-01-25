package com.example.tomek.tomaszjarosz;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.BaseAdapter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


class DownloadImageTask extends AsyncTask<Integer, Integer, Bitmap> {

    private final List<Bitmap> bitmaps;
    private final BaseAdapter baseAdapter;
    private final String url;

    public DownloadImageTask(String url, List<Bitmap> bitmapList,BaseAdapter baseAdapter) {
        this.url = url;
        this.bitmaps = bitmapList;
        this.baseAdapter = baseAdapter;
    }


    protected void onPreExecute() {
    }

    protected Bitmap doInBackground(Integer... ints) {
        return getBitmapDownloaded(url);
    }

    protected void onPostExecute(Bitmap result) {
        MainActivity.asyncTasks.remove(this);
        bitmaps.add(result);
        baseAdapter.notifyDataSetChanged();
    }

    private Bitmap getBitmapDownloaded(String url) {
        InputStream copyInputStream1;
        InputStream copyInputStream2;
        byte [] bitmapData = getDataFromUrl(url);
        if(bitmapData != null) {
            copyInputStream1 = byteTOInputStream(bitmapData);
            copyInputStream2 = byteTOInputStream(bitmapData);
        } else {
            return null;
        }
        int requiredHeigth = 50;
        int requiredWidth = 50;
        return decodeSampledBitmapFromInputStream(copyInputStream1, copyInputStream2,
                requiredHeigth, requiredWidth);
    }
    private byte[] getDataFromUrl(String url1) {
        try {
            URL url=new URL(url1);
            HttpURLConnection connection=(HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(8000);
            InputStream inputStream = connection.getInputStream();
            return InputStreamTOByte(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Bitmap decodeSampledBitmapFromInputStream(InputStream in,
                                                      InputStream copyOfIn,
                                                      int reqHeight, int reqWidth) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(copyOfIn, null, options);
    }

    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    private byte[] InputStreamTOByte(InputStream in) throws IOException{

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024*16];
        int count;
        while((count = in.read(data,0,1024*16)) != -1)
            outStream.write(data, 0, count);

        return outStream.toByteArray();
    }

    private InputStream byteTOInputStream(byte[] in) {
        return new ByteArrayInputStream(in);
    }
}