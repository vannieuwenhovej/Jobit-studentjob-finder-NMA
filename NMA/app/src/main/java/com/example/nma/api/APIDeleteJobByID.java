package com.example.nma.api;

import android.os.AsyncTask;
import android.util.Log;

import com.example.nma.config.ConnectionConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class APIDeleteJobByID extends AsyncTask<Void, String, String> {

    private int jobID;

    public APIDeleteJobByID(int jobID) {
        this.jobID = jobID;
    }


    @Override
    protected String doInBackground(Void... voids) {
        Log.d("DELETE", "delete " + jobID);
        String url = ConnectionConfig.getServerAddress() + "/nma/deletejob/" + jobID;
        URL obj = null;
        try {
            obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //add reuqest header
            con.setRequestMethod("DELETE");
            con.setRequestProperty(
                    "Content-Type", "application/x-www-form-urlencoded" );

            // Send post request
            con.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());

//            wr.write();
//            wr.write(jobID);
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
