package com.example.nma.api;

import android.os.AsyncTask;

import com.example.nma.company.JobOffer;
import com.example.nma.config.ConnectionConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class APIUpdateJobByID extends AsyncTask<Void, String, String> {


    private JobOffer jobOfferToSend;

    public APIUpdateJobByID(JobOffer jobOfferToSend) {
        super();
        this.jobOfferToSend = jobOfferToSend;
    }

    private String makeJsonOfJob(JobOffer job){
        Gson g = new GsonBuilder().setDateFormat("YYYY-MM-dd").create();
        return g.toJson(job);
    }

    @Override
    protected String doInBackground(Void... voids) {
        String json = makeJsonOfJob(jobOfferToSend); // + "/" + json;
        String url = ConnectionConfig.getServerAddress() + "/nma/updatejob/" + jobOfferToSend.getJobOfferID();
        URL obj = null;
        try {

            obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //add reuqest header
            con.setRequestMethod("PUT");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");

            // Send PUT request
            con.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write( json );
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
