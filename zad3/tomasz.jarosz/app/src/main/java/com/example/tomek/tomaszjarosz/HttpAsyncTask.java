package com.example.tomek.tomaszjarosz;


import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class HttpAsyncTask extends AsyncTask<String, Void, Void> {
    private String responseStr;
    public AsyncResponse delegate = null;

    @Override
    protected Void doInBackground(String... params) {
        String urlStr = MainActivity.BASE_SERVER_URL + params[0];

        try {
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setConnectTimeout(10000);
            getResponseFromUrl(urlConnection);
            urlConnection.disconnect();
        } catch (IllegalStateException | IOException e3) {
            e3.printStackTrace();
            cancel(true);
        }
        return null;
    }
    private void getResponseFromUrl(HttpURLConnection urlConnection) {
        try {
            if (urlConnection.getResponseCode() == 200 || urlConnection.getResponseCode() == 201) {
                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                responseStr = convertResponseToStr(inputStream);
                inputStream.close();
            }
            else {
                setFlagsToLastPage();
            }
        } catch (IOException e) {
            e.printStackTrace();
            cancel(true);
        }
    }
    private void setFlagsToLastPage() {
        MainActivity.isLastPage = true;
        CustomScrollListener.getNextPage = false;
    }
    private String convertResponseToStr(InputStream inputStream) {
            try {
                BufferedReader bReader =
                        new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                StringBuilder sBuilder = new StringBuilder();
                String line;
                while ((line = bReader.readLine()) != null) {
                    sBuilder.append(line);
                }
                return sBuilder.toString();
            } catch (Exception e) {
                Log.e("Error converting result", e.toString());
                return null;
            }
    }

    protected void onPostExecute(Void v) {

        MainActivity.asyncTasks.remove(this);
        if(!MainActivity.isLastPage) {
            getContentFromJSON();
        }
    }

    private void getContentFromJSON() {
        try {
            JSONObject jsonObject = new JSONObject(responseStr);
            makeItemsFromJsonObject(jsonObject);
            delegate.processFinish();
        } catch (JSONException e) {
            Log.e("JSONException", "Error: " + e.toString());
        }
    }
    private void makeItemsFromJsonObject(JSONObject jb) {
        JSONArray jArray;
        try {
            jArray = jb.getJSONArray("array");
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jObject = jArray.getJSONObject(i);
                makeItemFromJsonArray(jObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void makeItemFromJsonArray(JSONObject jObject) {
        String title = null;
        String desc = null;
        String url = null;
        try {
            title = jObject.getString("title");
            desc = jObject.getString("desc");
            url = jObject.getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Item item = new Item(title, desc, url);
        MainActivity.items.add(item);
    }
}

