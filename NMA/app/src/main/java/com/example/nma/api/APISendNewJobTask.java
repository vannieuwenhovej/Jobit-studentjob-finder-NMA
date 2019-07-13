package com.example.nma.api;

import android.os.AsyncTask;

import com.example.nma.config.ConnectionConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class APISendNewJobTask extends AsyncTask<Void, String,String> {

    private String jobOfferToSendInJSON;

    public APISendNewJobTask(String jobOfferToSend) {
        super();
        this.jobOfferToSendInJSON = jobOfferToSend;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String url = ConnectionConfig.getServerAddress() + "/nmapost/newjob?json=" + jobOfferToSendInJSON;
        URL obj = null;
        try {
            obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "NMA/Android-JobApplication");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        // Send post request
        con.setDoOutput(true);
        OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        con.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
