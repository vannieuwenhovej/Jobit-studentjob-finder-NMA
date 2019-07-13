package com.example.nma.api;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.example.nma.config.ConnectionConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class APIHandler extends AsyncTask<String, Integer, Bitmap> {
    @Override
    protected Bitmap doInBackground(String... strings) {
        doAPIRequest();
        return null;
    }

    public void doAPIRequest() {
        URL url = null;
        try {
            url = new URL(ConnectionConfig.getServerAddress() + "/nmaapi/students");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

// Create connection
        HttpURLConnection myConnection = null;
        try {
            myConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        myConnection.setRequestProperty("User-Agent", "my-rest-app-v0.1");
        myConnection.setRequestProperty("Accept",
                "application/json");

        try {
            if (myConnection.getResponseCode() == 200) {
                // Success
            } else {
                System.out.println("ERROR in APIHandler");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream responseBody = null;
        try {
            responseBody = myConnection.getInputStream(); //inputstream
            String s = "";
            String line = "";

            // Wrap a BufferedReader around the InputStream
            BufferedReader rd = new BufferedReader(new InputStreamReader(responseBody));

            // Read response until the end
            while ((line = rd.readLine()) != null) {
                s += line;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
