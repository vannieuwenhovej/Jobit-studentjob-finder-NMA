package com.example.nma.api;

import android.os.AsyncTask;

import com.example.nma.api.interfaces.AsyncResponseJobOfferByID;
import com.example.nma.api.interfaces.AsyncResponseSollicitants;
import com.example.nma.company.JobOffer;
import com.example.nma.config.ConnectionConfig;
import com.example.nma.student.Student;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class APIRetrieveJobByID extends AsyncTask<Void, String, String> implements AsyncResponseSollicitants {
    APIRetrieveSollicitantsForJob apiGetSollicitants;
    private ArrayList<Student> sollicitants = new ArrayList<>();
    private int jobID;
    private JobOffer job;
    public AsyncResponseJobOfferByID delegate = null;

    public APIRetrieveJobByID(int jobID) {
        this.jobID = jobID;
    }

    public void setSollicitants(ArrayList<Student> sollicitants) {
        this.sollicitants = sollicitants;
    }

    @Override
    protected String doInBackground(Void... voids) {
        apiGetSollicitants = new APIRetrieveSollicitantsForJob(this, jobID);
        apiGetSollicitants.delegate = this;
        apiGetSollicitants.execute();
// Create URL
        URL url = null;
        try {
            url = new URL(ConnectionConfig.getServerAddress() + "/nmaapi/jobid/" + jobID);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

// Create connection
        HttpURLConnection myConnection = null;
        try {
            if (url != null) {
                myConnection = (HttpURLConnection) url.openConnection();

                myConnection.setRequestProperty("User-Agent", "NMA/Android-JobApplication");
                myConnection.setRequestProperty("Accept",
                        "application/json");
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
            parseDataToJSON(s);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void parseDataToJSON(String s) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        //Firstly get array of jobs as Json.
        JsonArray jsonObjects = new JsonParser()
                .parse(s)
                .getAsJsonArray();
        ArrayList<JobOffer> jobs = new ArrayList<>();
        //Looping otherwise might cause exception if somehow more rows are returned in the most unlikely case.
        for (int i=0; i<jsonObjects.size(); i++){
            JsonObject obj = jsonObjects.get(i).getAsJsonObject();
            JobOffer job = new JobOffer(obj.get("fldVacatureID").getAsInt(),
                    null, obj.get("fldFunctie").getAsString(), obj.get("fldBrutoLoonPerUur").getAsDouble(),
                    obj.get("fldVacatureBeschrijving").getAsString(), null, obj.get("fldGezochtProfielUitleg").getAsString(),
                    format.parse(obj.get("fldStartDatum").getAsString()), format.parse(obj.get("fldEindDatum").getAsString()));

            jobs.add(job);

        }
        onPostExecute(jobs.get(0));
    }

    private void onPostExecute(JobOffer job) {
        this.job = job;
    }

    @Override
    public void processFinish(ArrayList<Student> sollicitants) {
        job.setSollicitanten(sollicitants);
        delegate.processFinish(job);
    }
}
