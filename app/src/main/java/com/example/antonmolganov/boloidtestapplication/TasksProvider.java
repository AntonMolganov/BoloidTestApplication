package com.example.antonmolganov.boloidtestapplication;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Anton on 29.05.2015.
 */
public class TasksProvider {
    private final static String data_url = "http://test.boloid.com:9000/tasks";

    public static ArrayList<Task> provideNewData() throws IOException, JSONException {
        ArrayList<Task> newtasks = new ArrayList<Task>();
        URL url = new URL(data_url);
        HttpURLConnection urlConnection;
        urlConnection = (HttpURLConnection) url.openConnection();
        if (urlConnection.getResponseCode()==200){
            InputStreamReader isr = new InputStreamReader(urlConnection.getInputStream());
            BufferedReader br = new BufferedReader(isr, 8192);
            String inputLine;
            String resultString = "";
            StringBuilder sb = new StringBuilder();
            while ((inputLine = br.readLine()) != null) sb.append(inputLine);
            br.close();
            resultString = sb.toString();



            JSONObject jo = new JSONObject(resultString);
            JSONArray ja = jo.getJSONArray("tasks");
            for (int i = 0; i < ja.length(); i++)
                try {
                    Task task = new Task();
                    JSONObject joi = ja.getJSONObject(i);
                    task.setText(ja.getJSONObject(i).getString("text"));
                    task.setDurationLimitText(ja.getJSONObject(i).getString("durationLimitText"));
                    task.setTranslation(joi.getBoolean("translation"));
                    task.setLon(joi.getJSONObject("location").getDouble("lon"));
                    task.setLat(joi.getJSONObject("location").getDouble("lat"));
                    task.setBingMapImage(joi.getString("bingMapImage"));
                    task.setDate(joi.getLong("date"));
                    task.setTitle(joi.getString("title"));
                    task.setPrice(joi.getLong("price"));
                    task.setReflink(joi.getString("reflink"));
                    task.setLongText(joi.getString("longText"));
                    task.setID(joi.getLong("ID"));
                    JSONArray jai = joi.getJSONArray("prices");
                    for (int j = 0; j < jai.length(); j++) {
                        task.addPrice(jai.getJSONObject(j).getString("description"), jai.getJSONObject(j).getLong("price"));
                    };
                    task.setZoomLevel(joi.getLong("zoomLevel"));
                    task.setLocationText(joi.getString("locationText"));
                    newtasks.add(task);
                } catch (JSONException e) {
                    /* skip this corrupted task */
                }
        }
        return newtasks;
    };
}
