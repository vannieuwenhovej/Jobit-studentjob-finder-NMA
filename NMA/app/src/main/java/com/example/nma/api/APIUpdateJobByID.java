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
        String json = makeJsonOfJob(jobOfferToSend);
        String url = ConnectionConfig.getServerAddress() + "/nmapost/updatejob/" + jobOfferToSend.getJobOfferID() + "/" + json;
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
